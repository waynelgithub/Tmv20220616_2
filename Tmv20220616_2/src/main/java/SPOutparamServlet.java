

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.xdevapi.Type;

import java.sql.*;
/**
 * Servlet implementation class SPOutparamServlet
 */
@WebServlet("/SPOutparamServlet")
public class SPOutparamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SPOutparamServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String url="jdbc:mysql://localhost:3306/classicmodels?serverTimezone=Asia/Taipei&useUnicode=true&characterEncoding=utf8";
		String user="root";
		String password="1234";
		String city=request.getParameter("city");
		try {
			   Class.forName("com.mysql.cj.jdbc.Driver"); 
			   Connection   cn=DriverManager.getConnection(url, user, password);
			   String sql="call GetEmpCountInOffice(?, ?)";
			   CallableStatement st=cn.prepareCall(sql);
			   st.setString(1, city);
			   st.registerOutParameter(2, Types.INTEGER );
			   st.execute();			  
			   System.out.println("San Francisco Employee :"+ st.getInt(2));
			   response.setContentType("text/html;charset=utf-8");
			   response.getWriter().append("<h2>Employee number:").append( st.getInt(2)+"</h2>");
			   cn.close(); 
			   
		}catch(SQLException | ClassNotFoundException ex) {
			System.out.println("SPOutParam Error:"+ex.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
