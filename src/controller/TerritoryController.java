/**
 * 
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import services.DataHandler;
import beans.Territory;

/**
 * @author Vanja
 *
 */
@Path("/territories")
public class TerritoryController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getTerritories(@Context ServletContext context)
			throws URISyntaxException, IOException {
		Territory t1 = new Territory("Novi Sad", 702.2, 286546);
		Territory t2 = new Territory("Subotica", 1008, 99471);
		Territory t3 = new Territory("Beograd", 360, 1369000);

		ArrayList<Territory> read = new ArrayList<Territory>();
		String fileName = context.getRealPath("/") + "/files/territories.ser";
		File file = new File(fileName);
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		} else if (read.isEmpty()) {
			read.add(t1);
			read.add(t2);
			read.add(t3);
			System.out.println(read.size());
			DataHandler.serialize(read, file);
		}
		ObjectMapper mapper = new ObjectMapper();
		// Object to JSON in String
		String jsonInString = mapper.writeValueAsString(read);
		System.out.println("teritorije" + jsonInString);
		return jsonInString;
	}

	public static Territory findTerritoryByName(String name, File file) {
		ArrayList<Territory> read = new ArrayList<Territory>();
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}
		Territory retVal = null;
		for (Territory territory : read) {
			System.out.println(territory);
			if (territory.getName().equalsIgnoreCase(name)) {
				retVal = territory;
			}
		}
		return retVal;
	}
}
