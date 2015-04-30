## Overview ##

LruCache provides additional get versions which allow to provide auto callback implementation:

```java

public V get(K key, Callable<V> callable) throws Exception;
public V get(K key, Callable<V> callable, long ttl) throws Exception;
```

Get with callable tries to get value from cache and if it fails it uses callable to generate value, stores it in cache and returns.

## Example ##

Retrieving data from URLs is time expensive in synchronous code and sometimes it's worth caching. Let's imagine you want to cache URL.getContent() calls for desired amount of time (let's say 5 minutes).

Let's define Callable which would retrieve contents from URL

```java

class UrlContentGetter implements Callable<Object> {
private URL url;

UrlContentGetter(URL url) {
this.url = url;
}

@Override
public Object call() throws Exception {
return url.getContent();
}
}
```

Usage:

```java

//create cache which would store up to 200 contents for max time of 5 minutes each
LruCache<URL, Object> urlContentCache = new ConcurrentLruCache<URL, Object>(200, 60*5*100);

//example URL
URL url = new URL("http://code.google.com/p/simplelrucache/");

//create content getter
Callable<Object> contentGetter = new UrlContentGetter(url);

Object content = null;

//this call will use callable to retrieve content
content = cache.get(url, contentGetter);

//these calls will retrieve content directly from cache
content = cache.get(url, contentGetter);
content = cache.get(url);

//sleep more than 5 minutes
Thread.sleep(6*60*1000);

//this call will use callable to retrieve content because cache entry has expired
content = cache.get(url, contentGetter);
```