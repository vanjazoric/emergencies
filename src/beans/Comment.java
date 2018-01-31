/**
 * 
 */
package beans;

import java.util.Date;

/**
 * @author Vanja
 *
 */
public class Comment {

	private String text;
	private Date date;
	private User user;
	
	public Comment(String text, Date date, User user) {
		super();
		this.text = text;
		this.date = date;
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Comment [text=" + text + ", date=" + date + ", user=" + user
				+ "]";
	}
	
}
