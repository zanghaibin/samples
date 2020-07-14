### **erlang和rabbitmq的版本对应关系**

https://www.rabbitmq.com/which-erlang.html

### **1. 安装erlang环境**

https://dl.bintray.com/rabbitmq-erlang/rpm/erlang/

######安装依赖

`yum -y install gcc glibc-devel make ncurses-devel openssl-devel xmlto perl wget gtk2-devel binutils-devel
`
###### 下载后上传服务器，执行安装命令

`rpm -ivh erlang-23.0.2-1.el8.x86_64.rpm`

######验证是否安装成功

`erl -v`


### **1. 安装RabbitMQ**

下载地址 https://www.rabbitmq.com/install-rpm.html#downloads

######下载后执行安装
`yum install rabbitmq-server-3.8.5-1.el7.noarch.rpm`

######配置用户及远程连接访问

######添加开机启动RabbitMQ服务

`sudo chkconfig rabbitmq-server on  `

######启动服务

`sudo /sbin/service rabbitmq-server start `

######查看当前所有用户

`sudo rabbitmqctl list_users`

######由于RabbitMQ默认的账号用户名和密码都是guest。为了安全起见, 先删掉默认用户

`sudo rabbitmqctl delete_user guest`

######添加新用户 root 密码 111111

`sudo rabbitmqctl add_user root 111111`

######设置用户tag

`sudo rabbitmqctl set_user_tags root administrator`

######赋予用户默认vhost的全部操作权限

`sudo rabbitmqctl set_permissions -p / root ".*" ".*" ".*"`

######查看用户的权限

`sudo rabbitmqctl list_user_permissions root
`

######开启web管理接口

`sudo rabbitmq-plugins enable rabbitmq_management`

######将5672端口加入防火墙白名单

`firewall-cmd --permanent --add-port=5672/tcp`
   
`firewall-cmd --reload`

http://ip:15672

######添加开机启动RabbitMQ服务

`sudo chkconfig rabbitmq-server on
`
######启动服务

`sudo /sbin/service rabbitmq-server start 
`
######查看服务状态

`sudo /sbin/service rabbitmq-server status ` 

######停止服务

`sudo /sbin/service rabbitmq-server stop  ` 




2.开放指定端口
firewall-cmd --zone=public --add-port=80/tcp --permanent   //开放端口
firewall-cmd --reload                                                                   //重新载入，使其生效
3.关闭指定端口
firewall-cmd --zone=public --remove-port=80/tcp --permanent            //关闭端口
firewall-cmd --reload                                                                   //重新载入，使其生效
4.查看端口状态
firewall-cmd --zone=public --query-port=80/tcp                            //查看端口状态


参考链接：https://blog.csdn.net/shuai8624/article/details/106842595












