package edu.yildiz.pronaliz;

public class Agent {

	public String id="";
	public double credibility =0;
	public double availability =0;
	public double verifiability=0;


	public Agent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Agent [id=" + id + ", credibility=" + credibility + ", availability=" + availability
				+ ", verifiability=" + verifiability + "]";
	}
	public double getCredibility() {
		return credibility;
	}
	public void setCredibility(double credibility) {
		this.credibility = credibility;
	}
	public double getAvailability() {
		return availability;
	}
	public void setAvailability(double availability) {
		this.availability = availability;
	}
	public double getVerifiability() {
		return verifiability;
	}
	public void setVerifiability(double verifiability) {
		this.verifiability = verifiability;
	}



}
