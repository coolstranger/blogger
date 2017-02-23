It is a maven Project. To build the project Maven must be installed on the system.

Build Project : mvn clean package

Build File : web/target/blogger.war is the final build file.

Setup Instructions:

It needs a MySql database. And requires a Datasource with JNDI name jdbc/BLOGDS.

If deployed on tomcat fallow these Instructions:
  - Edit conf/context.xml, Add fallowing xml snippet:
                     <Resource name="jdbc/BLOGDS" auth="Container" type="javax.sql.DataSource"
                                  maxActive="100" maxIdle="30" maxWait="10000"
                                  username="root" password="password" driverClassName="com.mysql.jdbc.Driver"
                                  url="jdbc:mysql://localhost:3306/blog"/>

  - Modify user, password, and url according to your environment.
  - Download this file http://central.maven.org/maven2/mysql/mysql-connector-java/5.1.40/mysql-connector-java-5.1.40.jar
  - Put the above Downloaded file in tomcat/lib folder
  - copy web/target/blogger.war into tomcat/webapps folder
  - start tomcat
  - system will self bootstrap.
