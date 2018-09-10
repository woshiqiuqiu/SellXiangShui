<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="modal fade" id="regFormModal" role="dialog" aria-hidden="true" aria-labelledby="myModalLabel">
	<form role="form" action="${pageContext.request.contextPath}/reg.action" method="post" id="regFormBody">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" aria-hidden="true" type="button"
					data-dismiss="modal">×</button>
				<h4 class="modal-title" id="myModalLabel">用户注册</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					 <label for="exampleInputEmail1">用户名</label><input type="text" class="form-control" name="userName" />
				</div>
				<div class="form-group">
					 <label for="exampleInputPassword1">密码</label><input type="password" class="form-control" name="userPass" />
				</div>
				<div class="form-group">
					 <label for="exampleInputPassword1">年龄</label><input type="password" class="form-control" name="userAge" />
				</div>
			<div class="form-group">
					 <label for="exampleInputEmail2">男</label><input type="radio" class="form-control" name="userSex" value="1"checked/>
					<label for="exampleInputEmail2">女</label><input type="radio" class="form-control" name="userSex" value="2"/>
				</div>
				<div class="form-group">
					 <label for="exampleInputPassword2">邮箱</label><input type="text" class="form-control" name="userEmail" />
				</div>

			</div>
			<div class="modal-footer">
			    
				<button class="btn btn-primary" type="button" onclick="reg('${redirUrl}','${pageContext.request.contextPath}')">注册</button>
				<button class="btn btn-default" type="button"
					data-dismiss="modal" 
					<c:if test="${!empty backUrl}">onclick=closeLogForm('${backUrl}')</c:if>   >关闭窗口</button>
				
			</div>
		</form>	
		</div>

	</div>

</div>

<script type="text/javascript">
function reg(redirUrl,baseurl){
	console.log($('#regFormBody').serialize());

	$.ajax({
			method: "POST",
			url:baseurl+"/reg.action",
			dataType: "json",
			data:$('#regFormBody').serialize(),
			success: function(result){
		
				if(result.reg==true){
		   
					$('#regFormModal').modal('hide');
					
					$("#info").html("<li id='li1'><span>"+result.username+" 您好，欢迎来到随意购商城！</span>"+	
							"<li><a href='#' onclick='showCart()'>购物车 <span class='badge' id='cartBadge'></span></a></li>"+
			 				"<li><a href='${pageContext.request.contextPath}/order/getMyOrders'>我的订单</a></li>"+
			 				"<li><a href='${pageContext.request.contextPath}/usercenter/index'>个人中心</a></li>"+
			 				"<li><a href='#' onclick='logout()'>退出登录</a></li>");
								
				}
				else{
					alert("false");
				}
			}
	});
}
</script>