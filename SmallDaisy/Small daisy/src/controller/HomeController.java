package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.startup.HomesUserDatabase;

import model.Category;
import model.Goods;
import service.HomeService;

@WebServlet(urlPatterns ={"/index.action","/login.action","/logout.action","/reg.action"})
public class HomeController extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getServletPath();		
		if(url.equals("/index.action")){	
			List<Map<String,String>> list = HomeService.getTodayGoodsList();
			req.setAttribute("todaylist",list);
			List<Category> catelist = HomeService.getcateList();
			req.setAttribute("catelist",catelist);
			
			List<Goods> hotgoodslist = HomeService.getHotGoodsList();
			req.setAttribute("hotgoodslist",hotgoodslist);
			req.setAttribute("urlKey", "index");
		    req.getRequestDispatcher("/home.jsp").forward(req, resp);		    
		}
		else if(url.equals("/login.action")){
			resp.setContentType("text/json; charset=utf-8");
			PrintWriter out = resp.getWriter();
			String username=req.getParameter("userName");
			String password=req.getParameter("userPass");
//			int num=HomeService.getCheckUser(username,password);
			User loginuser=new User();
			loginuser=(User) HomeService.getCheckUser(username, password);
			
			if(loginuser!=null){
				req.getSession().setAttribute("_LOGIN_USER_",loginuser);
				out.print("{\"login\":true,\"username\":\""+username+"\"}");
			}else{
				out.print("{\"login\":false,\"msg\":\"用户名或密码错误\"}");
			}
			out.flush();
		}else if(url.equals("/logout.action")){
			req.getSession().removeAttribute("_LOGIN_USER_");
			
		}else if(url.equals("/reg.action")){
			resp.setContentType("text/json; charset=utf-8");
			PrintWriter out = resp.getWriter();
			String username=req.getParameter("userName");
			String password=req.getParameter("userPass");
		
			int i=HomeService.regUser(req.getParameterMap());

			if(i>=1){
			  User loginuser=new User();
			  loginuser.setUserName(username);
			  req.getSession().setAttribute("_LOGIN_USER_",loginuser);	
			  out.print("{\"reg\":true,\"username\":\""+username+"\"}");
		    }else{
			   out.print("{\"reg\":false}");
		    }
			
			out.flush();
		}
		else if (url.equals("/anreg.action")) {
			System.out.println("-------------------------------------------");
			resp.setContentType("text/json; charset=utf-8");
			PrintWriter out = resp.getWriter();
			String username = req.getParameter("userName");// http://localhost:8080/CreateShop/anreg.action?userName=lizhenyu&userPass=610544544
			String password = req.getParameter("userPass");
			System.out.println(username+password);
			User reguser = new User();
			reguser = HomeService.getCheckUser(username, password);
			System.out.println(reguser+"++++++++++");
             if(reguser==null) {
            	
            	 Map<String, String[]> regpam =new HashMap<>();
            	 String unres[]= {username};
            	 String upres[]= {password};
            	 String agres[]= {"123"};
            	 String sexres[]= {"0"};
            	 String emlres[]= {"456"};
            	 regpam.put("userName",unres );
            	 regpam.put("userPass",upres );
            	 regpam.put("userAge",agres);
            	 regpam.put("userSex",sexres );
            	 regpam.put("userEmail",emlres );
            	 System.out.println(regpam);
            	 int i = HomeService.regUser(regpam);
            	 System.out.println(i);
     			if (i >= 1) {
    				out.print("{\"reg\":true,\"username\":\"" + username + "\"}");
    			} else {
    				out.print("{\"reg\":false}");
    			}
             }
             else out.print("{\"reg\":false}");
             out.flush();
		}
	
		
		
		
		
	}
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}