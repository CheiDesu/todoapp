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
2. 测试类


# 业务
1. 登录
    1. 过滤器实现 √
    2. 登出 √
    
2. 待办和用户的crud已完成
    1. 待办 cr (ud:未测试)
    2. 事件 r  (cud:未测试)
        1. 事件内待办 r cud(未测试)
    2. 用户 cru 
    3. 工作组 crud

3. 前端
    1. 数据校验
    2. api单独一个文件写一下
    3. 样式
    
    
# 一些说明

0. 待优化：
    1. 用sql语句代替接口调用，减少冗余代码

1. 接口文档：
    http://localhost:8080/doc.html#/home
    
    
2. 前端代码参考：
    https://blog.csdn.net/weixin_44816309/article/details/109677098