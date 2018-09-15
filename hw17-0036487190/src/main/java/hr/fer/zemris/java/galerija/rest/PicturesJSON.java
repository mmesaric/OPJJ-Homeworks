package hr.fer.zemris.java.galerija.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.java.galerija.model.PictureDB;
import hr.fer.zemris.java.galerija.model.PictureEntry;

/**
 * This class uses GSON library for communication between AJAX and server. JSON
 * format is used as a form of communication.
 * 
 * @author Marko Mesarić
 *
 */
@Path("/pictures")
public class PicturesJSON {

	/**
	 * This method is called in case of accessing /rest/pictures with GET URL
	 * method. It returns a JSON as String containing all unique tags from picture
	 * database.
	 * 
	 * @return Response containing status code and result
	 */
	@GET
	@Produces("application/json")
	public Response getTags() {
		Gson gson = new Gson();

		Set<String> uniqueTags = new HashSet<>();
		List<PictureEntry> pictures = PictureDB.getPictures();

		for (PictureEntry picture : pictures) {
			for (String tag : picture.getTags()) {
				uniqueTags.add(tag);
			}
		}

		String result = gson.toJson(uniqueTags);

		return Response.status(Status.OK).entity(result).build();
	}

	/**
	 * This method is called in case of accessing /rest/pictures/{tagName} with GET
	 * URL method. It returns a JSON as String containing all picture file names
	 * which contain given tag name from picture database.
	 * 
	 * @return Response containing status code and result
	 */
	@Path("{tagName}")
	@GET
	@Produces("application/json")
	public Response getPicturesByTag(@PathParam("tagName") String tagName) {
		List<PictureEntry> pictures = PictureDB.getPictures();
		Set<String> picNames = new HashSet<>();

		for (PictureEntry picture : pictures) {
			List<String> tags = picture.getTags();

			if (tags.contains(tagName)) {
				picNames.add(picture.getFileName());
			}
		}

		if (picNames.size() == 0) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Gson gson = new Gson();
		String result = gson.toJson(picNames);

		return Response.status(Status.OK).entity(result).build();
	}

	/**
	 * This method is called in case of accessing /rest/pictures/fileName/{fileName}
	 * with GET URL method. It returns a JSON String for picture entry found by
	 * given picture file name.
	 * 
	 * @return Response containing status code and result
	 */
	@Path("/fileName/{fileName}")
	@GET
	@Produces("application/json")
	public Response getPictureByName(@PathParam("fileName") String fileName) {
		List<PictureEntry> pictures = PictureDB.getPictures();
		PictureEntry entry = null;

		for (PictureEntry picture : pictures) {
			if (picture.getFileName().equals(fileName)) {
				entry = picture;
				break;
			}
		}

		if (entry == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Gson gson = new Gson();
		String result = gson.toJson(entry);

		return Response.status(Status.OK).entity(result).build();
	}

}
