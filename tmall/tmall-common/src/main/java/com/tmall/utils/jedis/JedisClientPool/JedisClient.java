package com.tmall.utils.jedis.JedisClientPool;

import java.util.List;

public interface JedisClient {

    List<String> hvals(String key);

    boolean hexists(String key, String field);

    String set(String key, String value);

    String get(String key);

    Boolean exists(String key);

    Long expire(String key, int seconds);

    Long ttl(String key);

    Long incr(String key);

    Long hset(String key, String field, String value);

    String hget(String key, String field);

    Long hdel(String key, String... field);


}
