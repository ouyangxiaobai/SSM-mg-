# 项目名称

SSM框架的科普有毒蘑菇网站系统源码+数据库+完整安装视频

# 系统介绍
用户测试账号 test 123456
管理员测试账号 admin 123456
数据库每个表都有详细注释
代码也有详细注释（详细说明）
前段使用技术：html+JavaScript+css+layui+jQuery

网站前端：http://localhost:8080/CommonWeb/pc/index
网站后台:http://localhost:8080/CommonWeb/manage/login
4.系统实现（基础代码，业务功能代码的编写）
com 项目包结构说明
└─module
├─controller 控制层，负责请求的处理，数据库的操作调用
├─mapper 数据库操作接口，sql文件在xml中的配置
├─pojo 数据库对应实体类，用来和数据库表实现映射关系
└─util java中常见工具类的存放

所有WEB-INF/view/pc文件夹中的都是前端相关jsp页面
login.jsp 登录页面
register.jsp 注册页面
index.jsp 首页
code.jsp 验证码生成页面
liuyan.jsp 留言添加显示页面

所有WEB-INF/view/manage文件夹中的都是后台管理相关jsp页面，按照文件夹进行划分表，
每一个文件夹对应一张表的操作，例如userinfo是用户表的所有管理操作。

# 环境需要

1.运行环境：最好是java jdk 1.8，我们在这个平台上运行的。其他版本理论上也可以。\
2.IDE环境：IDEA，Eclipse,Myeclipse都可以。推荐IDEA;\
3.tomcat环境：Tomcat 7.x,8.x,9.x版本均可\
4.硬件环境：windows 7/8/10 1G内存以上；或者 Mac OS； \
5.数据库：MySql 5.7版本；\
6.是否Maven项目：否；

# 技术栈

1. 后端：Spring+SpringMVC+Mybatis\
2. 前端：JSP+CSS+JavaScript+jQuery

# 使用说明

1. 使用Navicat或者其它工具，在mysql中创建对应名称的数据库，并导入项目的sql文件；\
2. 使用IDEA/Eclipse/MyEclipse导入项目，Eclipse/MyEclipse导入时，若为maven项目请选择maven;\
若为maven项目，导入成功后请执行maven clean;maven install命令，然后运行；\
3. 将项目中springmvc-servlet.xml配置文件中的数据库配置改为自己的配置;\
4. 运行项目，在浏览器中输入http://localhost:8080/ 登录

# 高清视频演示

https://www.bilibili.com/video/BV1tY4y137r1/

> # **数据库及资料有偿获取：QQ:3484724101**

​