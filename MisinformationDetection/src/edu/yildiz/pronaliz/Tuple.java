/**
 *
 */
package edu.yildiz.pronaliz;



/**
 * @author jihad
 *
 */
public class Tuple {
	public String activityID ="";
	public String agentID ="";

	public Tuple() {
		super();
	}
	public Tuple(String activityID, String agentID) {
		super();
		this.activityID = activityID;
		this.agentID = agentID;
	}

	public String getActivityID() {
		return activityID;
	}
	public void setActivityID(String activityID) {
		this.activityID = activityID;
	}
	public String getAgentID() {
		return agentID;
	}
	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}
	@Override
	public String toString() {
		return "Tuple [activityID=" + activityID + ", agentID=" + agentID + "]";
	}


}
