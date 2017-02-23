var config;
var strings;
function init(){
    $.get("/labels/strings.json", function(data, status){
        strings = data;
    });

    $.get("/connection.json", function(data, status){
        config = data;
        displayHomePage();
    });
}

var displayHomePage = function (){

    $.get("/home.html", function(display,status){

        $("#contentWrapper").empty();
        $("#contentWrapper").append(display);

        $.get("/session", function(data,status){
            if(status=="success") {
                var user = JSON.parse(data);
                $('#loginTab').hide();
                $("#createBlogTab").show();
                $("#logoutTab").show();
                $("#myBlogsTab").show();
                $("#myDraftsTab").show();
                $("#changePasswordTab").show();

                $("#userInfoTable").show()
                $("#nameCol").append(user.firstName);
                var date = new Date(user.lastLoginTime);
                $("#timeCol").append(date.toString());
            }
        }).fail(function(error){
            $('#loginTab').show();
            $("#createBlogTab").hide();
            $("#logoutTab").hide();
            $("#myBlogsTab").hide();
            $("#myDraftsTab").hide();
            $("#userInfoTable").hide()
            $("#changePasswordTab").hide();
    });

        $("#searchButton").click(function(){

            displaySearchResult($("#searchKeywords").val(), "", false);
        });

    });
}

var doLogin = function(){
    window.location = config.loginPage;
}

var doLogout = function(){
    $.get("/logout", function(data, status){
        displayHomePage();
    }).fail(function(error){
        displayHomePage();
    });
}

var displayCreateBlog = function(){
    $.get("/blog.html", function(display,status){
        $("#contentWrapper").empty();
        $("#contentWrapper").append(display);

        $("#updateButton").hide();

        $("#createButton").click(function(){
            var payload = {};
            payload.name = $("#name").val();
            payload.desc = $("#desc").val();
            payload.body = $("#blog").val();

            var reqData = JSON.stringify(payload);
            $.ajax({
                contentType: 'application/json',
                data: reqData,
                success: function (data) {
                    displayHomePage();
                },
                error: function (err) {
                    resp = JSON.parse(err.responseText);
                        alert('Some error has occoured please try again:' + resp.message);
                },
                type: 'POST',
                url: '/blog'
            });

        });

        $("#backButton").click(function(){
            displayHomePage();
        });


    });
}

var displayUpdateBlog = function(id, lastEntity, prev, from){

    $.get("/blog?id=" + id, function (data, result) {
        $.get("/blog.html", function (display, status) {
            $("#contentWrapper").empty();
            $("#contentWrapper").append(display);

            var blog = JSON.parse(data);
            $("#blogId").val(blog.id);
            $("#name").val(blog.name);
            $("#desc").val(blog.desc);
            $("#blog").val(blog.body);

            $("#createButton").hide();

            $("#updateButton").click(function () {
                var payload = {};
                payload.id = $("#blogId").val();
                payload.name = $("#name").val();
                payload.desc = $("#desc").val();
                payload.body = $("#blog").val();

                var reqData = JSON.stringify(payload);
                $.ajax({
                    contentType: 'application/json',
                    data: reqData,
                    success: function (data) {
                        if ('drafts' == from) {
                            displayMyDrafts(lastEntity, prev)
                        } else {
                            displayMyBlogs(lastEntity, prev);
                        }
                    },
                    error: function (err) {
                        resp = JSON.parse(err.responseText);
                        alert('Some error has occoured please try again:' + resp.message);
                    },
                    type: 'PUT',
                    url: '/blog'
                });

            });

            $("#backButton").click(function () {
                if ('drafts' == from) {
                    displayMyDrafts(lastEntity, prev);
                } else {
                    displayMyBlogs(lastEntity, prev);
                }
            });


        });
    }).fail(function(error){
        resp = JSON.parse(error.responseText);
        alert('Some error has occoured please try again:' + resp.message);

    });
}

var displaySearchResult = function (keyword, lastEntity, prev) {
    var append = false;
    var url = "/search";
    if(keyword){
        url += "?keyword=" + encodeURIComponent(keyword);
        append = true;
    }
    if(lastEntity){
        if(append==true)
            url+="&";
        else {
            url += "?";
            append = true;
        }
        url += "lastEntity=" + encodeURIComponent(lastEntity);
    }
    if(prev){
        if(append==true)
            url+="&";
        else {
            url += "?";
            append = true;
        }
        url += "prev=" + encodeURIComponent(prev);
    }

    $.get(url, function(data, status){
        var blogs = JSON.parse(data);
        if(blogs.length>0) {
            $.get("/search.html", function (display, result) {
                $("#contentWrapper").empty();
                $("#contentWrapper").append(display);

                var i;
                for (i = blogs.length-1; i >= 0; i--) {
                    var blog = blogs[i];
                    var row = "<TR id=\"" + blog.id + "\" class=\"even\">";
                    row += "<TD onclick=\"displayViewBlog('" + blog.id + "', '" + keyword + "' , '" + lastEntity + "' , '" + prev + "', 'search')\">" + blog.name + "</TD>";
                    row += "<TD onclick=\"displayViewBlog('" + blog.id + "', '" + keyword + "' , '" + lastEntity + "' , '" + prev + "', 'search')\">" + blog.desc + "</TD>";
                    row += "<TD>" + new Date(blog.creationDate).toString() + "</TD>";
                    row += "<TD>" + new Date(blog.lastUpdate).toString() + "</TD>";
                    row += "<TD>" + blog.revision + "</TD>";
                    row += "</TR>";
                    $("#blogs").after(row);
                    if (i == 0) {
                        $("#firstEntity").val(blog.id);
                    } else if (i == (blogs.length - 1)) {
                        $("#lastEntity").val(blog.id);
                    }
                }

                $("#prevButton").click(function(){
                    displaySearchResult(keyword, $("#firstEntity").val(), true);
                });

                $("#nextButton").click(function(){
                    displaySearchResult(keyword, $("#lastEntity").val(), false);
                });
                $("#backButton").click(function(){
                    displayHomePage();
                });
            });
        }

    }).fail(function(error){
        resp = JSON.parse(error.responseText);
        alert('Some error has occoured please try again:' + resp.message);
        displayHomePage();
    });
}

var displayMyBlogs = function(lastEntity, prev, from){

    var append = false;
    var url = "/blogs";
    if(lastEntity){
        if(append==true)
            url+="&";
        else {
            url += "?";
            append = true;
        }
        url += "lastEntity=" + encodeURIComponent(lastEntity);
    }
    if(prev){
        if(append==true)
            url+="&";
        else {
            url += "?";
            append = true;
        }
        url += "prev=" + encodeURIComponent(prev);
    }

    $.get(url, function(data, status){
        var blogs = JSON.parse(data);
        if(blogs.length==0 && 'self'==from) return;
        $.get("/myblogs.html", function (display, result) {
            $("#contentWrapper").empty();
            $("#contentWrapper").append(display);

            var i;
            for (i = blogs.length-1; i >= 0; i--) {
                var blog = blogs[i];
                var row = "<TR id=\"" + blog.id + "\" class=\"even\">";
                row+='<TD><input type="checkbox" id="blog' + i + '" value="' + blog.id + '"></TD>';
                row += "<TD onclick=\"displayViewBlog('" + blog.id + "', '' , '" + lastEntity + "' , '" + prev + "','myblogs')\">" + blog.name + "</TD>";
                row += "<TD onclick=\"displayViewBlog('" + blog.id + "', '' , '" + lastEntity + "' , '" + prev + "', 'myblogs')\">" + blog.desc + "</TD>";
                row += "<TD>" + new Date(blog.creationDate).toString() + "</TD>";
                row += "<TD>" + new Date(blog.lastUpdate).toString() + "</TD>";
                row += "<TD>" + blog.revision + "</TD>";
                row += "<TD><input type=\"button\" value=\"edit\" onclick=\"displayUpdateBlog('" + blog.id + "', '" + lastEntity + "' , '" + prev + "', 'myblogs')\"></TD>";
                row += "</TR>";

                $("#blogs").after(row);
                if (i == 0) {
                    $("#firstEntity").val(blog.id);
                } else if (i == (blogs.length - 1)) {
                    $("#lastEntity").val(blog.id);
                }
            }

            $("#prevButton").click(function(){
                displayMyBlogs($("#firstEntity").val(), true, 'self');
            });

            $("#nextButton").click(function(){
                displayMyBlogs($("#lastEntity").val(), false, 'self');
            });
            $("#backButton").click(function(){
                displayHomePage();
            });

            $("#delButton").click(function(){
                deleteBlog(blogs.length, lastEntity, prev, 'myblogs');
            });
        });

    }).fail(function(error){
        resp = JSON.parse(error.responseText);
        alert('Some error has occoured please try again:' + resp.message);
        displayHomePage();
    });

}

var displayMyDrafts = function(lastEntity, prev, from){

    var append = false;
    var url = "/drafts";
    if(lastEntity){
        if(append==true)
            url+="&";
        else {
            url += "?";
            append = true;
        }
        url += "lastEntity=" + encodeURIComponent(lastEntity);
    }
    if(prev){
        if(append==true)
            url+="&";
        else {
            url += "?";
            append = true;
        }
        url += "prev=" + encodeURIComponent(prev);
    }

    $.get(url, function(data, status){
        var blogs = JSON.parse(data);
        if(blogs.length==0 && 'self'==from) return;
        $.get("/drafts.html", function (display, result) {
            $("#contentWrapper").empty();
            $("#contentWrapper").append(display);

            var i;
            for (i = blogs.length-1; i >= 0; i--) {
                var blog = blogs[i];
                var row = "<TR id=\"" + blog.id + "\" class=\"even\">";
                row+='<TD><input type="checkbox" id="blog' + i + '" value="' + blog.id + '"></TD>';
                row += "<TD>" + blog.name + "</TD>";
                row += "<TD>" + blog.desc + "</TD>";
                row += "<TD>" + new Date(blog.creationDate).toString() + "</TD>";
                row += "<TD>" + new Date(blog.lastUpdate).toString() + "</TD>";
                row += "<TD>" + blog.revision + "</TD>";
                row += "<TD><input type=\"button\" value=\"edit\" onclick=\"displayUpdateBlog('" + blog.id + "', '" + lastEntity + "' , '" + prev + "', 'drafts')\"></TD>";
                row += "</TR>";

                $("#blogs").after(row);
                if (i == 0) {
                    $("#firstEntity").val(blog.id);
                } else if (i == (blogs.length - 1)) {
                    $("#lastEntity").val(blog.id);
                }
            }

            $("#prevButton").click(function(){
                displayMyDrafts($("#firstEntity").val(), true, 'self');
            });

            $("#nextButton").click(function(){
                displayMyDrafts($("#lastEntity").val(), false, 'self');
            });
            $("#backButton").click(function(){
                displayHomePage();
            });

            $("#delButton").click(function(){
                deleteBlog(blogs.length, lastEntity, prev, 'drafts');
            });

            $("#publishButton").click(function(){
                publishBlog(blogs.length, lastEntity, prev);
            });
        });

    }).fail(function(error){
        resp = JSON.parse(error.responseText);
        alert('Some error has occoured please try again:' + resp.message);
        displayHomePage();
    });

}

var displayViewBlog = function (id, keyword, lastEntity, prev, from){
    $.get("/blog?id=" + id, function(data, status) {
        var blog = JSON.parse(data);
        $.get("/comment?id=" + id, function(res, stat) {
            var comments = JSON.parse(res);
            $.get("/view.html", function (display, result) {
                $("#contentWrapper").empty();
                $("#contentWrapper").append(display);

                $("#nameCell").append(blog.name);
                $("#descCell").append(blog.desc);
                $("#blogCell").append(blog.body);

                $("#blogId").val(id);
                $("#keyword").val(keyword);
                $("#lastEntity").val(lastEntity);
                $("#prev").val(prev);

                var i;
                for(i=0; i<comments.length; i++){
                    var comment = comments[i];
                    var row = "<TR class=\"even\">";
                    row += "<TD width='50%'>" + comment.comment + "</TD>";
                    row += "<TD width='10%'>" + comment.user + "</TD>";
                    row += "<TD width='20%'>" + new Date(comment.time).toString() + "</TD>";
                    row += "</TR>";
                    $("#commentTable").append(row);
                }

                $("#commentButton").click(function () {
                    var payload = {};

                    payload["comment"] = $(document.getElementById("comment")).val();
                    payload["id"] = $(document.getElementById("blogId")).val();

                    var reqData = JSON.stringify(payload);

                    $.ajax({
                        contentType: 'application/json',
                        data: reqData,
                        success: function(data){
                            displayViewBlog(id, keyword, lastEntity, prev);
                        },
                        error: function(err){
                            resp = JSON.parse(err.responseText);
                            alert('Some error has occoured please try again: ' + resp.message);
                        },
                        type: 'POST',
                        url: '/comment'
                    });

                });

                $("#backButton").click(function () {
                    if('search'==from) {
                        displaySearchResult(keyword, lastEntity, prev);
                    }else{
                        displayMyBlogs(lastEntity, prev);
                    }
                });

            });
        });
    }).fail(function(error){
        resp = JSON.parse(error.responseText);
        alert('Some error has occoured please try again:' + resp.message);
    });

}

function displayChangePassword(){
    $.get("/change.html", function(display,status) {

        $("#contentWrapper").empty();
        $("#contentWrapper").append(display);

        $("#changeButton").click(function(){
            var payload = {};

            payload["old_password"] = $(document.getElementById("old_password")).val();
            payload["new_password"] = $(document.getElementById("new_password")).val();
            payload["confirm_password"] = $(document.getElementById("confirm_password")).val();

            var reqData = JSON.stringify(payload);

            $.ajax({
                contentType: 'application/json',
                data: reqData,
                success: function(data){
                    alert('Password change Successful');
                    displayHomePage();
                },
                error: function(err){
                    resp = JSON.parse(err.responseText);
                    alert('Some error has occoured please try again: ' + resp.message);
                },
                type: 'PUT',
                url: '/credentials'
            });

        });

        $("#backButton").click(function(){
            displayHomePage();
        });

    });
}

function deleteBlog(count, lastEntity, prev, from) {
    var ids = [];
    var i;

    for (i = 0; i < count; i++) {
        if (document.getElementById('blog' + i).checked) {
            ids.push(document.getElementById('blog' + i).value);
        }
    }
    if (ids.length == 0) {
        return;
    }

    var reqData = JSON.stringify(ids);
    $.ajax({
        contentType: 'application/json',
        data: reqData,
        success: function (data) {
            if ('drafts' == from) {
                displayMyDrafts(lastEntity, prev);
            } else {
                displayMyBlogs(lastEntity, prev);
            }
        },
        error: function (err) {
            resp = JSON.parse(err.responseText);
            alert('Some error has occoured please try again:' + resp.message);
        },
        type: 'DELETE',
        url: '/blog'
    });
}

 function publishBlog(count, lastEntity, prev){
        var ids = [];
        var i;

        for(i=0; i<count; i++){
            if(document.getElementById('blog' + i).checked){
                ids.push(document.getElementById('blog' + i).value);
            }
        }
        if(ids.length==0){
            return;
        }

        var reqData = JSON.stringify(ids);
        $.ajax({
            contentType: 'application/json',
            data: reqData,
            success: function(data){
                    displayMyDrafts(lastEntity, prev);
            },
            error: function(err){
                resp = JSON.parse(err.responseText);
                alert('Some error has occoured please try again:' + resp.message);
            },
            type: 'PUT',
            url: '/publish'
        });


    }

