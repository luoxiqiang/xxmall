package per.xxmall.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {

	@Autowired(required= false)
	private ShardedJedisPool shardedJedisPool;
	
	//抽取
	private <T> T execute(Function<T, ShardedJedis> fun) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return fun.callback(shardedJedis);
		} finally {
			if(shardedJedis != null)
				shardedJedis.close();
		}
	}
	//设置键值
	public String set(String key,String val) {
		return execute(new Function<String,ShardedJedis>(){
			@Override
			public String callback(ShardedJedis e) {
				return e.set(key, val);
			}
			
		});
	}
	
	//获取值
	public String get(String key) {
		return execute(new Function<String,ShardedJedis>(){
			@Override
			public String callback(ShardedJedis e) {
				return e.get(key);
			}
			
		});
	}
	
	//删除键值
	public Long del(final String key) {
	    return this.execute(new Function<Long, ShardedJedis>() {
	        @Override
	        public Long callback(ShardedJedis e) {
	            return e.del(key);
	        }
	    });
	}

    /**
     * 设置生存时间，单位为：秒
     * 
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(final String key, final Integer seconds) {
        return this.execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis e) {
                return e.expire(key, seconds);
            }
        });
    }

    /**
     * 执行set操作并且设置生存时间，单位为：秒
     * @param key
     * @param value
     * @return
     */
    public String set(final String key, final String value, final Integer seconds) {
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis e) {
                String str = e.set(key, value);
                e.expire(key, seconds);
                return str;
            }
        });
    }
}
