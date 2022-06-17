

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Coffee;

/**
 * Servlet implementation class UpdateJSCoffee2Servlet
 */
@WebServlet("/UpdateJSCoffee2Servlet")
public class UpdateJSCoffee2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateJSCoffee2Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String js=request.getParameter("jsCoffee");
		Gson g=new Gson();
		Coffee[]   cfs =g.fromJson(js, Coffee[].class);		
		boolean b=updateCoffee(cfs);
		if(b)
			   response.getWriter().append("update Success");
			else
				response.getWriter().append("update Failed");
	}
	boolean updateCoffee(Coffee[]  cfs) {
		if(cfs.length==0)
    		return false;
    	Connection con=null;
		PreparedStatement  update= null;

    	String updateString = "update  classicmodels.COFFEES  set SALES=? , Total=?  where COF_NAME = ?";


		    try {
		         Class.forName("com.mysql.cj.jdbc.Driver");
		         con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Taipei","root","1234");
		      
		        con.setAutoCommit(false);
		        for(int i=0;i<cfs.length;i++) {
		        	update = con.prepareStatement(updateString);
		        	update.setInt(1, cfs[i].getSales());
		        	update.setInt(2, cfs[i].getTotal());
		        	update.setString(3, cfs[i].getCofName()); 
			        int r=update.executeUpdate();
			        if(r==0)
			        {
			        	con.rollback();
			        	return false;
			        }
		        }
		        con.commit();	            
		        
		    } catch (Exception e ) {
		    	System.out.println("update Coffee Error:"+e.getMessage());
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
