<%@include file="../header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

			<div class="row">
            <c:if test="${!empty newGoods }">
			<c:forEach items="${newGoods}" var="g">
				<div class="col-md-3">
					<div class="thumbnail">
						<a href="${pageContext.request.contextPath}/goods/goodDetail.action?goods_id=${g.goodsId}">
				        <img width="208px" height="172px" alt="${g.goodsName }" 
				        src="${pageContext.request.contextPath}${g.goodsPic}"
				        /></a>
				        
						<div class="caption text-center">
							<h4>
								${g.goodsName}
							</h4>
							<p>
								原价<span class="glyphicon glyphicon-yen" aria-hidden="true"></span>${g.goodsPrice}
								<span class="glyphicon glyphicon-hand-right" aria-hidden="true"></span>
							</p>
							<p>
								现售<span class="label label-pill label-info"><span class="glyphicon glyphicon-yen" aria-hidden="true"></span>200</span>
							</p>
							<p>
								共售出${g.goodsSales}件
							</p>
						</div>
					</div>
				</div>
			</c:forEach>
	        </c:if>
			</div>
			<c:if test="${empty newGoods}">
				<div class="alert alert-danger col-md-2" role="alert">对不起，暂无此类商品</div>
			</c:if>    
    
<%@include  file="../footer.jsp"%>		   