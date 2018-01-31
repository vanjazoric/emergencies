/**
 * 
 */
package beans;

import java.io.Serializable;

/**
 * @author Vanja
 *
 */
public abstract class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String picture;
	private Role role;
	
	public User(String username, String password, String firstName,
			String lastName, String phone, String email, String picture,
			Role role) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.picture = picture;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", phone=" + phone + ", email=" + email + ", picture="
				+ picture + ", role=" + role + "]";
	}
}
