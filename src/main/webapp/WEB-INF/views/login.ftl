<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>登录</title>

<link href="${ctx}/vendor/init.css" rel="stylesheet">
<link href="${ctx}/vendor/login.css" rel="stylesheet">
<link href="${ctx}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
  <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script type="text/javascript" src="${ctx}/vendor/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#userName").on({
	    	focus:function(){$("body").css("background-color","lightgray");},  
	    	blur:function(){$("body").css("background-color","lightblue");}, 
	  	});
	})
</script>
</head>
<body>
<form method="post" id="formlogin" action="${ctx}/login/formLogin">
<div class="txt_0 txt">
	<div class="container">
    	<div class="row row_0">
		  <div class="col-xs-6">
			<h4>
			   <img src="${ctx}/images/logo.png" class="img-responsive" alt="Responsive image">
			</h4>
		  </div>
		  <div class="col-xs-6">
		  	 <h4><a href="#" class="pull-right" style="margin-top:10px">返回首页&gt;</a></h4>
		  </div>
		</div>
    </div>
</div>
<div class="txt_1 txt">
	<div class="container">
    	<div class="row">
		  <div class="col-xs-6 row_l">
		  	<img src="${ctx}/images/login_bg.png" class="img-responsive" alt="Responsive image">
		  </div>
		  <div class="col-xs-6 row_r">
		  	<div class="login_box">
		  		<h6>欢迎登录</h6>
		  		<div class="login_name form-group">
		  			<label for="userName" class="label_user">用户名</label>
					<input type="text" name="username" maxlength="50" value="" id="username" class="user_input form-control" />
		  		</div>
		  		<div class="login_password form-group">
		  			<label for="password" class="label_password">密码</label>
					<input type="password" name="password" maxlength="50" value="" id="password" class="password_input form-control" />
		  		</div>
		  		<div class="login_securityCode row">
		  			<div class="col-md-5">
		  				<input type="text" class=" form-control">
		  			</div>
  					<div class="col-md-5 padding_cur0">
						<div class="pull-right" style="width:90%;height:40px;line-height: 40px;text-align: center;color:#fff;font-size:20px;background:#0175e2">5132</div>
  					</div>
  					<div class="col-md-2 padding_cur1">
  						<a href="#" class="pull-left" style="margin-top:10px">换一张</a>
  					</div>
		  		</div>
		  		<div class="login_auto">
		  			 <div class="checkbox">
					    <label>
					      <input type="checkbox">自动登录
					    </label>
					 </div>
		  		</div>
		  		<div class="login_success">		  			
		  				<button type="submit" class="btn btn-success btn-primary">登录</button>
		  		</div>
		  		<div>
		  			<a href="#" class="pull-left lightred">忘记密码</a>
		  			<a href="#" class="pull-right">免费注册</a>
		  		</div>
		  	</div>
		  </div>
		</div>
    </div>
</div>
</form>    
</body>
</html>