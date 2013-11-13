package edu.columbia.cc.elPonePeli.app;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;

import edu.columbia.cc.elPonePelis.model.Video;



public enum PonePeli{
	instance;
	
	AWSCredentials awsCredentials = new ClasspathPropertiesFileCredentialsProvider().getCredentials();
	DatabaseHelper dbh = new DatabaseHelper()
						.withCredentials(awsCredentials);
	
	public void createVideoEntry(Video video){
		dbh.saveVideo(video);
	}
	
}
