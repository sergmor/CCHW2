package edu.columbia.cc.elPonePeli.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.BasicAWSCredentials;

import edu.columbia.cc.elPonePeli.app.AwsCredentialConstants;
import edu.columbia.cc.elPonePeli.app.DatabaseHelper;

/**
 * Servlet implementation class FileDeleteServlet
 */
public class FileDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String[] values = request.getParameterValues("checkboxgroup");
		DatabaseHelper db = new DatabaseHelper().withCredentials(new BasicAWSCredentials(AwsCredentialConstants.ACCESS.getValue(), AwsCredentialConstants.SECRET.getValue()));
		if (values != null && values.length > 0)
		{
			for (int i = 0; i < values.length; i++) {
				System.out.println("Attempting to delete video "+ values[i]);
				db.deleteVideoById(values[i]);
				
			}
			
		}
		System.out.println("Nothing came across");
		response.sendRedirect("Home.jsp");
	}

}
