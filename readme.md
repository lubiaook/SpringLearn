### Spring noSql实践

#### docker 安装以及使用 
* 容器 不包含操作系统比虚拟机要轻量级
#### 认识docker

#### 

#### mogodb

#### Spring对Redis的支持(缓存使用)

 * Redis 是一款开源的内存KV存储，支持多种数据结构
 
 Jedis 客户端的简单使用 
 * 线程不安全
 
#### Redis线上部署方案 
   *  哨兵模式
   *  集群模式
   
#### 了解Spring的缓存抽象
##### 哪些缓存需要写在代码里 哪些需要用Redis?
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
***配置连接工厂***
* LettuceConnectionFactory 与JedisConnectionFactory
  * RedisStandaloneConfiguration 
  * RedisSentinelConfiguration
  * ResidClusterConfiguration 

###### ***处理不同类型数据源的Repository***
***如何区分这些Repository***
* 根据实体的注解
* 根据继承的接口类型
* 扫描不同的包 

