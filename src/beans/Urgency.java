/**
 * 
 */
package beans;

/**
 * @author Vanja
 *
 */
public enum Urgency {
	CRVENO,
	NARANDŽASTO,
	PLAVO;
	
	private String urgency;

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}
}
