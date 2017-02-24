var config;

function init(){
    $.get("/connection.json", function(data, status){
        config = data;
    });

    $('#loginButton').click(function(){
        if($("#j_username").val()==''){
            alert('Please Enter Login Id');
            return;
        }

        if($("#j_password").val()==''){
            alert('Please Enter Password');
            return;
        }

        doLogin();
    });

    $('#newButton').click(function(){
       showCreateForm();
    });

    $('#createButton').click(function(){
        createUser();
    })

    $('#cancelButton').click(function(){
        showLoginForm();
        clearCreateForm();
    });

    $('#homeButton').click(function(){
        window.location = "/index.html";
    });


    showLoginForm();

}

function doLogin(){
    $.post("/login",
        {
            j_username : $("#j_username").val(),
            j_password : $("#j_password").val()
        },
        function(data, status){
            if(status=="success"){
                submitResponseForm(data);
            }

        }).fail(function(error){
        var resp = JSON.parse(error.responseText)

        handleError("Authentication Failed:" + resp.message, 'login');
        });
}

function createUser(){
    $.post("/user",
        {
            f_name : $("#f_name").val(),
            l_name : $("#l_name").val(),
            email : $('#email').val(),
            login : $("#login").val(),
            password : $("#password").val(),
            confirm_password : $("#confirm_password").val()
        },
        function(data, status){
            if(status=="success"){
                alert('User Created Sucessfully');
                showLoginForm();
                clearCreateForm();
            }
        }).fail(function(error){
            var resp = JSON.parse(error.responseText)
            handleError("User Creation Failed:" + resp.message, 'create');
        });

}

function submitResponseForm(data, status){
    $("#token").val(data);
    $("#status").val('200');
    $("#responseForm").submit();

}

function handleError(reason, from){
    $("#errorDiv").empty();
    $("#errorDiv").append("<BR/>" + reason + "<BR/><BR/>");
    showErrorForm();

    if(from=='login'){
        $('#backButton').click(function(){
            showLoginForm();
        });

    }else{
        $('#backButton').click(function(){
            showCreateForm();
        });
    }
}

function showCreateForm(){
    $("#loginForm").hide();
    $("#createForm").show();
    $("#errorForm").hide();
}

function showLoginForm(){
    $("#loginForm").show();
    $("#createForm").hide();
    $("#errorForm").hide();
}

function showErrorForm(){
    $("#loginForm").hide();
    $("#createForm").hide();
    $("#errorForm").show();
}

function clearCreateForm(){
    $("#f_name").val('');
    $("#l_name").val('');
    $("#email").val('');
    $("#login").val('');
    $("#password").val('');
    $("#confirm_password").val('');

}
