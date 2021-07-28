package com.test.plugin;

import redis.clients.jedis.Jedis;

public class Main {
    public static void main(String[] args) {
        Jedis resource = JedisUtil.getPool().getResource();
    }
}
