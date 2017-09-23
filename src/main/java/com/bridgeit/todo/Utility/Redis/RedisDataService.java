package com.bridgeit.todo.Utility.Redis;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

public class RedisDataService {
	
	private static final String redisHost = "localhost";
    private static final Integer redisPort = 6379;
 
    //the jedis connection pool..
    private static JedisPool pool = null;
 
    public static void initpool() {
    	if(pool==null)
    	{
    		//configure our pool connection
            pool = new JedisPool(redisHost, redisPort);
    	}
        
 
    }
    public static void addHash( String key, Map<String, String> map) {
    	
    	
    	initpool();
        //add some values in Redis HASH
    
        Jedis jedis = pool.getResource();
        try {
            jedis.hmset(key, map);
           /* jedis.hmget(key, fields)*/
           
        } catch (JedisException e) {
        	e.printStackTrace();
            //if something wrong happen, return it back to the poolexist
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            ///it's important to return the Jedis instance to the pool once you've finished using it
            if (null != jedis)
                pool.returnResource(jedis);
            System.out.println();
        }
    }
    public static void getHash( String key) 
    {
    	initpool();
        //add some values in Redis HASH
    
        Jedis jedis = pool.getResource();
        try {
            //save to redis
           
           /* System.out.println( jedis.hgetAll(key));*/
        	/*System.out.printlnpackage com.spreadsheet.services;( jedis.hmget(key, fields);*/
           
        } catch (JedisException e) {
        	e.printStackTrace();
            //if something wrong happen, return it back to the pool
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            ///it's important to return the Jedis instance to the pool once you've finished using it
            if (null != jedis)
                pool.returnResource(jedis);
            System.out.println();
        }
    }
    
    
    
    
}
