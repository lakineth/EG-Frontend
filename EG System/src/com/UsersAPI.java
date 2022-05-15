package com;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;



@WebServlet("/UsersAPI")
@MultipartConfig
public class UsersAPI extends HttpServlet  {
	private static final long serialVersionUID = 1L;
    
	User user = new User();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersAPI() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException {
		String username = request.getParameter("username");
		String address = request.getParameter("address");
		String phonenum = request.getParameter("phonenum");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		String output = user.insertUser(username, address, phonenum, email, password);
		response.getWriter().write(output);
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**Map paras = getParasMap(request);
		
		String output = user.updateUser(paras.get("hidUserIDSave").toString(),
	    paras.get("username").toString(),
		paras.get("address").toString(),
		paras.get("phonenum").toString(),
		paras.get("email").toString(),
	    paras.get("password").toString());
		 
		response.getWriter().write(output); */

		
		
		
		int id = Integer.parseInt(request.getParameter("hidUserIDSave"));
		String username = request.getParameter("username");
		String address = request.getParameter("address");
		String phonenum = request.getParameter("phonenum");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		String output = user.updateUser(id, username, address, phonenum, email, password);
		response.getWriter().write(output);
		
	}
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		String output = user.deleteUser(paras.get("userID").toString());
		response.getWriter().write(output);
	}
	
	// Convert request parameters to a Map
			private static Map getParasMap(HttpServletRequest request)
			{
				Map<String, String> map = new HashMap<String, String>();
				try
				{
					 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
					 String queryString = scanner.hasNext() ?
					 scanner.useDelimiter("\\A").next() : "";
					 scanner.close();
					 String[] params = queryString.split("&");
					 for (String param : params)
					 { 
						 String[] p = param.split("=");
						 map.put(p[0], p[1]);
					}
				}catch (Exception e){
					
				}
					return map;
			}

}
