package edu.columbia.cc.elPonePelis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TranscoderInput {
	
	@JsonProperty("key")
	private String key = "";
	@JsonProperty("container")
	private String container = "";
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getContainer() {
		return container;
	}
	public void setContainer(String container) {
		this.container = container;
	}
	
	

}
