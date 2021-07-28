package com.test.plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jq.dbapi.common.ApiConfig;
import com.jq.dbapi.plugin.CachePlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

public class RedisCachePlugin extends CachePlugin {
    @Override
    public void set(ApiConfig apiConfig, Map<String, Object> map, Object data) {
        logger.info("设置缓存");
        Jedis jedis = null;
        try {
            JedisPool pool = JedisUtil.getPool();
            jedis = pool.getResource();
            String key = "api-" + apiConfig.getId();
            String hashKey = "";
            for (Object o : map.values()) {
                hashKey += o.toString() + "-";
            }
            jedis.hset(key, hashKey, JSON.toJSONString(data));
        } catch (Exception e) {

            super.logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void clean(ApiConfig apiConfig) {
        Jedis jedis = null;
        try {
            JedisPool pool = JedisUtil.getPool();
            jedis = pool.getResource();
            String key = "api-" + apiConfig.getId();
            jedis.del(key);
        } catch (Exception e) {
            super.logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Object get(ApiConfig apiConfig, Map<String, Object> map) {
        Jedis jedis = null;
        try {
            JedisPool pool = JedisUtil.getPool();
            jedis = pool.getResource();
            String key = "api-" + apiConfig.getId();
            String hashKey = "";
            for (Object o : map.values()) {
                hashKey += o.toString() + "-";
            }
            String hget = jedis.hget(key, hashKey);
            List<JSONObject> list = JSON.parseArray(hget, JSONObject.class);
            return list;
        } catch (Exception e) {
            super.logger.error(e.getMessage(), e);
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
