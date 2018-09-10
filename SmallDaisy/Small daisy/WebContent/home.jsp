<%@include file="/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

			<div class="carousel slide" id="carousel-210314" data-ride="carousel" data-interval="2000">
				<ol class="carousel-indicators">
					<li class="active" data-slide-to="0" data-target="#carousel-210314">
					</li>
					<li data-slide-to="1" data-target="#carousel-210314">
					</li>
					</li>
					<li data-slide-to="3" data-target="#carousel-210314">
					</li>
					</li>
					<li data-slide-to="4" data-target="#carousel-210314">
					</li>
				</ol>
				<div class="carousel-inner">
					<div class="item active">
						<img alt="" src="${pageContext.request.contextPath}/img/adv/ad1.jpg" />

					</div>
					<div class="item">
						<img alt="" src="${pageContext.request.contextPath}/img/adv/ad3.jpg" />
					</div>
					<div class="item">
						<img alt="" src="${pageContext.request.contextPath}/img/adv/ad4.jpg" />
					</div>
					<div class="item">
						<img alt="" src="${pageContext.request.contextPath}/img/adv/ad5.jpg" />
					</div>
				</div> <a class="left carousel-control" href="#carousel-210314" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a> <a class="right carousel-control" href="#carousel-210314" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
			</div>
			<!-- 今日推荐 -->
			<div class="row clearfix">
	
					<div class="col-md-12 column ">
					    <div class="thumbnail today-style">
					      <div class="caption">
					        <h3 align="center">CELEBRATE WITH SCENT</h3>		
					        <p class="text-center" style="color:white;">
					        和煦春日的微风花香，捕捉描绘惹人着迷的花开印象。
					        亭亭而立的小苍兰，清新纯洁率真洒脱；层叠华贵的牡丹，盛放着张扬的生命力；绿叶掩映的橙花，星星点点特立独行。
					        </p>		
					</div>	
			 
				<c:forEach items="${todaylist }" var="c" varStatus="vs">

					<div class="col-md-2 column ">	
					    <a href="${pageContext.request.contextPath}/goods/goodsDetail.action?goods_id=${c.goods_id}">
					    <div class="thumbnail todaygoods" id="">
					      <img src="${pageContext.request.contextPath}${c.goods_pic }"/>
					     <div class="caption text-center">
					        <h4 class="font-red">${c.goods_price }</h4>
					        <p>${c.goods_name}</p>
					      </div>
					    </div>
					    </a>				
					</div>

                </c:forEach>
				
			</div>
			
	
			
			
<%@include  file="footer.jsp"%>