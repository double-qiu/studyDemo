package org.carl.canal.client;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.carl.canal.queue.RabbitMQService;
import org.carl.canal.queue.RabbitMQType;
import org.jetbrains.annotations.NotNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;

public class CanalFactory {
	protected static final Logger L = Logger.getGlobal();
	protected CanalConnector connector;
	protected static int batchSize = 10;// 需要取的数据条数
	protected Long timeout = 1L;


	public void initCanalConnector() {
		L.info("initial rabbitmq service...");
		connector = CanalConnectors.newSingleConnector(new InetSocketAddress("192.168.1.141", 11111), "example", "",
				"");
		connector.connect();
		connector.subscribe(".*\\..*");
		connector.rollback();
	}

	protected void parseRowData2RabbitMQ(@NotNull Entry entry) {
		if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
			return;
		}
		RowChange rowChage = null;
		try {
			rowChage = RowChange.parseFrom(entry.getStoreValue());
		} catch (Exception e) {
			throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(), e);
		}

		EventType eventType = rowChage.getEventType();
		L.info(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
				entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
				entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), eventType));
		// 分类别将数据库修改数据提交至RabbitMQ
		for (RowData rowData : rowChage.getRowDatasList()) {
			// 读取数据存入RabbitMQ
			if (eventType == EventType.DELETE) {
				L.info("RabbitMQ DELETE's Data");
				RabbitMQService.save2RabbitMQ(RabbitMQType.DELETE,
						columnData2JSON(eventType, entry, rowData.getBeforeColumnsList()));
			} else if (eventType == EventType.INSERT) {
				L.info("RabbitMQ INSERT Data");
				RabbitMQService.save2RabbitMQ(RabbitMQType.INSERT, columnData2JSON(eventType, entry, rowData.getAfterColumnsList()));
			} else if (eventType == EventType.UPDATE) {
				L.info("RabbitMQ UPDATE Data");
				RabbitMQService.save2RabbitMQ(RabbitMQType.UPDATE, columnData2JSON(eventType, entry, rowData.getAfterColumnsList()));
			} else {
				L.info("-------> before");
				printColumn(rowData.getBeforeColumnsList());
				L.info("-------> after");
				printColumn(rowData.getAfterColumnsList());
			}
		}
	}

	/**
	 * @show 组装需要的JSON数据
	 * @param eventType
	 *            String 数据库变更类型
	 * @param entry
	 *            String 实体类信息
	 * @param columns
	 *            List<Column> 变更的数据类型
	 * @return
	 */
	protected String columnData2JSON(EventType eventType, @NotNull Entry entry, List<Column> columns) {
		List<Key> keys = new ArrayList<Key>();// 保存主键信息可能为多主键
		// 需要组装为JSON保存   {"action":"UPDATE","doc":{"createDate":"0000-00-00 00:00:00","id":"dd","name":"ddos"},"keys":"[{\"name\":\"id\",\"value\":\"dd\"}]","table":"test"}
		JSONObject json = new JSONObject(), doc = new JSONObject();// json为总数据DOC为具体文档数据
		json.put("table", entry.getHeader().getTableName());// 表名
		for (Column column : columns) {
			if (column.getIsKey()) {// 判断是否为主键
				L.info("found primary " + column.getName());
				keys.add(new Key(column.getName(), column.getValue()));// 加入主键
			}
			doc.put(column.getName(), column.getValue());// 加入详细信息
		}
		json.put("keys", JSON.toJSONString(keys));// 主键
		json.put("action", eventType);
		json.put("doc", doc);
		return json.toString();
	}

	/**
	 * @show 将Cloumn转换为Map
	 * @param columns
	 * @return
	 */
	protected Map<String, Object> getCloumnData(@NotNull List<Column> columns) {
		Map<String, Object> data = new HashMap<String, Object>();
		for (Column column : columns) {
			data.put(column.getName(), column.getValue());
		}
		return data;
	}

	protected void printEntry(@NotNull Entry entry) {
		if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
			return;
		}

		RowChange rowChage = null;
		try {
			rowChage = RowChange.parseFrom(entry.getStoreValue());
		} catch (Exception e) {
			throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(), e);
		}

		EventType eventType = rowChage.getEventType();
		L.info(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
				entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
				entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), eventType));

		for (RowData rowData : rowChage.getRowDatasList()) {
			if (eventType == EventType.DELETE) {
				printColumn(rowData.getBeforeColumnsList());
			} else if (eventType == EventType.INSERT) {
				printColumn(rowData.getAfterColumnsList());
			} else if (eventType == EventType.UPDATE) {
				printColumn(rowData.getAfterColumnsList());
			} else {
				L.info("-------> before");
				printColumn(rowData.getBeforeColumnsList());
				L.info("-------> after");
				printColumn(rowData.getAfterColumnsList());
			}
		}
	}

	protected void printColumn(@NotNull List<Column> columns) {
		for (Column column : columns) {
			L.info(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
		}
	}

	/**
	 * 保存单个主键信息
	 * 
	 * @author Administrator
	 *
	 */
	class Key implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8818364333334437541L;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		private String name, value;

		public Key(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}
	}
}
