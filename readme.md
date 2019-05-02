## MovingAverageList Interface
`Double getMovingAverage(Integer n);`
- _Return the moving average for the last N elements_

`void add(E e);`
- _Adds an element to the datastructure_

`void addAll(Collection<? extends E> items);`
- _Adds the collection of elements given_

`E get(Integer i);`
- _Return the element at the specified index_

`List<? extends E> getList();`
- _Return all elements added_

## CircleBuffer
- Implementation of the MovingAverageList interface
- Uses an array to implement the interface
- The buffer is created with a fixed capacity
- Insertion of elements greater than the capacity will reuse the existing space by overwriting older elements
- Time complexity:
  -  `getMovingAverage(n)`  -> `O(n)`
  -  `add(E e)` -> `O(1)`
  -  `addAll()` -> `O(t)` where t is the size of elements to be added
  -  `get(Integer i)` -> `O(1)`
  -  `getList()` -> `O(n)`

## Design Question

#### Top Level design

##### Batching Layer
- Incoming data is ingested by a batch layer and stored into a storage layer (HDFS) and published to Kafka
  - Data is treated as immutable. This allows us to recompute our view/metrics
- The stored data is batch processed using a map reduce framework (Hadoop)
  - Data is processed to produce metrics with granularity of an hour and stored into a db (MongoDB)
    - i.e view count every hour
##### Serving Layer
- Data computed from the map reduce step is stored in a db (MongoDB)
  - Client application can query the db directly to get data in the granularity of an hour
##### Speed Layer
- The kafka stream is processed as streams using Storm
  - The processed data is stored into another db (Cassandra)
- The data computed is transient and will be invalidated eventually
  - Data older than 1 hour will be evicted as that data will be captured in the serving layer eventually

#### Client Application
- Queries the serving layer db and the speed layer db to serve the time series metrics
  - Will aggregate historical data from the serving layer and the latest data from the speed layer
- A simple nodejs/spring boot app that is load balanced and caches queries that it serves


1. Handle large write volume: Billions of write events per day
   - The batching layer can handle billions of write event per day. All incoming data is treated as immutable. Since we are not doing any incremental update there won't be any locking and versioning conflict to slow down the write process. The distributed file system can handle large amount of data
2. Handle large read/query volume: Millions of merchants wish to gain insight into their business. Read/Query patterns are time-series related metrics.
   - The serving layer and the speed layer combined can handle large queries or time series related metrics. The data are stored in horizontally scalable dbs.
3. Provide metrics to customers with at most one hour delay.
   - Data is processed by the batched layer with the granularity of an hour. In addition, to that the kafka stream is processed to store the latest data. This should be able to provide metrics with at most an hour delay.
4. Run with minimum downtime.
   - Since the different layer's are independent of each other it is fault tolerant. Any failure in the different layers would still make the system available with stale data.
5. Have the ability to reprocess historical data in case of bugs in the processing logic.
   - Since data is stored without incremental update it can be reprocessed to again
