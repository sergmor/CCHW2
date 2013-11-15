<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@page import="com.sun.org.apache.xpath.internal.FoundIndex"%>
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
%>

<!DOCTYPE html>
<html lang="en">
  
  <head>
      <!---Java script -->
      <script>
      function deletePeli()
        {

        }      
    </script> 
      
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>
    	Delete video
    </title>
    <link href="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"
    rel="stylesheet">
  </head>
  
  <body>
    padding-top: 50px;
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
              <a href="Delete.html">Delete</a>
            </li>
            <li>
              <a href="LiveStream.html">Live Stream</a>
            </li>
          </ul>
        </div>
        <!--/.nav-collapse -->
      </div>
    </div>      
    <div class="container">
      <div class="well" style="top: 10px">
        <h3>
          It's sad you will no longer share your moody peli!
        </h3>
        <p>
          Make sure you get another one soon!
        </p>
      </div>
      <form action="FileDeleteServlet" method="POST">
        <ul class="list-group">
        <%
        for (Video v : videos)
        {
        %>
			<li class="list-group-item">
				<span class="badge">14</span>
				<%=v.getVideoName()%>
				<label class="checkbox">
					<input type="checkbox" name="checkboxgroup" value="<%=v.getId()%>">&nbsp;Delete this peli!
				</label>
			</li>
        <%
        }
        %>
          
        </ul>
      </form>
      <button type="submit" class="btn btn-default" onclick = "deletePeli">
        Farewell
      </button>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"
    >
    </script>
    <script src="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"
    >
    </script>
  </body>

</html>