# SpringBootSecurityThymeleaf DEMO

![Screenshot1 from running application](screenshot1.jpeg)
![Screenshot2 from running application](screenshot2.jpeg)

## 关于

该DEMO是一个基于Spring Boot & Spring Security & Thymeleaf & Bootstrap 
实现用户手机号+验证码注册登录或者手机号+密码登录的登录系统。

## 使用

运行mvn命令： ```mvn spring-boot:run``` 启动程序。打开浏览器前往：http://localhost:8080 查看DEMO

该DEMO利用H2数据库作为项目的数据库，在项目启动时已经写入了一个测试账号（手机号：100，密码：password），加密方法用的是Bcrypt，
利用在线小工具[Bcrypt Generator](https://www.bcrypt-generator.com)你也可以哈希自己想要的密码。

该DEMO有以下入口：

```
/ 或 /index - 返回index页面，无需登录。
/user - 用户个人中心，需登录。
/signup - 登录界面：手机号+验证码。如果用户不存在则新注册该用户；如果用户已存在则登录。
/login - 登录界面：手机号+密码。只适用于已存在的用户登录。
```

