package org.carl.canal.listener;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.carl.canal.util.JsonRedisSerializer;
import org.carl.canal.util.RedisKeys;
import org.carl.canal.util.TableInfo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class CanalInsertLitener implements MessageListener {
	private static final Logger L = Logger.getGlobal();
	private ApplicationContext ac;
	private RedisTemplate rt;
	private JsonRedisSerializer jrs;
	private List<TableInfo> tableInfos;

	{
		init();// 初始化
	}

	private void init() {
		// ac = new ClassPathXmlApplicationContext("spring-redis.xml");
		// rt = (RedisTemplate) ac.getBean("jedisTemplate");
		jrs = new JsonRedisSerializer();
	}

	@Override
	public void onMessage(Message msg) {
		String jsonObj = new String(msg.getBody());
		JSONObject jo = JSONObject.parseObject(jsonObj);
		Map<Object, Object> keys, doc = null;
		String table = null, action = null;
		for (java.util.Map.Entry<String, Object> entry : jo.entrySet()) {
			switch (entry.getKey()) {
			case "table":
				table = entry.getValue().toString();
				break;
			case "keys":
				// keys=(Map<Object, Object>) entry.getValue();
				L.info(entry + "" + entry.getValue());
				break;
			case "action":
				table = entry.getValue().toString();
				break;
			case "doc":
				doc = (Map<Object, Object>) JSONObject.parse(entry.getValue().toString());
				break;

			default:
				L.info("unkown exception..");
				break;
			}
			System.out.print(entry.getKey() + "-" + entry.getValue() + "\t");
		}
		// 值取出完毕
		for (TableInfo tableInfo : tableInfos) {
			if (tableInfo.getValue().equalsIgnoreCase(table)) {// 符合表名
				String source_ip, target_ip, happened_time;
				String[] statistics_attacks_day_hour = null, statistics_attacks_type = null;
				source_ip = doc.get("source_ip").toString();
				target_ip = doc.get("target_ip").toString();
				happened_time = doc.get("happened_time").toString();
				try {
					statistics_attacks_day_hour = RedisKeys.statistics_attacks_day_hour(happened_time);
					statistics_attacks_type = RedisKeys.statistics_attacks_type(table);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				RedisConnectionFactory factory = rt.getConnectionFactory();
				RedisConnection redisConnection = RedisConnectionUtils.getConnection(factory);
				List<Object> results;
				redisConnection.openPipeline();
				redisConnection.zIncrBy(statistics_attacks_day_hour[0].getBytes(), 1.0,
						statistics_attacks_day_hour[1].getBytes());
				redisConnection.zIncrBy(statistics_attacks_type[0].getBytes(), 1.0,
						statistics_attacks_type[1].getBytes());
				if (StringUtils.isNotBlank(target_ip)) {
					String[] statistics_attacks_sourceip = RedisKeys.statistics_attacks_sourceip(source_ip);
					redisConnection.zIncrBy(statistics_attacks_sourceip[0].getBytes(), 1.0,
							statistics_attacks_sourceip[1].getBytes());
				}
				if (StringUtils.isNotBlank(target_ip)) {
					String[] statistics_attacks_targetip = RedisKeys.statistics_attacks_targetip(source_ip);
					redisConnection.zIncrBy(statistics_attacks_targetip[0].getBytes(), 1.0,
							statistics_attacks_targetip[1].getBytes());
				}
				//统计攻击类型开始
				String statistics_attacks_count[]=RedisKeys.statistics_attacks_count(table);//获取该类型个数
				//判断该key是否存在,存在则+1,否则为0
				Double score=rt.opsForZSet().score(statistics_attacks_count[0], statistics_attacks_count[1]);
				L.info("this key's score \t" +score);
				if(score!=null){
					redisConnection.zIncrBy(statistics_attacks_count[0].getBytes(), 1.0,
							statistics_attacks_count[1].getBytes());
				}else{
					redisConnection.zAdd(statistics_attacks_count[0].getBytes(), 0.0, statistics_attacks_count[1].getBytes());
				}
				//统计攻击类型结束
				results = redisConnection.closePipeline();
				if (results == null) {
					return;
				}
				L.info("excute result..." + results.toString());
				redisConnection.close();
			}
		}
	}
	public ApplicationContext getAc() {
		return ac;
	}

	public void setAc(ApplicationContext ac) {
		this.ac = ac;
	}

	public RedisTemplate getRt() {
		return rt;
	}

	public void setRt(RedisTemplate rt) {
		this.rt = rt;
	}

	public JsonRedisSerializer getJrs() {
		return jrs;
	}

	public void setJrs(JsonRedisSerializer jrs) {
		this.jrs = jrs;
	}

	public List<TableInfo> getTableInfos() {
		return tableInfos;
	}

	public void setTableInfos(List<TableInfo> tableInfos) {
		this.tableInfos = tableInfos;
	}

}
