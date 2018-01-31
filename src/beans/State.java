/**
 * 
 */
package beans;

/**
 * @author Vanja
 *
 */
public enum State {
	ACTIVE,
	ARCHIVED;

	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
