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

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import services.DataHandler;
import beans.Emergency;
import beans.State;
import beans.Territory;
import beans.Urgency;

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

		System.out.println("DOSAO");

		String fileName = context.getRealPath("/") + "/files/territories.ser";
		File file = new File(fileName);
		Territory territory = TerritoryController.findTerritoryByName(
				territoryName, file);
		Emergency emergency = new Emergency(location, municipality,
				description, new Date(), territory, Urgency.valueOf(urgency),
				fileDetails.getFileName(), State.ACTIVE, null);

		System.out.println(emergency);
		if (!fileDetails.getFileName().equals("")) {
			String uploadedFileLocation = context.getRealPath("/") + "images/"
					+ fileDetails.getFileName();

			// save uploaded picture to file
			DataHandler.savePicture(picture, uploadedFileLocation);
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
		java.net.URI uri = new java.net.URI("../../Emergencies/index.html");
		return Response.temporaryRedirect(uri).build();
	}

}
