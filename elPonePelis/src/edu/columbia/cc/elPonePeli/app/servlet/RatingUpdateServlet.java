package edu.columbia.cc.elPonePeli.app.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.BasicAWSCredentials;

import edu.columbia.cc.elPonePeli.app.AwsCredentialConstants;
import edu.columbia.cc.elPonePeli.app.DatabaseHelper;
import edu.columbia.cc.elPonePelis.model.Video;

@WebServlet(name = "RatingUpdateServlet")
public class RatingUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RatingUpdateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		float rating = Float.parseFloat(request.getParameter("rating"));
		System.out.println("Rating is : " + rating);
		
		String currentVideoId = request.getParameter("currentVideoId");
		System.out.println("currentVideoId = " + currentVideoId);
		
		if (currentVideoId.equals(""))
		{
			System.out.println("Nothing to do with rating.");
		}
		else
		{
			System.out.println("Trying to update rating ...");
			DatabaseHelper helper = new DatabaseHelper().withCredentials(new BasicAWSCredentials(AwsCredentialConstants.ACCESS.getValue(), AwsCredentialConstants.SECRET.getValue()));
			Video currentVideo = helper.getVideoById(currentVideoId);
			currentVideo.updateRating(rating);
			helper.saveVideo(currentVideo);
			System.out.println("Update rating saved.");
		}
		
		response.sendRedirect("Home.jsp");
	}

}
