<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
function showCart(baseurl,goodslist){
	
	alert(" showCart( )   " +baseurl);
	$("#cartModal").modal('show');
	$("#cartTitle").html("我的购物车");
	if(goodslist==null){
		$("#cart_msg").hide();
		$("#cartBody").html("<h3>目前购物车为空，快去购物吧</h3>");
	}else{
		alert(" #cart_tab " );		
		$("#cart_tab").show();
		
	}	
}
</script>

<div class="modal fade" id="cartModal" role="dialog" aria-hidden="true" aria-labelledby="myModalLabel" >
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">					 
				<button class="close" aria-hidden="true" type="button" data-dismiss="modal">
					×
				</button>
				<h4 class="modal-title" id="cartTitle">
					我的购物车
				</h4>
			</div>
			<form class="form-inline" role="form" action=" " method="post">
			<div class="modal-body" id="cartBody">
			<div id="cart_msg" style="display:none"></div>
			<c:if test="${sessionScope.goodslist!=null}">
			<table class="table table-striped" id="cart_tab">
				<thead>
					<tr>
						<th>#</th>
						<th>商品图片</th>
						<th>商品名称</th>
						<th>商品单价</th>
						<th>数量</th>
						<th>小计</th>	
						<th>操作</th>						
					</tr>
				</thead>						
				<tbody id="cart_tbody">
				    <c:forEach items="${sessionScope.goodslist}" var="s" varStatus="vs">
					<tr>
						<td>${vs.index+1}</td>
						<td>${s.goodsPic}</td>
						<td>${s.goodsName}</td>
						<td>${s.goodsPrice}</td>
						<td><input type="text" name="goodsSales" value="${s.goodsSales }" size="5"/></td>
						<td></td>	
						<td><a href="#">删除</a></td>						
					</tr>
					</c:forEach>
				</tbody>
			</table>				
			</c:if>					 
		
			</div>		
			<div class="modal-footer">
				<button class="btn btn-primary" type="submit" id="ok">去结算</button>		 
				<button class="btn btn-default" type="button" data-dismiss="modal" onclick="closeMsg()">关闭窗口</button>	
			</div>
			</form>	
		</div>
		
	</div>
</div>

<!-- 登陆情况信息提示模态框 -->
<div class="modal fade" id="msgModal" role="dialog" aria-hidden="true" aria-labelledby="myModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">					 
				<button class="close" aria-hidden="true" type="button" data-dismiss="modal">
					×
				</button>
				<h4 class="modal-title" id="msgTitle">
					
				</h4>
			</div>
			<div class="modal-body" id="msgBody">
				
			</div>
			<div class="modal-footer">	 
				<button class="btn btn-default" type="button" data-dismiss="modal">关闭窗口</button>	
			</div>
		</div>
		
	</div>
	
</div>
</body>
</html>