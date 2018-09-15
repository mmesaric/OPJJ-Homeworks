package hr.fer.zemris.java.galerija.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;

/**
 * This class is responsible for creating the database (creating the collection
 * of picture entries which sets it up for DAO manipulation). File "opisnik.txt"
 * located in WEB-INF folder contains information about picture file names,
 * descriptions and tags.
 * 
 * @author Marko Mesarić
 *
 */
public class PictureDB {

	/**
	 * List of picture entries
	 */
	private static List<PictureEntry> pictures = new ArrayList<>();

	/**
	 * Method used for initializing the database by parsing the "opisnik.txt" file
	 * contained in WEB-INF folder.
	 * 
	 * @param sce servlet context event
	 */
	public static void initializeDB(ServletContextEvent sce) {
		Path picDirectory = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/opisnik.txt"));

		List<String> lines;
		try {
			lines = Files.readAllLines(picDirectory);
		} catch (IOException e) {
			throw new RuntimeException("Error when reading file containing information about pictures");
		}

		for (int i = 0; i < lines.size(); i += 3) {

			String fileName = lines.get(i).trim();
			String description = lines.get(i + 1).trim();
			String tagsLine = lines.get(i + 2).trim();

			if (fileName.isEmpty() || description.isEmpty() || tagsLine.isEmpty())
				throw new RuntimeException("Invalid picture descriptor file content.");

			String[] tags = tagsLine.split(",");

			List<String> tagsList = new ArrayList<>();
			for (String tag : tags) {
				tagsList.add(tag.trim());
			}

			pictures.add(new PictureEntry(fileName, description, tagsList));
		}
	}

	/**
	 * Getter for collection of pictures.
	 * @return {@link #pictures}
	 */
	public static List<PictureEntry> getPictures() {
		return pictures;
	}

}
