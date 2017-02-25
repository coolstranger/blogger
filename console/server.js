/************************************************
        STRING CONSTANTS
************************************************/
var base64 = 'base64';
var utf8 = 'utf8';

var GET = 'GET';
var POST = 'POST';
var PUT = 'PUT';
var DELETE = 'DELETE';

var COOKIE = 'Cookie';
var SET_COOKIE = 'set-cookie';
var CONTENT_TYPE = "content-type";
var APPLICATION_JSON = "application/json";
var AUTH_TOKEN = 'BLOGGER_AUTH_TOKEN';

var HTTP_OK = 200;
var HTTP_UNAUTORIZED = 401;
var HTTP_ERROR = 500;


/************************************************
        URL's
 ************************************************/
var ROOT = "/";
var LOGIN = "/login";
var RESPONSE = "/response";
var CREDENTIALS = "/credentials";
var SESSION = "/session";
var USER = "/user";
var BLOG = "/blog";
var BLOGS = "/blogs";
var DRAFTS = "/drafts";
var PUBLISH = "/publish";
var COMMENT = "/comment";
var SEARCH = "/search";
var LOGOUT = "/logout";
var LOGIN_PAGE = "login.html";
var HOME_PAGE = "index.html";

/************************************************
        NODE MODULES
 ************************************************/
var express = require('express');
var http = require('http');
var json = require('jsonfile');
var bodyParser = require('body-parser');
var session = require('express-session');
var uuid = require('node-uuid');
var fs = require("fs");


var app = express();
var config;



app.use(express.static('public'));
app.use( bodyParser.json() );
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(session({
    secret: uuid.v4()
}));

json.readFile('public/connection.json', function(err, data){
    config = data;
});


/************************************************
        END POINTS
 ************************************************/

/************************************************
 Endpoint   :   /
 Method     :   GET
 Desc       :   Login Page Request
 ************************************************/
app.get(ROOT, function(req, res){
    res.redirect(HOME_PAGE);
});

/************************************************
 Endpoint   :   /login
 Method     :   POST
 Desc       :   For making user/password Authentication on Rest Server
************************************************/
app.post(LOGIN , function(req, res){
    makeRequest(req, res, LOGIN, POST, req.body);
});



/************************************************
 Endpoint   :   /response
 Method     :   POST
 Desc       :   For Receiving Response of Login
 ************************************************/
app.post(RESPONSE, function(req, res){

    var token = req.body.token;
    var status = req.body.status;

    if(status==200){
        req.session.token = token;
        res.redirect(HOME_PAGE);
    }else{
        res.redirect(LOGIN_PAGE);
    }
});

/************************************************
 Endpoint   :   /session
 Method     :   GET
 Desc       :   For Getting Login User
 ************************************************/
app.get(SESSION, function(req, res){
    if(req.session.token) {
        makeGETRequestWithAuth(req,res,SESSION);
    }else{
        res.status(HTTP_UNAUTORIZED).end("Invalid Session");
    }
});

/************************************************
 Endpoint   :   /logout
 Method     :   GET
 Desc       :   For Logout
 ************************************************/
app.get(LOGOUT, function(req, res){
    if(req.session.token) {
        req.session.destroy();
    }
    res.end();
});


/************************************************
 Endpoint   :   /search
 Method     :   GET
 Desc       :   For Getting Search Result
 ************************************************/
app.get(SEARCH, function(req, res){
    var append = false;
    var url = SEARCH;
    if(req.query.keyword){
        url += "?keyword=" + encodeURIComponent(req.query.keyword);
        append = true;
    }
    if(req.query.lastEntity){
        if(append==true)
            url+="&";
        else {
            url += "?";
            append = true;
        }
        url += "last_entity=" + encodeURIComponent(req.query.lastEntity);
    }
    if(req.query.prev){
        if(append==true)
            url+="&";
        else {
            url += "?";
            append = true;
        }
        url += "prev=" + encodeURIComponent(req.query.prev);
    }

    makeGETRequest(req,res,url);
});


/************************************************
 Endpoint   :   /credentials
 Method     :   PUT
 Desc       :   For Changing Password
 ************************************************/
app.put(CREDENTIALS, function (req, res) {
    if(req.session.token) {
        makeRequestWithAuth(req,res,CREDENTIALS, PUT, req.body);
    }else{
        res.status(HTTP_UNAUTORIZED).end("Invalid Session");
    }

});



/************************************************
 Endpoint   :   /user
 Method     :   GET
 Desc       :   For getting User
 ************************************************/
app.get(USER, function (req, res) {
    if(req.session.token) {
        var id = req.query.id;
        var url = USER + "?id=" + encodeURIComponent(id);
        makeGETRequest(req,res,url);
    }else{
        res.status(HTTP_UNAUTORIZED).end("Invalid Session");
    }

});

/************************************************
 Endpoint   :   /user
 Method     :   POST
 Desc       :   For creating User
************************************************/
app.post(USER, function (req, res) {
    makeRequest(req,res,USER, POST, req.body);

});

/************************************************
 Endpoint   :   /user
 Method     :   PUT
 Desc       :   For updating User
 ************************************************/
app.put(USER, function (req, res) {
    if(req.session.token) {
        makeRequest(req,res,USER, PUT, req.body);
    }else{
        res.status(HTTP_UNAUTORIZED).end("Invalid Session");
    }

});

/************************************************
 Endpoint   :   /blog
 Method     :   POST
 Desc       :   For creating Blog
 ************************************************/
app.post(BLOG, function (req, res) {
    if(req.session.token) {
        makeRequestWithAuth(req,res,BLOG, POST, req.body);
    }else{
        res.status(HTTP_UNAUTORIZED).end("Invalid Session");
    }

});

/************************************************
 Endpoint   :   /blog
 Method     :   PUT
 Desc       :   For updating Blog
 ************************************************/
app.put(BLOG, function (req, res) {
    if(req.session.token) {
        makeRequestWithAuth(req,res,BLOG, PUT, req.body);
    }else{
        res.status(HTTP_UNAUTORIZED).end("Invalid Session");
    }

});

/************************************************
 Endpoint   :   /blog
 Method     :   DELETE
 Desc       :   For updating Blog
 ************************************************/
app.delete(BLOG, function (req, res) {
    if(req.session.token) {
        makeRequestWithAuth(req,res,BLOG, DELETE, req.body);
    }else{
        res.status(HTTP_UNAUTORIZED).end("Invalid Session");
    }

});
/************************************************
 Endpoint   :   /blog
 Method     :   GET
 Desc       :   For Getting Blog
 ************************************************/
app.get(BLOG, function (req, res) {
        var url = BLOG + "?id=" + req.query.id;

        makeGETRequest(req,res,url);

});

/************************************************
 Endpoint   :   /blogs
 Method     :   GET
 Desc       :   For Getting User Blogs
 ************************************************/
app.get(BLOGS, function (req, res) {
    var append = false;
    var url = BLOGS;
    if(req.query.lastEntity){
        if(append==true)
            url+="&";
        else {
            url += "?";
            append = true;
        }
        url += "last_entity=" + encodeURIComponent(req.query.lastEntity);
    }
    if(req.query.prev){
        if(append==true)
            url+="&";
        else {
            url += "?";
            append = true;
        }
        url += "prev=" + encodeURIComponent(req.query.prev);
    }

    makeGETRequestWithAuth(req,res,url);

});

/************************************************
 Endpoint   :   /drafts
 Method     :   GET
 Desc       :   For Getting User Draft Blogs
 ************************************************/
app.get(DRAFTS, function (req, res) {
    var append = false;
    var url = DRAFTS;
    if(req.query.lastEntity){
        if(append==true)
            url+="&";
        else {
            url += "?";
            append = true;
        }
        url += "last_entity=" + encodeURIComponent(req.query.lastEntity);
    }
    if(req.query.prev){
        if(append==true)
            url+="&";
        else {
            url += "?";
            append = true;
        }
        url += "prev=" + encodeURIComponent(req.query.prev);
    }

    makeGETRequestWithAuth(req,res,url);

});

/************************************************
 Endpoint   :   /publish
 Method     :   PUT
 Desc       :   For Publishing Blog
 ************************************************/
app.put(PUBLISH, function (req, res) {
    if(req.session.token) {
        makeRequestWithAuth(req,res,PUBLISH, PUT, req.body);
    }else{
        res.status(HTTP_UNAUTORIZED).end("Invalid Session");
    }

});


/************************************************
 Endpoint   :   /comment
 Method     :   GET
 Desc       :   For Getting Comments
 ************************************************/
app.get(COMMENT, function (req, res) {
    var url = COMMENT + "?id=" + req.query.id;
    makeGETRequest(req,res,url);
});

/************************************************
 Endpoint   :   /comment
 Method     :   POST
 Desc       :   For adding Comments
 ************************************************/
app.post(COMMENT, function (req, res) {
    if(req.session.token) {
        makeRequestWithAuth(req,res,COMMENT, POST, req.body);
    }else{
        res.status(HTTP_UNAUTORIZED).end("Invalid Session");
    }

});

var server = app.listen(4040, function(){
    console.log("Server Running at 4040");
});

/************************************************
            UTILITY FUNCTIONS
************************************************/

function makeGETRequest(req, res, url){
    var status, out='', error;
    var options = {
        host: config.restHost,
        port: config.restPort,
        path: config.restContext + url,
        method: GET
    };

    try {

        var sreq = http.request(options, function (sres) {

            sres.on("data", function (chunk) {
                status = sres.statusCode;
                if (status == HTTP_OK) {
                    out += chunk.toString(utf8);
                } else {
                    try {
                        error = JSON.parse(chunk.toString(utf8));
                    } catch (err) {
                        error = "Error in making request";
                    }
                    console.log(error);
                }
            });

            sres.on("end", function () {
                if (status == HTTP_OK) {
                    res.end(out);
                } else {
                    if (status == HTTP_UNAUTORIZED) {
                        res.status(HTTP_UNAUTORIZED).end("Invalid Session");
                    } else if (status == HTTP_ERROR) {
                        res.status(HTTP_ERROR).end(JSON.stringify(error));
                    } else {
                        res.status(status).end(JSON.stringify(error));
                    }
                }
            });

            sres.on("error", function (err) {
                console.log(err.stack);
                res.status(HTTP_ERROR).end();
            });

        });

        sreq.end();
    }catch(err){
        res.status(HTTP_ERROR).end(error);
    }
}

function makeGETRequestWithAuth(req, res, url){
    var status, out='', error;
    var options = {
        host: config.restHost,
        port: config.restPort,
        path: config.restContext + url,
        method: GET,
        headers: {
            COOKIE: AUTH_TOKEN + "=" + req.session.token
        }
    };

    try {

        var sreq = http.request(options, function (sres) {

            sres.on("data", function (chunk) {
                status = sres.statusCode;
                if (status == HTTP_OK) {
                    out += chunk.toString(utf8);
                } else {
                    try {
                        error = JSON.parse(chunk.toString(utf8));
                    } catch (err) {
                        error = "Error in making request";
                    }
                    console.log(error);
                }
            });

            sres.on("end", function () {
                if (status == HTTP_OK) {
                    res.end(out);
                } else {
                    if (status == HTTP_UNAUTORIZED) {
                        res.status(HTTP_UNAUTORIZED).end("Invalid Session");
                    } else if (status == HTTP_ERROR) {
                        res.status(HTTP_ERROR).end(JSON.stringify(error));
                    } else {
                        res.status(status).end(JSON.stringify(error));
                    }
                }
            });

            sres.on("error", function (err) {
                console.log(err.stack);
                res.status(HTTP_ERROR).end();
            });

        });

        sreq.end();
    }catch(err){
        res.status(HTTP_ERROR).end(error);
    }
}

function makeRequest(req, res, url, method, body){
    var payload = JSON.stringify(body);
    var status, out, error;
    var options = {
        host: config.restHost,
        port: config.restPort,
        path: config.restContext + url,
        method: method,
        headers: {
            "content-type" : APPLICATION_JSON,
            "content-length" : payload.length
        }
    };

    try {
        var sreq = http.request(options, function (sres) {

            sres.on("data", function (chunk) {
                try {
                    status = sres.statusCode;
                    if (status == HTTP_OK) {
                        out = chunk.toString(utf8);
                    } else {
                        try {
                            error = JSON.parse(chunk.toString(utf8));
                        } catch (err) {
                            error = "Error in making request";
                        }
                        console.log(error);
                    }
                } catch (err) {
                    console.log(err);
                }
            });

            sres.on("end", function () {
                status = sres.statusCode;
                if (status == HTTP_OK) {
                    if (out) {
                        res.end(out);
                    } else {
                        res.end();
                    }
                } else {
                    if (status == HTTP_UNAUTORIZED) {
                        res.status(HTTP_UNAUTORIZED).end("Invalid Session");
                    } else if (status == HTTP_ERROR) {
                        res.status(HTTP_ERROR).end(JSON.stringify(error));
                    } else {
                        res.status(status).end(JSON.stringify(error));
                    }
                }
            });

            sres.on("error", function (err) {
                console.log(err.stack);
                res.status(HTTP_ERROR).end();
            });
        });

        sreq.write(payload);

        sreq.end();
    }catch(err){
        res.status(HTTP_ERROR).end(error);
    }
}

function makeRequestWithAuth(req, res, url, method, body){
    var payload = JSON.stringify(body);
    var status, out, error;
    var options = {
        host: config.restHost,
        port: config.restPort,
        path: config.restContext + url,
        method: method,
        headers: {
            COOKIE: AUTH_TOKEN + "=" + req.session.token,
            "content-type" : APPLICATION_JSON,
            "content-length" : payload.length
        }
    };

    try {
        var sreq = http.request(options, function (sres) {

            sres.on("data", function (chunk) {
                try {
                    status = sres.statusCode;
                    if (status == HTTP_OK) {
                        out = chunk.toString(utf8);
                    } else {
                        try {
                            error = JSON.parse(chunk.toString(utf8));
                        } catch (err) {
                            error = "Error in making request";
                        }
                        console.log(error);
                    }
                } catch (err) {
                    console.log(err);
                }
            });

            sres.on("end", function () {
                status = sres.statusCode;
                if (status == HTTP_OK) {
                    if (out) {
                        res.end(out);
                    } else {
                        res.end();
                    }
                } else {
                    if (status == HTTP_UNAUTORIZED) {
                        res.status(HTTP_UNAUTORIZED).end("Invalid Session");
                    } else if (status == HTTP_ERROR) {
                        res.status(HTTP_ERROR).end(JSON.stringify(error));
                    } else {
                        res.status(status).end(JSON.stringify(error));
                    }
                }
            });

            sres.on("error", function (err) {
                console.log(err.stack);
                res.status(HTTP_ERROR).end();
            });
        });

        sreq.write(payload);

        sreq.end();
    }catch(err){
        res.status(HTTP_ERROR).end(error);
    }
}
