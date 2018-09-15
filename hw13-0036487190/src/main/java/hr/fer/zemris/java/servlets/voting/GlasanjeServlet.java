package hr.fer.zemris.java.servlets.voting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet, which, when called reads data source file containing informations
 * about bands for which users vote and parses the data into a list which is
 * forwarded to "glasanjeIndex.jsp" as attribute.
 * 
 * @author Marko Mesarić
 *
 */
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<BandEntry> entries = new ArrayList<>();

		for (String line : lines) {

			if (line == null || line.isEmpty())
				continue;

			String[] splitLine = line.split("\t");

			if (splitLine.length != 3)
				continue;

			int id = Integer.parseInt(splitLine[0]);
			String bandName = splitLine[1];
			String songLink = splitLine[2];

			entries.add(new BandEntry(id, bandName, songLink));
		}

		// req.getSession().setAttribute("bandsList", entries);
		req.setAttribute("bandsList", entries);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);

	}

	/**
	 * This class models a single band entry containing information about bands
	 * 
	 * @author Marko Mesarić
	 *
	 */
	public static class BandEntry {
		/**
		 * band id
		 */
		private int id;
		/**
		 * name of the band
		 */
		private String bandName;
		/**
		 * youtube link to song
		 */
		private String songLink;

		/**
		 * 
		 * @param id {@link #id}
		 * @param bandName {@link #bandName}
		 * @param songLink {@link #songLink}
		 */
		public BandEntry(int id, String bandName, String songLink) {
			super();
			this.id = id;
			this.bandName = bandName;
			this.songLink = songLink;
		}

		/**
		 * Getter for id
		 * @return {@link #id}
		 */
		public int getId() {
			return id;
		}

		/**
		 * Getter for band name
		 * @return {@link #bandName}
		 */
		public String getBandName() {
			return bandName;
		}

		/**
		 * Getter for song link
		 * @return {@link #songLink}
		 */
		public String getSongLink() {
			return songLink;
		}
	}
}
