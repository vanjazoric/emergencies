/**
 * 
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
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
		Territory t1 = new Territory(1, "Novi Sad", 702.2, 286546);
		Territory t2 = new Territory(2, "Subotica", 1008, 99471);
		Territory t3 = new Territory(3, "Beograd", 360, 1369000);

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

	@POST
	@Path("/changeName")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Boolean changeName(@FormParam("id") int id,
			@FormParam("name") String name, @Context ServletContext context)
			throws URISyntaxException, IOException {
		ArrayList<Territory> read = new ArrayList<Territory>();
		String fileName = context.getRealPath("/") + "/files/territories.ser";
		File file = new File(fileName);
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}
		for (Territory t : read) {
			if (t.getId() == id) {
				t.setName(name);
			}
		}
		DataHandler.serialize(read, file);
		return true;
	}

	@POST
	@Path("/changeArea")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Boolean changeArea(@FormParam("id") int id,
			@FormParam("area") double area, @Context ServletContext context)
			throws URISyntaxException, IOException {
		ArrayList<Territory> read = new ArrayList<Territory>();
		String fileName = context.getRealPath("/") + "/files/territories.ser";
		File file = new File(fileName);
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}
		for (Territory t : read) {
			if (t.getId() == id) {
				t.setArea(area);
			}
		}
		DataHandler.serialize(read, file);
		return true;
	}

	@POST
	@Path("/changePopulation")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Boolean changePopulation(@FormParam("id") int id,
			@FormParam("population") int population,
			@Context ServletContext context) throws URISyntaxException,
			IOException {
		ArrayList<Territory> read = new ArrayList<Territory>();
		String fileName = context.getRealPath("/") + "/files/territories.ser";
		File file = new File(fileName);
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}
		for (Territory t : read) {
			if (t.getId() == id) {
				t.setPopulation(population);
			}
		}
		DataHandler.serialize(read, file);
		return true;
	}

	@POST
	@Path("/delete")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Boolean deleteTerritory(@FormParam("id") int id,
			@Context ServletContext context) throws URISyntaxException,
			IOException {
		ArrayList<Territory> read = new ArrayList<Territory>();
		String fileName = context.getRealPath("/") + "/files/territories.ser";
		File file = new File(fileName);
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}
		Iterator<Territory> it = read.iterator();
		while (it.hasNext()) {
			if (it.next().getId() == id) {
				it.remove();
			}
		}
		DataHandler.serialize(read, file);
		return true;
	}

	@POST
	@Path("/add")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Boolean addTerritory(@FormParam("name") String name,
			@FormParam("area") int area,
			@FormParam("population") int population,
			@Context ServletContext context) throws URISyntaxException,
			IOException {
		ArrayList<Territory> read = new ArrayList<Territory>();
		String fileName = context.getRealPath("/") + "/files/territories.ser";
		File file = new File(fileName);
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}
		Random r = new Random();
		int id = r.nextInt();
		Territory t = new Territory(id, name, area, population);
		read.add(t);
		DataHandler.serialize(read, file);
		return true;
	}
}
