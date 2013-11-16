<%@page import="com.sun.org.apache.xpath.internal.FoundIndex"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.amazonaws.auth.*" %>

<%@ page import="edu.columbia.cc.elPonePeli.app.DatabaseHelper" %>
<%@ page import="edu.columbia.cc.elPonePeli.app.AwsCredentialConstants" %>
<%@ page import="edu.columbia.cc.elPonePelis.model.*" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="org.apache.commons.logging.Log" %>
<%@ page import="org.apache.commons.logging.LogFactory" %>
<%@ page import="java.util.List" %>
 

<%
//System.out.println("Property secret = "+System.getProperty("AWS_SECRET_KEY"));
//System.out.println("Property access = "+System.getProperty("AWS_ACCESS_KEY_ID"));
Log log = LogFactory.getLog(Object.class);
DatabaseHelper helper = new DatabaseHelper().withCredentials(new BasicAWSCredentials(AwsCredentialConstants.ACCESS.getValue(), AwsCredentialConstants.SECRET.getValue()));
List<Video> videos = helper.getAllVideos();
String defaultVideoLink = "";
String defaultVideoId="";
String defaultVideoName="No videos";
String defaultVideoRating="";
boolean mobile=false;
String userAgent = request.getHeader("user-agent");
//Checks if it is a mobile browser 
Pattern pattern = Pattern.compile("up.browser|up.link|windows ce|iphone|iemobile|mini|mmp|symbian|midp|wap|phone|pocket|mobile|pda|psp", Pattern.CASE_INSENSITIVE);   
Matcher matcher = pattern.matcher(userAgent);   
if (matcher.find()){ mobile = true; } 
log.error("mobile is " + mobile);

if (videos.size() > 0)
{
	defaultVideoLink = mobile ? videos.get(0).getMobileLink() : videos.get(0).getWebLink();
	defaultVideoId = videos.get(0).getId();
	defaultVideoName = videos.get(0).getVideoName();
	defaultVideoRating = ""+videos.get(0).getAvgRating();
	
	for (Video v : videos)
	{
		System.out.println(v.toString());
	}
	log.error("defaultVideoLink is " + defaultVideoLink);
	
}
%>

<!DOCTYPE html>
<html lang="en">
  
  <head>
    <script>
      function updateRating()
        {

        }
      function playPeli(videoLink, videoId, videoRating)
      {
    	  var videoPlayer = document.getElementById('videoPlayerId');
          var videoSrc = document.getElementById('videoSrcId');
          
          videoPlayer.pause();
          
          videoSrc.setAttribute("src", videoLink);

          videoPlayer.load();
          //videoPlayer.play();
          //alert("videoLink="+videoLink+", videoId="+videoId);
          
          document.getElementById('currentVideoId').value = videoId;
          document.getElementById('currentVideoRating').value = videoRating;
      }  
      function getBrowser()
      {            
          if(window.innerWidth <= 800 && window.innerHeight <= 600)
          {
              return "http://54.204.185.214:1935/live/myStream/playlist.m3u8";
          } 
          else 
          {
              return "http://www.wowza.com/resources/3.6.0/examples/LiveVideoStreaming/FlashHTTPPlayer/player.html"
          }
      } 
    </script>
      
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.png">
    <title>
      El Pone Peli Home
    </title>
    <link href="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/screen-aad0703d82.css" rel="stylesheet">
</head>
  
  <body>
    padding-top: 70px;
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">el Pone Pelis</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active">
              <a href="Home.jsp">Home</a>
            </li>
            <li>
              <a href="Upload.html">Upload</a>
            </li>
            <li>
              <a href="Delete.jsp">Delete</a>
            </li>
            <li>
				<a href="javascript:document.location.href=getBrowser();" >LiveStream</a>	
            </li>
          </ul>
        </div>
        <!--/.nav-collapse -->
      </div>
    </div>
    <div class="container">
      <div class="starter-template">
        <h1>

        </h1>
        <p class="lead">
         <h1>
          <br>
          Welcome to el Pone Pelis..!!!
        </h1>
        </p>
      </div>
    </div>
    <!-- /.container -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
    </script>
    <script src="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js">
    </script>
    <div class="container">
      <table class="table">
        <tbody>
          <tr>
            <td align = "left" >
              <video width="400" height="300" controls="controls" name="video" id="videoPlayerId">
                  <source id="videoSrcId" src="<%=defaultVideoLink%>" alt="No Link" type="video/mp4" />
              </video>
                
              <div class="well" style="height: 85px; width: 580px">
                <h4>
                  <%=defaultVideoName%> - <%=defaultVideoRating%>
                </h4>
                
              </div>
              <form action="RatingUpdateServlet" method="POST">
              	<input type="hidden" name="currentVideoId" value="<%=defaultVideoId%>" id="currentVideoId"/>
              	<input type="hidden" name="currentVideoRating" value="<%=defaultVideoRating%>" id="currentVideoRating"/>
	              <select name="rating">
	                    <option value="1">1</option>
	                    <option value="2">2</option>
	                    <option value="3">3</option>
	                    <option value="4">4</option>
	                    <option value="5">5</option>
	               </select>
	               <button type="submit" class="btn btn-default">
	                Rate
	               </button>
               </form>
             
            <td>
            <table class="table">
                <tbody>
                    <tr>
                        <!--thumbnails-->
                       <div id="thumbs">
                       <%
                       System.out.println("Before the for loop.");
                       for (Video v : videos)
                       {
                    	%>
                    	  <tr>
                            <td align = "left">
                            	<%String linkToShow;
                            	if (mobile)
                           {linkToShow = v.getMobileLink();}
                           else
                           {linkToShow = v.getWebLink();}%>
                                <div class="thumb" onclick="playPeli(&quot;<%=linkToShow%>&quot;,&quot;<%=v.getId()%>&quot;,&quot;<%=v.getAvgRating()%>&quot;)"><img id="<%=v.getId()%>" src="<%=v.getThumbnailLink()%>" alt="Smiley face" height="85" width="85" /></div>
                            </td>
                        </tr>
                    	<% 
                       }
                       %>
                      </div>                                                 
                    </tbody>
                </table>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </body>

</html>