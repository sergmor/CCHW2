package edu.columbia.cc.elPonePelis.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TranscoderOutput {
	
	@JsonProperty("id")
	private String id = "";
	@JsonProperty("presetId")
	private String presetId = "";
	@JsonProperty("key")
	private String key = "";
	@JsonProperty("thumbnailPattern")
	private String thumbnailPattern = "";
	@JsonProperty("status")
	private String status = "";
	@JsonProperty("duration")
	private String duration = "";
	@JsonProperty("width")
	private String width = "";
	@JsonProperty("height")
	private String height = "";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPresetId() {
		return presetId;
	}
	public void setPresetId(String presetId) {
		this.presetId = presetId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getThumbnailPattern() {
		return thumbnailPattern;
	}
	public void setThumbnailPattern(String thumbnailPattern) {
		this.thumbnailPattern = thumbnailPattern;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
}
