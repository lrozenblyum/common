package com.igumnov.common;

import com.igumnov.common.cache.Value;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache {

    private static Map<String, Value> cache = new HashMap();
    private static Map<String, HashSet<String>> cacheTag = new HashMap();
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static Queue<Value> queue = new LinkedList<>();
    private static Queue<Value> queueExpired = new ConcurrentLinkedQueue<>();
    private static int cacheSize;
    private static double cacheDefaultTTL;

    public static void init(int size, double defaultTTL) {
        cacheSize = size;
        cacheDefaultTTL = defaultTTL;
        Task.schedule(() -> {
            while (!queueExpired.isEmpty()) {
                Cache.remove(queueExpired.poll().getKey());
            }
        }, 1.0);

    }

    public static Object put(String key, Object value, double ttl, String... tag) {
        Value obj = new Value();
        double objTTL;
        if (ttl == 0) {
            objTTL = cacheDefaultTTL;
        } else {
            objTTL = ttl;
        }
        obj.setObject(value);
        for (String t : tag) {
            obj.getTags().add(t);
        }
        obj.setExpireBy((long) (objTTL * 1000) + System.currentTimeMillis());
        obj.setKey(key);
        try {
            lock.writeLock().lock();
            if (cache.get(key) == null) {
                queue.add(obj);
            }
            cache.put(key, obj);
            for (String t : obj.getTags()) {
                HashSet<String> tagList = cacheTag.get(t);
                if (tagList == null) {
                    tagList = new HashSet<String>();
                    cacheTag.put(t, tagList);
                }
                tagList.add(key);
            }
            if (queue.size() > cacheSize) {
                delete(queue.poll());
            }

        } finally {
            lock.writeLock().unlock();
        }
        return value;

    }

    private static void delete(Value obj) {
        if (!obj.isRemoved()) {
            cache.remove(obj.getKey());
            for (String tag : obj.getTags()) {
                HashSet<String> objs = cacheTag.get(tag);
                if(objs != null) {
                    objs.remove(obj.getKey());
                    if (objs.size() == 0) {
                        cacheTag.remove(tag);
                    }
                }
            }
            obj.setRemoved(true);
        }
    }


    public static Object get(String key) {
        Value ret = null;
        try {
            lock.readLock().lock();
            ret = cache.get(key);
            if (ret != null) {
                if (ret.getExpireBy() < System.currentTimeMillis()) {
                    queueExpired.add(ret);
                    ret = null;
                }
            }
        } finally {
            lock.readLock().unlock();
        }

        if (ret == null) {
            return null;
        } else {
            return ret.getObject();
        }
    }

    public static void removeByTag(String tag) {

        try {
            lock.writeLock().lock();
            LinkedList<Value> deleteList = new LinkedList<>();
            HashSet<String> objects = cacheTag.get(tag);
            if (objects != null) {
                for (String object : objects) {
                    Value obj = cache.get(object);
                    if(obj != null) {
                        deleteList.push(obj);
                    }
                }
                for (Value value : deleteList) {
                    delete(value);
                }
            }

        } finally {
            lock.writeLock().unlock();
        }

    }

    public static Object remove(String key) {
        Object ret = null;
        try {
            lock.writeLock().lock();
            Value obj = cache.get(key);
            if (obj != null) {
                delete(obj);
                ret = obj.getObject();
            }
        } finally {
            lock.writeLock().unlock();
        }
        return ret;
    }


    public static Object put(String key, Object value, String... tag) {
        return put(key, value, 0, tag);
    }

}
