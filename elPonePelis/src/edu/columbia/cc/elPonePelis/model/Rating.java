package edu.columbia.cc.elPonePelis.model;

public class Rating implements IRating
{
	private float avgRating;
	private int numRatings;
	
	public Rating()
	{
		this.numRatings = 0;
		this.avgRating = 0.0f;
	}
	
	public float getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}
	public int getNumRatings() {
		return numRatings;
	}
	public void setNumRatings(int numRatings) {
		this.numRatings = numRatings;
	}
	
	public float updateRating(float newRating)
	{
		float currentTotalRating = this.avgRating * this.numRatings;
		float newAvgRating = (currentTotalRating + newRating) / (this.numRatings + 1);
		
		this.avgRating = newAvgRating;
		this.numRatings++;
				
		return this.avgRating;
	}
}
