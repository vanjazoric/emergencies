/**
 * 
 */
package beans;

/**
 * @author Vanja
 *
 */
public class Volunteer extends User {

	private static final long serialVersionUID = 1L;
	private Territory territory;
	private Boolean isBlocked;
	
	public Volunteer(String username, String password, String firstName,
			String lastName, String phone, String email, String picture,
			Role role, Territory territory, Boolean isBlocked) {
		super(username, password, firstName, lastName, phone, email, picture,
				role);
		this.territory = territory;
		this.isBlocked = isBlocked;
	}

	public Territory getTerritory() {
		return territory;
	}

	public void setTerritory(Territory territory) {
		this.territory = territory;
	}

	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	@Override
	public String toString() {
		return "Volunteer [territory=" + territory + ", isBlocked=" + isBlocked
				+ "]";
	}
}