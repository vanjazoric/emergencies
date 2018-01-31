/**
 * 
 */
package beans;

/**
 * @author Vanja
 *
 */
public class Admin extends User {

	public Admin(String username, String password, String firstName,
			String lastName, String phone, String email, String picture,
			Role role) {
		super(username, password, firstName, lastName, phone, email, picture,
				role);
	}
}