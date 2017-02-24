It is a node.js server project. It requires node installed on machine and on path.

Execute: npm install

Edit public/connection.json
{
    "restHost" : "localhost",
    "restPort" : "8080",
    "restContext" : "/blogger"
}
restHost - where tomcat is runnning
restPort - tomcat port
restContext - Context on which war was deployed(default is blogger)

To run console service : node server.js

Hit http://host:4040 in your Browser and start publishing Blogs.
