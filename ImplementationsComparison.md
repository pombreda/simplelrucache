# Main types #

There are 2 main implementations available:
  * **ConcurrentLruCache** - highly concurrent implementation based on ConcurrentLinkedHashMap - should provide best results for large number of concurrent reads and writes
  * **SynchronizedLruCache** - simple synchronized implementation based on LinkedHashMap - should provide best results for low number of concurrent operations

# SoftReference versions #

There are also two versions which use SoftReferences to store values:

  * **SoftReferenceConcurrentLruCache**
  * **SoftReferenceSynchronizedLruCache**

Their performance specification is identical to main types.

Storing values as SoftReference has both benefits and drawbacks.

Most important advantage is ability to prevent OutOfMemoryError. When JVM realizes it starts to run out of memory it will try to reclaim memory from objects without strong references (stored as soft reference only).

However it is also possible that GC will reclaim memory even if JVM isn't running out of it. Also it is possible that most recently used entry will be reclaimed instead of last recently used. Therefore in future implementations SoftReference versions will manage collection of strong references for configurable percent of most recently used entries.