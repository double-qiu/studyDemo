package cn.didadu.sample.rediscluster;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jinggg on 16/3/29.
 */
public class RedisClusterTest {

    public static void main(String[] args){
        /**
         * Jedis连接池
         */
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(100);
        config.setMinIdle(100);
        config.setMaxWaitMillis(6 * 1000);
        config.setTestOnBorrow(true);

        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 9001));
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 9002));
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 9003));

        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, 2000, 2, config);
        try{
            for(int i = 0; i < 1000; i++){
                long t1 = System.currentTimeMillis();
                jedisCluster.set("" + i, "" + i);
                long t2 = System.currentTimeMillis();
                String value = jedisCluster.get("" + i);
                long t3 = System.currentTimeMillis();
                System.out.println("" + value);
                System.out.println((t2 - t1) + "mills");
                System.out.println((t3 - t2) + "mills");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                jedisCluster.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
