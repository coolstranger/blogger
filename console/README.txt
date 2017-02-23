It is a node.js server project. It requires node installed on machine and on path.

Execute: npm install

Edit public/connection.json
{
    "restHost" : "localhost",
    "restPort" : "8080",
    "restContext" : "/blogger",
    "adminHost" : "localhost",
    "adminPort" : "4040",
    "loginPage" : "http://localhost:4040/login.html"
}
restHost - where tomcat is runnning
restPort - tomcat port
restContext - Context on which war was deployed(default is blogger)
adminHost - where node server is running
adminPort - port on which node is running
loginPage - <NODE SERVER>/login.html


To run console service : node server.js

Hit http://host:4040 in your Browser and start publishing Blogs.
