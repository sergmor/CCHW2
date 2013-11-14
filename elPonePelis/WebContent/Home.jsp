<%@page import="com.sun.org.apache.xpath.internal.FoundIndex"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.amazonaws.auth.*" %>

<%@ page import="edu.columbia.cc.elPonePeli.app.DatabaseHelper" %>
<%@ page import="edu.columbia.cc.elPonePeli.app.AwsCredentialConstants" %>
<%@ page import="edu.columbia.cc.elPonePelis.model.*" %>
<%@ page import="java.util.List" %>

<%
//System.out.println("Property secret = "+System.getProperty("AWS_SECRET_KEY"));
//System.out.println("Property access = "+System.getProperty("AWS_ACCESS_KEY_ID"));
DatabaseHelper helper = new DatabaseHelper().withCredentials(new BasicAWSCredentials(AwsCredentialConstants.ACCESS.getValue(), AwsCredentialConstants.SECRET.getValue()));
List<Video> videos = helper.getAllVideos();
String defaultVideoLink = "";
String defaultVideoId="";
if (videos.size() > 0)
{
	defaultVideoLink = videos.get(0).getVideoLink();
	defaultVideoId = videos.get(0).getId();
}
%>

<!DOCTYPE html>
<html lang="en">
  
  <head>
    <script>
      function updateRating()
        {

        }
      function playPeli()
        {
        alert("Hello! I am an alert box!!");
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
              <a href="Delete.html">Delete</a>
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
              <video width="400" height="300" controls="controls">
                  <source src="<%=defaultVideoLink%>" alt="No Link" type="video/mp4" />
              </video>
                
              <div class="well" style="height: 85px; width: 580px">
                <h4>
                  Video Title
                </h4>
                <p>
                  Uploader
                </p>
              </div>
              <form action="RatingUpdateServlet" method="POST">
              	<input type="hidden" name="currentVideoId" value="<%=defaultVideoId%>"/>
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
                
               <ul class="list-group">
                <li class="list-group-item" style= "width: 564px" >
                    <textarea name="comments" style="position: absolute; top: 2px;left: 0px;"cols="90" rows="2">                        
                        
                    </textarea>                    
                    Comment..!
                </li>
                <li class="list-group-item"  style= "width: 564px">
                    <textarea name="comments" style="position: absolute; top: 2px;left: 0px;"cols="90" rows="2"> 
                        
                    </textarea>
                  Comment..!!
                </li>
              </ul>
              <button type="submit" class="btn btn-default">
                Add Comment
              </button>                    
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
                                <div class="thumb" onclick="playPeli()"><img id="<%=v.getId()%>" src="<%=v.getThumbnailLink()%>" alt="Smiley face" height="85" width="85" /></div>
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