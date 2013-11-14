

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.amazonaws.auth.PropertiesCredentials;

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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
//    protected void processRequest(HttpServletRequest request,
//            HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//
//        // Create path components to save the file
//        final String path = request.getParameter("destination");
//        final Part filePart = request.getPart("file");
//        final String fileName = getFileName(filePart);
//
//        OutputStream out = null;
//        InputStream filecontent = null;
//        final PrintWriter writer = response.getWriter();
//
//        try {
//            out = new FileOutputStream(new File(path + File.separator
//                    + fileName));
//            filecontent = filePart.getInputStream();
//
//            int read = 0;
//            final byte[] bytes = new byte[1024];
//
//            while ((read = filecontent.read(bytes)) != -1) {
//                out.write(bytes, 0, read);
//            }
//            writer.println("New file " + fileName + " created at " + path);
//        } catch (FileNotFoundException fne) {
//            writer.println("You either did not specify a file to upload or are "
//                    + "trying to upload a file to a protected or nonexistent "
//                    + "location.");
//            writer.println("<br/> ERROR: " + fne.getMessage());
//
//        } finally {
//            if (out != null) {
//                out.close();
//            }
//            if (filecontent != null) {
//                filecontent.close();
//            }
//            if (writer != null) {
//                writer.close();
//            }
//        }
//    }

//    private String getFileName(final Part part) {
//        final String partHeader = part.getHeader("content-disposition");
//        for (String content : part.getHeader("content-disposition").split(";")) {
//            if (content.trim().startsWith("filename")) {
//                return content.substring(
//                        content.indexOf('=') + 1).trim().replace("\"", "");
//            }
//        }
//        return null;
//    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String description = request.getParameter("destination"); // Retrieves <input type="text" name="description">
	    Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
	    String filename = getFilename(filePart);
	    InputStream filecontent = filePart.getInputStream();
	    
	    System.out.println("Calling video store method to perform upload to S3 ...");
	    VideoStore store = new VideoStore()
	    					.withBucketName("sample12345")
	    					.withCredentials(new PropertiesCredentials(this.getClass().getResourceAsStream("AwsCredentials.properties")));
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
		    							.withCredentials(new PropertiesCredentials(this.getClass().getResourceAsStream("AwsCredentials.properties")));
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
