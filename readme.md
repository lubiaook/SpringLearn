### Spring noSql实践

#### docker 安装以及使用 
* 容器 不包含操作系统比虚拟机要轻量级
#### 认识docker

#### 不同用户的docker 

#### docker 常用命令
* ***镜像相关***
  * `docker pull <inager>`
  * `docker searcher <image>` 
* ***容器相关***
  * `docker run`
  * `docker start/stop <容器名称>` :启动、停止
  * `docker ps <容器名>`
  * `docker logs <容器名>`
#### docker run 的常用选项
***docker run [OPTIONS ] IMAGE[COMMOND][ARG...]
**选项说明**
* -d 后台运行容器
* -e 设置环境变量
* --expose /-p 宿主机端口：容器端口
* --link 连接不同容器
* --name 指定容器名称
* -v 数值目录：容器目录,挂载自判卷:
#### 国内Docker镜像配置
* ***官方Docker Hub*** 
  * https://hub.docker.com
* ***官方镜像***
   * 镜像 https://www.docker-cn.com/registry-mirror 
   * 下载 https://www.docker-cn.com/get-docker
* ***阿里云镜像***
   * https://dev.aliyun.com


#### 通过Docker启动MongoDB
*  ##### 官方指引
  > * https://hub.docker.com/_/mongo
*  ##### **获取镜像** 
  > * `docker pull mongo`
*  ##### **运行MongoDB镜像** 
  > * `docker run --name mongo -p 27017:27017 -v ~/docker-data/mongo:/data/db -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGODB_INITDB_ROOT_PASSWORD=admin -d mongo`
*  登录docker访问mongo
  > *  `docker exec -it mongo bash` ->登录docker 
  > *  `mongo -u admin -p admin`    -> 访问 mongodb 
  > *  `show dbs`                   -> 查看 数据库 
  > *  `use springbucks`            -> 查看 具体数据库 
  > *  `db.文档名称.find()`            ->查询语句 
  > *  `db.文档名称.remove({"name":"拿铁"})`  ->删除语句 



####  Spring 对MogoDB的支持
* **MongoDB是一款开源的文档型** 
* 数据库类型
   > * key-value redis 
   > * 文档型 mongdb  cochbase
   > * 列出错 hbase
   > * 图数据库 new forhow
   > * new sql pdb codb(google) 

* **Spring Data MongoDB的支持**
     * 注解  
       *  `@Document`
       *  `@Id`
     * MongoTemplate
          *   `save/remove`
          *   `Criteria /Quary/Update`
 
* **Spring Data MongoDB的Repository**
> * **`@EnableMongoRepositories`**
   >  * 注解在Application上 
> * ***对应的接口*** 
   >  * `MongoRepository<T,ID>`
   >  * `PagingAndSortingRepository<T,ID>`
   >  * `CrudRepository<T,ID>`


#### Spring对Redis的支持(缓存使用)

 * Redis 是一款开源的内存KV存储，支持多种数据结构
    > * https://redis.io
 * **Spring 对Redis的支持**
    > * Spring Data Redis 
      >>  * 支持客户端Jedis / Lettuce              
      >>  * RedisTemplate
      >>  * Repository 支持
 * Jesid实例不是线程不安全：我们不能多个线程中共享同一个Jedis实例
 * 通过JedisPool获得Jedis实例
 * 直接使用Jedis中的方法
 * Jedis 客户端的简单操作Redis 
 
#### Jedis 客户端的简单使用
```java
@Bean
@ConfigurationProperties("redis")
public JedisPoolCoinfig jedisPoolConfig(){
 return new JedisPoolConfig();
}
@Bean()
public JedisPool jedisPool(@value("$redis.host}")String host){
return  new JedisPool(jedisPoolConfig,host);
}
```
#### **通过Docker启动Redis**
* **官方指引**
  > * https://hub.docker.com/_/redis
* **docker 镜像获取**
  > * docker pull redis 
* **启动Redis**
  > * docker run --name redis -d -p 6379:6379 redis
* **docker中访问redis**

  > * docker exec -it redis bash 
  > * redis-cli 
  > * key* 
  > * HGETALL 
   


#### Redis线上部署方案 
   *  **哨兵模式**
      * **Redis Sentinel是Redis的一种高可用的方案**
         > *  监控、通知、自动故障转移、服务发现
      * **JedisSentinelPool** 
      *             +----+ 
                    | M1 | 
                    | S1 | 
                    +----+ 
                      | 
                      | 
            +----+    |      +----+
            | R2 |----+------| R3 | 
            | S2 |           | S3 |
            +----+           +----+
            Configuration:quorum
   * ***集群模式***
      * **单节点模式(非集群模式)对单节点可以批量操作。集群模式不允许批量操作**
      * **Redis Cluster**
         > * 自然数据自动分片（分成16348个hash Slot）
         > * 在部分节点失效时有一定时效性 
      * **RedisCluster**
         > * Jedis只从Master读取数据，如果要想要读写分离、可定制 
   
#### 了解Spring的缓存抽象
##### 哪些缓存需要写在代码里 哪些需要用Redis?
    *  一天内无变化，长时间没变化。直接放缓存
    *  缓存内部具备一致性，多个机器需要获取一直的缓存
    *  数据的读写比 写一次读十次需要加缓存 读写频率过高不需要使用缓存
##### *为不同的缓存提供一层抽象*
   1. 为java方法增加缓存,缓存执行结果
   2. 支持ConcurrentMap 、EhCache、JCache(JSR-107)
   3. 支持ConcurrentMap 、EhCache、JCache(JSR-107)
   4. 接口 
       * org.springframework.cache.Cache
       * org.springframework.cache.CacheManager
 ##### **基于注解的缓存**
 *  ***@EnableConfig***
   > * @Cacheable 直接重换从里去
   > * @CacheEvict 直接清理
   > * @CachePut   做缓存设置
   > * @Caching   对缓存打包 可做多个
   > * @CacheConig 
#### Redis在Spring中的其他用法


##### 与Redis建立连接
 **配置连接工厂**
  * **LettuceConnectionFactory与JedisConnectionFactory**
    >  * RedisStandaloneConfiguration 
    >  * RedisSentinelConfiguration
    >  * ResidClusterConfiguration 

 **RedisTemplate**
  
  * **RedisTemplate<K,V>**
    >  * opsForxxx() 
  * **StringRedisTemplate**
 ***一定要设置过期时间***
    
 **Resid Repository**
  * **实体注释**
    >  * RedisHash ->对应entity 
    >  * Id 
    >  * Indexed   ->对应二级索引 
    
 **处理不同类型数据源的Repository**
  * **如何区分这些Repository***
  > * 根据实体的注解
  > * 根据继承的接口类型
  > * 扫描不同的包 
  
### 数据访问进阶
##### Project Reactor 


 
 **配置连接工厂**
#### [左耳听风](https://time.geekbang.org/column/intro/48?utm_source=geektimeweb&utm_medium=pc&utm_term=pc_interstitial_233)

### 如何阅读官方文档
* [打开Spring官方文档](https://spring.io) 
* 点击【**project**】
