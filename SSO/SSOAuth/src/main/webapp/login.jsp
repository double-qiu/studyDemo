<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String url = request.getParameter("goto");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>系统登录界面</title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link href="<%=path%>/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="<%=path%>/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
	<link href="<%=path%>/css/templatemo_style.css" rel="stylesheet" type="text/css">	
<script type="text/javascript" src="<%=path%>/js/jquery-1.10.2.min.js"></script>

<script type="text/javascript">

    $(function(){
         $("#btn_clear").bind("click",function(){
              btn_clear();
        });
         $("#btn_ok").bind("click",function(){
              btn_ok();
        });
    });

	function btn_ok(){
		var result =validateform();
		if(!result){
			   return ;
		}
	    $("#form").attr("action","jsp/login.page");
	    $("#form").submit();
	}

	function btn_clear() {
		$(":input").not("input[type=button]").each(function() {
			$(this).val("");
		});
	}

	function validateform() {
		var result = true;
		$(":input").not("input[type=button]").each(function() {
			if ($(this).val() == "") {
				result = false;
			}
		});
		return result;
	}
</script>
</head>
<body>

	
	<div class="container">
		<div class="col-md-12">
			<h1 class="margin-bottom-15">用户信息登录</h1>
			<form class="form-horizontal templatemo-container templatemo-login-form-1 margin-bottom-30"  id="form"action="login.jsp" method="post">				
		        <div class="form-group">
		          <div class="col-xs-12">		            
		            <div class="control-wrapper">
		            	<label for="username" class="control-label fa-label"><i class="fa fa-user fa-medium"></i></label>
		            	<input type="text" class="form-control"  name="username"  placeholder="Username">
		            </div>		            	            
		          </div>              
		        </div>
		        <div class="form-group">
		          <div class="col-md-12">
		          	<div class="control-wrapper">
		            	<label for="password" class="control-label fa-label"><i class="fa fa-lock fa-medium"></i></label>
		            	<input type="password" class="form-control"  name="userpassword"  placeholder="Password">
		            </div>
		          </div>
		        </div>
		        <div class="form-group">
		          <div class="col-md-12">
		          	<div class="control-wrapper">
		          		<input type="button" id="btn_ok" class="btn btn-info" value="登录" />
		          	</div>
		          </div>
		        </div>
		        <hr>
		        <input name="goto" type="hidden" value=<%=url%> />
		      </form>
		</div>
	</div>
</body>
</html>