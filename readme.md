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
> * ***对应的接口*** 
   >  * `MongoRepository<T,ID>`
   >  * `PagingAndSortingRepository<T,ID>`
   >  * `CrudRepository<T,ID>`
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

