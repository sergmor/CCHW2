package edu.columbia.cc.elPonePeli.app.resources;


import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import edu.columbia.cc.elPonePeli.app.PonePeli;
import edu.columbia.cc.elPonePelis.model.Video;

@Path("/videos/{video_id}")
public class VideoResource
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	public VideoResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getVideo() {
		String htmlPage = "";
		
		return htmlPage;
	}
	
	@PUT
	 public Response putTodo(JAXBElement<Video> video) {
	    Video newVideo = video.getValue();
	    PonePeli.instance.createVideoEntry(newVideo);
	    Response res;
		try {
			res = Response.created(new URI(uriInfo.getAbsolutePath()+"/"+ newVideo.getId().toString())).build();
		} catch (URISyntaxException e) {
			res = Response.created(uriInfo.getAbsolutePath()).build();
			e.printStackTrace();
		}
	    return res;
	  }
}
