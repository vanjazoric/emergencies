/**
 * 
 */
package beans;

/**
 * @author Vanja
 *
 */
public enum Role {
	ADMIN, 
	VOLUNTEER;

	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
