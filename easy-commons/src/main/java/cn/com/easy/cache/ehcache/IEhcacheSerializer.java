package cn.com.easy.cache.ehcache;

/**
 * Ehcache对象序列化接口
 * 
 * @author nibili 2015年9月23日
 * 
 */
public interface IEhcacheSerializer {

	/**
	 * 序列化
	 * 
	 * @param value
	 * @return
	 * @auth nibili 2015年9月23日
	 */
	public String  serialize(Object value) throws Exception;

	/**
	 * 反序列化对象
	 * 
	 * @param string
	 * @return
	 * @auth nibili 2015年9月23日
	 */
	public Object deserialize(String value);
}
