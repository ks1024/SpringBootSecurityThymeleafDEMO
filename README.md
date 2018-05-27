# SpringBootSecurityThymeleaf DEMO

实现目前对用户比较友好的快速登录方式： 
- 手机号+验证码进行注册或登录
- 如果已有密码，则可以手机号+密码登录

Run ```mvn spring-boot:run``` 

Go to http://localhost:8080 查看DEMO

Go to http://localhost:8080/h2_console 查看H2数据库

在程序初始阶段，往H2数据库中插入了一个已有密码的测试用户账户（手机号：100，密码：password）

该DEMO中可以学到：

- 如何自定义LoginFilter
- 如何实现2个不同的登录界面
