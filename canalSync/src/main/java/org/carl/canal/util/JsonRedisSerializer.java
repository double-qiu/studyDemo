package org.carl.canal.util;

import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;

public class JsonRedisSerializer {
	/**
	 * 不使用sdr自带的json序列化工具，一切操作基于string
	 **/
	public static final String EMPTY_JSON = "{}";

	public JsonRedisSerializer() {
	}

	/**
	 * java-object as json-string
	 * 
	 * @param object
	 * @return
	 */
	public String seriazileAsString(Object object) {
		if (object == null) {
			return EMPTY_JSON;
		}
		try {
			return JSON.toJSONString(object);// 使用fastjson处理数据
		} catch (Exception ex) {
			throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
		}
	}

	/**
	 * @show 反序列化
	 * @param str
	 * @param clazz
	 * @return
	 */
	public <T> T deserializeAsObject(String str, Class<T> clazz) {
		if (str == null || clazz == null) {
			return null;
		}
		try {
			return JSON.parseObject(str, clazz);// 转换数据
		} catch (Exception ex) {
			throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
		}
	}

}