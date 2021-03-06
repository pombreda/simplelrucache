/**
 * Copyright 2011 Damian Momot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.code.simplelrucache;

import java.util.concurrent.Callable;

/**
 * Base class for concrete implementations
 *
 * @author Damian Momot
 */
abstract class BaseLruCache<K, V> implements LruCache<K, V> {
    private final long ttl;

    /**
     * Constructs BaseLruCache
     *
     * @param ttl
     * @throws IllegalArgumentException if ttl is not positive
     */
    protected BaseLruCache(long ttl) {
        if (ttl <= 0) throw new IllegalArgumentException("ttl must be positive");

        this.ttl = ttl;
    }

    @Override
    public boolean contains(K key) {
        //can't use contains because of expiration policy
        V value = get(key);

        return value != null;
    }

    /**
     * Creates new LruCacheEntry<V>.
     *
     * It can be used to change implementation of LruCacheEntry
     *
     * @param value
     * @param ttl
     * @return
     */
    protected LruCacheEntry<V> createEntry(V value, long ttl) {
        return new StrongReferenceCacheEntry<V>(value, ttl);
    }

    @Override
    public V get(K key) {
        return getValue(key);
    }

    @Override
    public V get(K key, Callable<V> callable) throws Exception {
        return get(key, callable, ttl);
    }

    @Override
    public V get(K key, Callable<V> callable, long ttl) throws Exception {
        V value = get(key);

        //if element doesn't exist create it using callable
        if (value == null) {
            value = callable.call();
            put(key, value, ttl);
        }

        return value;
    }

    @Override
    public long getTtl() {
        return ttl;
    }

    /**
     * Returns LruCacheEntry mapped by key or null if it does not exist
     *
     * @param key
     * @return
     */
    abstract protected LruCacheEntry<V> getEntry(K key);

    /**
     * Tries to retrieve value by it's key. Automatically removes entry if
     * it's not valid (LruCacheEntry.getValue() returns null)
     *
     * @param key
     * @return
     */
    protected V getValue(K key) {
        V value = null;

        LruCacheEntry<V> cacheEntry = getEntry(key);

        if (cacheEntry != null) {
            value = cacheEntry.getValue();

            //autoremove entry from cache if it's not valid
            if (value == null) remove(key);
        }

        return value;
    }

    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public void put(K key, V value) {
        put(key, value, ttl);
    }

    @Override
    public void put(K key, V value, long ttl) {
        if (value != null) putEntry(key, createEntry(value, ttl));
    }

    /**
     * Puts entry into cache
     *
     * @param key
     * @param entry
     */
    abstract protected void putEntry(K key, LruCacheEntry<V> entry);
}
