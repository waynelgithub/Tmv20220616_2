

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import com.google.gson.*;
import model.*;
/**
 * Servlet implementation class CoffeeJson2Servlet
 */
@WebServlet("/CoffeeJson2Servlet")
public class CoffeeJson2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CoffeeJson2Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String js=request.getParameter("coffee");
		Gson g=new Gson();
		Coffee[]   cfs =g.fromJson(js, Coffee[].class);		
		//response.getWriter().append("Coffee []  length: "+cfs.length);
		boolean b=insertTable(cfs);
		if(b)
		   response.getWriter().append("Success");
		else
			response.getWriter().append("Failed");
	}
    boolean insertTable(Coffee[] cfs) {
    	if(cfs.length==0)
    		return false;
    	Connection con=null;
		PreparedStatement  insert= null;
		 String insertStatement =
		        "insert into classicmodels.COFFEES( COF_NAME , SUP_ID , PRICE , SALES ,TOTAL)" +
		        " values ( ? , ? ,? ,? ,?)";

		    try {
		         Class.forName("com.mysql.cj.jdbc.Driver");
		         con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Taipei","root","1234");
		      
		        con.setAutoCommit(false);
		        for(int i=0;i<cfs.length;i++) {
		        	insert = con.prepareStatement(insertStatement);
		            insert.setString(1, cfs[i].getCofName()); 
			        insert.setInt(2, cfs[i].getSupId());           
			        insert.setDouble(3, cfs[i].getPrice());
			        insert.setInt(4, cfs[i].getSales());
			        insert.setInt(5, cfs[i].getTotal());
			        int r=insert.executeUpdate();
			        if(r==0)
			        {
			        	con.rollback();
			        	return false;
			        }
		        }
		        con.commit();	            
		        
		    } catch (Exception e ) {
		    	System.out.println("Insert Coffee Error:"+e.getMessage());
		    	try {
					con.rollback();
				} catch (SQLException e1) {					
					e1.printStackTrace();
				}
		    	return false;
		    }finally {
    	          try {
    	        	con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
    	
    	return true;
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
