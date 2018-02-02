/**
 * 
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Vanja
 *
 */
public class Emergency implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String location;
	private String municipality;
	private String description;
	private Date date;
	private Territory territory;
	private Urgency urgency;
	private String photo;
	private State state;
	private Volunteer volunteer;
	private ArrayList<Comment> comments;
	
	public Emergency(int id, String location, String municipality, String description,
			Date date, Territory territory, Urgency urgency, String photo,
			State state, Volunteer volunteer, ArrayList<Comment> comments) {
		super();
		this.id = id;
		this.location = location;
		this.municipality = municipality;
		this.description = description;
		this.date = date;
		this.territory = territory;
		this.urgency = urgency;
		this.photo = photo;
		this.state = state;
		this.volunteer = volunteer;
		this.comments = comments;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Territory getTerritory() {
		return territory;
	}

	public void setTerritory(Territory territory) {
		this.territory = territory;
	}

	public Urgency getUrgency() {
		return urgency;
	}

	public void setUrgency(Urgency urgency) {
		this.urgency = urgency;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Volunteer getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Emergency [id=" + id + ", location=" + location
				+ ", municipality=" + municipality + ", description="
				+ description + ", date=" + date + ", territory=" + territory
				+ ", urgency=" + urgency + ", photo=" + photo + ", state="
				+ state + ", volunteer=" + volunteer + ", comments=" + comments
				+ "]";
	}
}
