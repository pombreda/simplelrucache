# simplelrucache #


## Description ##
simplelrucache is library which provides simple, memory-only, threadsafe and fast implementations of LRU cache. Library itself is very lightweight, and it's only dependency is [concurrentlinkedhasmap](http://code.google.com/p/concurrentlinkedhashmap/) and slf4j-api

## Features ##
  * LRU policy
  * TTL support
  * Threadsafe
  * Lightweight (12.7kB without dependencies, 87.1kB with dependencies)
  * Possibility to store values as SoftReference so garbage collection will firstly reclaim memory from cache before OutOfMemoryError

## More info ##
  * [ImplementationsComparison](ImplementationsComparison.md)
  * [BasicUsage](BasicUsage.md)
  * [UsingAutoCallbacks](UsingAutoCallbacks.md)
  * [Dependencies](Dependencies.md)
  * MavenRepository

## Future ##
  * more implementations
  * support for tags
  * managing strong references for configurable number of most recently used entries so that SoftReference's of those entries won't be cleared in case of out of memory