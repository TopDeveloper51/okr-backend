# OKRapi

This is a SpringBoot-Mybatis application providing a REST API to a DataMapper-backend model for OKR-e project.

## Install

* Ensure JAVA_HOME environment variable is set and points to your [Java SE JDK and JRE 8.291](https://www.techspot.com/downloads/5198-java-jre.html)  
	
* Download Maven latest Maven software from [Download latest version of Maven](http://maven.apache.org/download.cgi)

* Add MAVEN_HOME in environment variable to '...\apache-maven-3.1.1\bin'

* Install [Mysql 5.7](https://cdn.mysql.com//Downloads/MySQLInstaller/mysql-installer-community-5.7.34.0.msi) and run service

### Install on CentOS 7 or 8
	
* Installing OpenJDK 8

	$ sudo dnf install java-1.8.0-openjdk-devel

* Installing Maven Apatch
	
	$ sudo dnf install maven
	
* Installing Mysql server

	$  yum install  -y  mysql-community-server
	
#####	 Enter mysql cli

	$ mysql -u root -p
	
#####	 Create a new User for okr

	mysql> CREATE USER 'okr'@'%' IDENTIFIED BY 'user_password';
	
#####	 Import and Run the query script file

	mysql> source okr_db.sql;
	
#####	 Set privilage to the user

	mysql> GRANT ALL PRIVILEGES ON okr_db.* TO 'okr'@'%';
	
	
## Set the Environment

set environment configuration on "src/main/resource/application.properties"
	
	spring.datasource.url = jdbc:mysql://10.10.12.17:3306/okr_db
	spring.datasource.username = okr
	spring.datasource.password = 123!@#qweQWE
	server.address=10.10.12.17
	server.port = 8085
	
## Build the app

    mvn clean install 

## Run the service on tomcat

	mvn spring-boot:run

or

    java -jar .\target\okre-1.0.0.jar

## Run the service on Linux system

Make the service of okr project

	$ nano /etc/systemd/system/okre.service

Insert follow information
	
	[Unit]
	Description= My Spring Project
	[Service]
	User=ubuntu
	Group=ubuntu
	Type=simple
	ExecStart=/usr/bin/java -jar /home/erik/a703/okre/target/okre-1.0.0.jar
	SuccessExitStatus=143
	[Install]
	WantedBy=multi-user.target

Enable and start service

	systemctl enable okre
	
	systemctl start okre
	
	sudo systemctl status app
	
	systemctl daemon-reload
	
	systemctl restart okre
	
	
----------------

# REST API

The REST API to the app is described below.

	The Base Url is http://{server.address}:{server.port}/
