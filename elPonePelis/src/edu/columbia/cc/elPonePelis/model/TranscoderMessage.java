package edu.columbia.cc.elPonePelis.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TranscoderMessage {
	@JsonProperty("state")
	private String state = "";
	@JsonProperty("version")
	private String version = "";
	@JsonProperty("jobId")
	private String jobId = "";
	@JsonProperty("pipelineId")
	private String pipelineId = "";
	@JsonProperty("input")
	private TranscoderInput input;
	@JsonProperty("outputs")
	private TranscoderOutput[] outputs;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getPipelineId() {
		return pipelineId;
	}
	public void setPipelineId(String pipelineId) {
		this.pipelineId = pipelineId;
	}
	public TranscoderInput getInput() {
		return input;
	}
	public void setInput(TranscoderInput input) {
		this.input = input;
	}
	public TranscoderOutput[] getOutputs() {
		return outputs;
	}
	public void setOutputs(TranscoderOutput[] outputs) {
		this.outputs = outputs;
	}

	
}
