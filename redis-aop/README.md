# spring-redis-aop
redis客户端集成spring实现aop方式,方便在程序中使用spring的时候更加方便的使用redis内存数据库，
采用aop的方式主要是为了不去污染业务代码，达到业务逻辑更加清晰易于阅读，在使用过程中可以设置
key的有效时间，也可以开启或者关闭某一个方法的缓存使用（有效方便测试）。也可以通过配置来确定
所有注解过的方法是否使用缓存，在选择存储类型的时候，可以选择String或者Map形势（Set和List还没
有实现，可以自行实现）使用还是很方便的哈