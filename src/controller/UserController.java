/**
 * 
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import services.DataHandler;
import beans.Admin;
import beans.Role;
import beans.Territory;
import beans.User;
import beans.Volunteer;

/**
 * @author Vanja
 *
 */

@Path("/users")
public class UserController {

	@POST
	@Path("/register")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response register(@FormDataParam("firstName") String firstName,
			@FormDataParam("lastName") String lastName,
			@FormDataParam("username") String username,
			@FormDataParam("password") String password,
			@FormDataParam("phone") String phone,
			@FormDataParam("email") String email,
			@FormDataParam("territory") String territoryName,
			@FormDataParam("picture") InputStream picture,
			@FormDataParam("picture") FormDataContentDisposition fileDetails,
			@Context ServletContext context) throws URISyntaxException,
			IOException {

		String fileName = context.getRealPath("/") + "/files/territories.ser";
		File file = new File(fileName);
		Territory territory = TerritoryController.findTerritoryByName(
				territoryName, file);
		Volunteer volunteer = new Volunteer(username, password, firstName,
				lastName, phone, email, fileDetails.getFileName(),
				Role.VOLUNTEER, territory, false);

		if (!fileDetails.getFileName().equals("")) {
			String uploadedFileLocation = context.getRealPath("/") + "images/"
					+ fileDetails.getFileName();

			// save uploaded picture to file
			DataHandler.savePicture(picture, uploadedFileLocation);
		}
		ArrayList<User> read = new ArrayList<User>();

		String fileName2 = context.getRealPath("/") + "/files/users.ser";
		File file2 = new File(fileName2);
		if (file2.exists()) {
			read = DataHandler.deserialize(file2);
		}
		read.add((User) volunteer);
		DataHandler.serialize(read, file2);
		java.net.URI location = new java.net.URI("../../Emergencies/login.html");
		return Response.temporaryRedirect(location).build();
	}

	@POST
	@Path("/checkUsername")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Boolean checkUsername(@FormParam("username") String username,
			@Context ServletContext context) throws URISyntaxException,
			IOException {
		System.out.println("aaa");
		String fileName = context.getRealPath("/") + "/files/users.ser";
		File file = new File(fileName);
		User user = findUserByUsername(username, file);
		if (user == null){
			return true;
		}
		else{
			return false;
		}
	}
	
	@POST
	@Path("/checkEmail")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Boolean checkEmail(@FormParam("email") String email,
			@Context ServletContext context) throws URISyntaxException,
			IOException {
		String fileName = context.getRealPath("/") + "/files/users.ser";
		File file = new File(fileName);
		User user = findUserByEmail(email, file);
		if (user == null){
			return true;
		}
		else{
			return false;
		}
	}

	@GET
	@Path("/loginTest")
	@Produces(MediaType.APPLICATION_JSON)
	public User testLogin(@Context HttpServletRequest request,
			@Context ServletContext context) {
		User retVal = null;
		retVal = (User) request.getSession().getAttribute("user");
		if (retVal != null && retVal.getRole() == Role.VOLUNTEER) {
			String fileName = context.getRealPath("/") + "/files/users.ser";
			File file = new File(fileName);
			Volunteer v = VolunteerController.findVolunteerByUsername(
					retVal.getUsername(), file);
			retVal = v;
		}
		System.out.println("\n\nUlogovan: " + retVal);
		return retVal;
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public User login(@FormParam("username") String username,
			@FormParam("password") String password,
			@Context ServletContext context, @Context HttpServletRequest request)
			throws URISyntaxException {
		ArrayList<User> read = new ArrayList<User>();
		String fileName = context.getRealPath("/") + "/files/users.ser";
		File file = new File(fileName);
		read = DataHandler.deserialize(file);

		User retVal = (User) request.getSession().getAttribute("user");
		if (retVal == null) {
			for (User u : read) {

				if (u.getUsername().equals(username)
						&& u.getPassword().equals(password)) {
					request.getSession().setAttribute("user", u);
					retVal = u;
					return u;
				} else {
					continue;
				}
			}
		}
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String loadUsers(@Context ServletContext context)
			throws JsonProcessingException {

		ArrayList<User> read = new ArrayList<User>();
		String fileName = context.getRealPath("/") + "/files/users.ser";
		File file = new File(fileName);
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		} else {
			User a1 = new Admin("admin", "123456", "Admin", "Admirovic",
					"0643891303", "admin@gmail.com", "1223.jpg", Role.ADMIN);
			User a2 = new Admin("vanja_admin", "123456", "Vanja", "Zorić",
					"0643891303", "vanja@gmail.com", "1223.jpg", Role.ADMIN);
			User a3 = new Volunteer("vanjaa", "123456", "Vanja", "Zzzz",
					"0643891303", "vanj@gmail.com", "1223.jpg", Role.VOLUNTEER,
					new Territory(1, "Novi Sad", 702.2, 286546), false);
			read.add(a1);
			read.add(a2);
			read.add(a3);
			DataHandler.serialize(read, file);
		}
		ObjectMapper mapper = new ObjectMapper();
		// Object to JSON in String
		String jsonInString = mapper.writeValueAsString(read);
		System.out.println("KORISNICI" + jsonInString);
		return jsonInString;
	}

	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logout(@Context HttpServletRequest request)
			throws URISyntaxException {
		User user = null;
		user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			request.getSession().invalidate();
		}
		java.net.URI location = new java.net.URI("../../Emergencies/login.html");
		return Response.temporaryRedirect(location).build();
	}

	public static User findUserByUsername(String username, File file) {
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
		return retVal;
	}
	
	public static User findUserByEmail(String email, File file) {
		ArrayList<User> read = new ArrayList<User>();
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}
		User retVal = null;
		for (User user : read) {
			if (user.getEmail().equalsIgnoreCase(email)) {
				retVal = user;
			}
		}
		return retVal;
	}

	@POST
	@Path("/profile")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public User profile(@FormParam("username") String username,
			@Context ServletContext context) throws URISyntaxException {

		String fileName = context.getRealPath("/") + "/files/users.ser";
		File file = new File(fileName);
		User user = UserController.findUserByUsername(username, file);
		return user;
	}

	@POST
	@Path("/blockUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Volunteer blockUser(@FormParam("username") String username,
			@Context ServletContext context) throws URISyntaxException {

		ArrayList<User> read = new ArrayList<User>();
		String fileName = context.getRealPath("/") + "/files/users.ser";
		File file = new File(fileName);
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}
		for (User user : read) {
			if (user.getUsername().equals(username)) {
				((Volunteer) user).setIsBlocked(true);
				DataHandler.serialize(read, file);
				for (User use : read) {
					System.out.println("BLOCK FUNKCIJA " + use);
				}
				return (Volunteer) user;
			}
		}
		return null;
	}

	@POST
	@Path("/unblockUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Volunteer unblockUser(@FormParam("username") String username,
			@Context ServletContext context) throws URISyntaxException {

		ArrayList<User> read = new ArrayList<User>();
		String fileName = context.getRealPath("/") + "/files/users.ser";
		File file = new File(fileName);
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}
		for (User user : read) {
			if (user.getUsername().equals(username)) {
				((Volunteer) user).setIsBlocked(false);
				DataHandler.serialize(read, file);
				for (User use : read) {
					System.out.println("UNBLOCK FUNKCIJA " + use);
				}
				return (Volunteer) user;
			}
		}
		return null;
	}
}