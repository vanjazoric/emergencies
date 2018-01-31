/**
 * 
 */
package beans;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Vanja
 *
 */
public class Emergency implements Serializable {

	private static final long serialVersionUID = 1L;
	private String location;
	private String municipality;
	private String description;
	private Date date;
	private Territory territory;
	private Urgency urgency;
	private String picture;
	private State state;
	private Volunteer volunteer;
	
	public Emergency(String location, String municipality, String description,
			Date date, Territory territory, Urgency urgency, String picture,
			State state, Volunteer volunteer) {
		super();
		this.location = location;
		this.municipality = municipality;
		this.description = description;
		this.date = date;
		this.territory = territory;
		this.urgency = urgency;
		this.picture = picture;
		this.state = state;
		this.volunteer = volunteer;
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

	public Territory getTerriyory() {
		return territory;
	}

	public void setTerriyory(Territory terriyory) {
		this.territory = terriyory;
	}

	public Urgency getUrgency() {
		return urgency;
	}

	public void setUrgency(Urgency urgency) {
		this.urgency = urgency;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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

	@Override
	public String toString() {
		return "Emergency [location=" + location + ", municipality="
				+ municipality + ", description=" + description + ", date="
				+ date + ", terriyory=" + territory + ", urgency=" + urgency
				+ ", picture=" + picture + ", state=" + state + ", volunteer="
				+ volunteer + "]";
	}
}
