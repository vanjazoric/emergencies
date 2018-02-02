/**
 * 
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import services.DataHandler;
import beans.Emergency;
import beans.State;
import beans.Territory;
import beans.Urgency;
import beans.Volunteer;

/**
 * @author Vanja
 *
 */
@Path("/emergencies")
public class EmergencyController {

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response register(@FormDataParam("location") String location,
			@FormDataParam("municipality") String municipality,
			@FormDataParam("description") String description,
			@FormDataParam("territory") String territoryName,
			@FormDataParam("urgency") String urgency,
			@FormDataParam("picture") InputStream picture,
			@FormDataParam("picture") FormDataContentDisposition fileDetails,
			@Context ServletContext context) throws URISyntaxException,
			IOException {

		System.out.println(picture+"   "+fileDetails);

		String fileName = context.getRealPath("/") + "/files/territories.ser";
		File file = new File(fileName);
		Territory territory = TerritoryController.findTerritoryByName(
				territoryName, file);
		Random rand = new Random();
		int id = rand.nextInt(1000000000);
		Emergency emergency = new Emergency(id, location, municipality,
				description, new Date(), territory, Urgency.valueOf(urgency),
				fileDetails.getFileName(), State.ACTIVE, null, null);

		System.out.println(emergency);
		if (!fileDetails.getFileName().equals("")) {
			String uploadedFileLocation = context.getRealPath("/") + "images/"
					+ fileDetails.getFileName();

			// save uploaded picture to file
			DataHandler.savePicture(picture, uploadedFileLocation);
		}
		else{
			emergency.setPhoto("logo.png");
		}

		ArrayList<Emergency> read = new ArrayList<Emergency>();

		String fileName2 = context.getRealPath("/") + "/files/emergencies.ser";
		File file2 = new File(fileName2);
		if (file2.exists()) {
			read = DataHandler.deserialize(file2);
		}
		read.add(emergency);
		DataHandler.serialize(read, file2);
		System.out.println(read.size());
		java.net.URI uri = new java.net.URI("../../Emergencies/success.html");
		return Response.temporaryRedirect(uri).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getEmergencies(@Context ServletContext context)
			throws URISyntaxException, IOException {

		ArrayList<Emergency> read = new ArrayList<Emergency>();
		String fileName = context.getRealPath("/") + "/files/emergencies.ser";
		File file = new File(fileName);
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}
		ObjectMapper mapper = new ObjectMapper();
		// Object to JSON in String
		String jsonInString = mapper.writeValueAsString(read);
		System.out.println("emergencies:" + jsonInString);
		return jsonInString;
	}

	@POST
	@Path("/update")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateEmergency(@FormParam("emergencyId") int id,
			@FormParam("username") String username,
			@Context ServletContext context) throws URISyntaxException,
			IOException {
		ArrayList<Emergency> read = new ArrayList<Emergency>();
		String fileName = context.getRealPath("/") + "/files/emergencies.ser";
		File file = new File(fileName);
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}

		String fileName2 = context.getRealPath("/") + "/files/users.ser";
		File file2 = new File(fileName2);
		Volunteer volunteer = VolunteerController.findVolunteerByUsername(
				username, file2);
		for (Emergency e : read) {
			if (e.getId() == id) {
				e.setVolunteer(volunteer);
			}
		}
		DataHandler.serialize(read, file);
		java.net.URI uri = new java.net.URI("../../Emergencies/index.html");
		return Response.temporaryRedirect(uri).build();
	}

	public static Emergency findEmergencyById(int id, File file) {
		ArrayList<Emergency> read = new ArrayList<Emergency>();
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}
		Emergency retVal = null;
		for (Emergency e : read) {
			if (e.getId() == id) {
				retVal = e;
			}
		}
		return retVal;
	}
}
