/**
 * 
 */
package controller;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import services.DataHandler;
import beans.Emergency;
import beans.Role;
import beans.User;
import beans.Volunteer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Vanja
 *
 */

@Path("volunteers")
public class VolunteerController {

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVolunteers(@PathParam("id") int id,
			@Context ServletContext context) throws JsonProcessingException {

		ArrayList<User> read = new ArrayList<User>();
		ArrayList<User> retList = new ArrayList<User>();
		String fileName = context.getRealPath("/") + "/files/users.ser";
		File file = new File(fileName);
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}
		String fileName2 = context.getRealPath("/") + "/files/emergencies.ser";
		File file2 = new File(fileName2);
		Emergency e = EmergencyController.findEmergencyById(id, file2);
		for (User user : read) {
			if (user.getRole() == Role.VOLUNTEER) {
				if (e.getTerritory()
						.getName()
						.equalsIgnoreCase(
								(((Volunteer) user).getTerritory().getName()))) {
					retList.add(user);
				}
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		// Object to JSON in String
		String jsonInString = mapper.writeValueAsString(retList);
		return jsonInString;
	}

	public static Volunteer findVolunteerByUsername(String username, File file) {
		ArrayList<User> read = new ArrayList<User>();
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}
		User retVal = null;
		for (User user : read) {
			if (user.getUsername().equalsIgnoreCase(username)) {
				retVal = user;
			}
		}
		return (Volunteer) retVal;
	}
}