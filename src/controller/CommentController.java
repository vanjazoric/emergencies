/**
 * 
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import services.DataHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import beans.Comment;
import beans.Emergency;
import beans.User;

/**
 * @author Vanja
 *
 */
@Path("/comments")
public class CommentController {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Comment addComment(@FormParam("comment") String comment,
			@FormParam("id") int emId, @FormParam("username") String username,
			@FormParam("date") String date, @Context ServletContext context)
			throws JsonParseException, JsonMappingException, IOException,
			ParseException {

		DateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
		Date d = df.parse(date);
		String fileName2 = context.getRealPath("/") + "/files/users.ser";
		File file2 = new File(fileName2);
		User u = UserController.findUserByUsername(username, file2);
		Comment c = new Comment(comment, d, u);
		
		ArrayList<Emergency> read = new ArrayList<Emergency>();
		String fileName = context.getRealPath("/") + "/files/emergencies.ser";
		File file = new File(fileName);
		if (file.exists()) {
			read = DataHandler.deserialize(file);
		}
		for (Emergency e : read) {
			if (e.getId() == emId) {
				e.getComments().add(c);
			}
		}
		DataHandler.serialize(read, file);
		return c;
	}
}
