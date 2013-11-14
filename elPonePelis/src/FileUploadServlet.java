

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;

import edu.columbia.cc.elPonePeli.app.AwsCredentialConstants;
import edu.columbia.cc.elPonePeli.app.DatabaseHelper;
import edu.columbia.cc.elPonePeli.app.VideoStore;
import edu.columbia.cc.elPonePelis.model.Video;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet(name = "FileUploadServlet")
@MultipartConfig
public class FileUploadServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public FileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Part filePart = request.getPart("file");
	    String filename = getFilename(filePart);
	    InputStream filecontent = filePart.getInputStream();
	    
	    System.out.println("Calling video store method to perform upload to S3 ...");
	    VideoStore store = new VideoStore()
	    					.withCredentials(new BasicAWSCredentials(AwsCredentialConstants.ACCESS.getValue(), AwsCredentialConstants.SECRET.getValue()));
	    Video video = store.storeIntoBucket(filename, filecontent);
	    System.out.println("Call finished.");
	    
	    if (video == null)
	    {
	    	System.out.println("Some error occurred. No details to persist in the database.");
	    }
	    else
	    {
	    	System.out.println("Trying to persist details in database ...");
		    DatabaseHelper helper = new DatabaseHelper()
		    							.withCredentials(new BasicAWSCredentials(AwsCredentialConstants.ACCESS.getValue(), AwsCredentialConstants.SECRET.getValue()));
		    helper.saveVideo(video);
		    System.out.println("Done.");
	    }
	    
	    response.sendRedirect("Upload.html");
	}

	private static String getFilename(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}
}
