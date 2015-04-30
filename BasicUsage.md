## Construction of cache instance ##

Cache instances are constructed like any other objects. Example:

```java

int capacity = 500;
long ttl = 5*60*1000; //5 minutes

LruCache<String, Integer> cache = new ConcurrentLruCache<String, Integer>(capacity, ttl);
```

## Basic operations (get, put, remove, contains, clear) ##
```java

String key = "my_cache_key";
Integer value = ...; //expensive computation

//adding element to cache
cache.put(key, value);

//optionally you can provide other ttl
cache.put(key, value, 60*1000);

//checking if element exists and is valid
if (cache.contains(key)) ...;

//getting value rom cache
Integer cachedValue = cache.get(key);

//manually removing element from cache
cache.remove(key);

//manually clearing entire cache
cache.clear();
```