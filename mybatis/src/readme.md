#### 简单配置
  * mybatis.mapper-locations=classpath*:mapper/**/*.xml
  * mybatis.type-aliases-package=类型别名包名
  * mybatis.type-handlers-package=TypeHandler扫描包名
  * mybatis.configuration.map-underscore-to-camel-case=true (下划线转换为驼峰规则)
  
#### Mapper的定义与扫描
   *  @MapperScan 配置扫描位置
   *  @Mapper 定义接口 
   *  映射的定义-- xml与注解