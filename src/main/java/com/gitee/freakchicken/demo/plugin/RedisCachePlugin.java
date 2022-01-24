package com.gitee.freakchicken.demo.plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.freakchicken.dbapi.common.ApiConfig;
import com.gitee.freakchicken.dbapi.plugin.CachePlugin;
import com.gitee.freakchicken.dbapi.plugin.PluginConf;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;

public class RedisCachePlugin extends CachePlugin {

    JedisPool pool;

    public void testCollection(){
        Jedis resource = pool.getResource();
    }

    @Override
    public void init() {
        JedisPoolConfig jcon = new JedisPoolConfig();
        jcon.setMaxTotal(200);
        jcon.setMaxIdle(50);
        jcon.setTestOnBorrow(true);
        jcon.setTestOnReturn(true);
        String password = PluginConf.getKey("RedisCachePlugin.password");
        if (StringUtils.isNotBlank(password)) {
            this.pool = new JedisPool(jcon, PluginConf.getKey("RedisCachePlugin.ip"),
                    Integer.parseInt(PluginConf.getKey("RedisCachePlugin.port")), 100,
                    password,
                    Integer.parseInt(PluginConf.getKey("RedisCachePlugin.db")));
        } else {
            this.pool = new JedisPool(jcon, PluginConf.getKey("RedisCachePlugin.ip"),
                    Integer.parseInt(PluginConf.getKey("RedisCachePlugin.port")), 100,
                    null,
                    Integer.parseInt(PluginConf.getKey("RedisCachePlugin.db")));
        }
        super.logger.info("init jedis pool success");
    }

    @Override
    public void set(ApiConfig apiConfig, Map<String, Object> map, Object data) {
        // redis缓存时间
        String expireTime = apiConfig.getCachePluginParams();
        super.logger.debug("set data to cache");
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String key = "api-" + apiConfig.getId();
            String hashKey = "";
            for (Object o : map.values()) {
                hashKey += o.toString() + "-";
            }
            jedis.hset(key, hashKey, JSON.toJSONString(data));
            // 设置过期时间，过期时间从插件参数传过来
            if (StringUtils.isNoneBlank(expireTime)) {
                jedis.expire(key, Integer.parseInt(expireTime));
            }
        } catch (Exception e) {
            super.logger.error("设置缓存失败", e);
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
        super.logger.debug("get data from cache");
        Jedis jedis = null;
        try {
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
            super.logger.error("查询缓存失败", e);
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
