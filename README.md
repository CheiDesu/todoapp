# 技术栈
1. springboot
    1. devtools
2. mybatis-plus
    1.  mybatis-plus-generator:逆向工程工具
3. 前端
    1. uni-app（暂定）
    2. element-ui
4. 其他工具：
    1. knife4j:生成接口文档
    2. fastjson:返回数据转换成json？
    3. lombok



# 初始化搭建
1. 数据库实现 √
2. mp逆向工程 √
3. 接口文档 √

# 调试
1. slf4j日志 √
2. 测试类 （可以先不写）


# 业务
0. 处理用户和待办的关系表

1. 登录
    1. 过滤器实现 √
    2. 登出 √
    
2. 待办和用户的crud已完成
    1. 待办 cru (d:未测试)
    2. 事件 r  (cud:未测试)
        1. 事件内待办 r (cud 未测试)
    2. 用户 crud （已测试）
    3. 工作组 crud （已测试）

3. 前端
    1. 数据校验
    2. api单独一个文件写一下
    3. 样式
    4. 普通用户和企业用户的前端权限认证
    
    
# 一些说明

0. 待优化：
    1. 用sql语句代替接口调用，减少冗余代码
    2. controller多次调用的代码块封装到service
    3. 用户密码加密认证（md5等）
    4. 存在bean相互循环依赖的问题。。。
    5. 前端的日历组件存在显示不完全的问题

1. 接口文档：
    http://localhost:8081/doc.html#/home
    
    
2. 前端代码参考：
    https://blog.csdn.net/weixin_44816309/article/details/109677098


# 使用说明：
前端服务器根目录：http://localhost:8080

登陆页面:http://localhost:8080/login

登陆之后会跳转到home界面，展示各个大todo，可在该页面进行增删查操作

![](https://picgo-1304285457.cos.ap-guangzhou.myqcloud.com/images/20220606135143.png)

单击其中的大todo，即可进入该大todo的具体页面：/todo 展示了大todo下的各个待办事项

![](https://picgo-1304285457.cos.ap-guangzhou.myqcloud.com/images/20220606135157.png)

各个页面的右上角可以随时进行logout

![](https://picgo-1304285457.cos.ap-guangzhou.myqcloud.com/images/20220606135240.png)

