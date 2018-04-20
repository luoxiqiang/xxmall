var TT = xxmall = {
	checkLogin : function(){
		var token = $.cookie("XX_TOKEN");
		if(!token){
			return ;
		}
		$.ajax({
			url : "http://sso.xxmall.com/rest/user/query/" + token,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var _data = data.data;
					var html =_data.username+"，欢迎来到淘淘！<a href=\"http://www.xxmall.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});