package hr.fer.zemris.java.servlets.voting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet, when called, prepares the necessary data which
 * "glasanjeRez.jsp" has to display to user. Generates and fills the list of
 * entries containing information about band, their ID, number of votes and link
 * to some song.
 * 
 * @author Marko Mesarić
 *
 */
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String resultsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String bandsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Path votesFilePath = Paths.get(resultsFileName);
		Path bandsFilePath = Paths.get(bandsFileName);

		if (!Files.exists(votesFilePath)) {
			List<String> bandsLines = Files.readAllLines(bandsFilePath);
			List<VoteEntry> voteEntries = new ArrayList<>();

			for (String line : bandsLines) {

				if (line == null || line.isEmpty())
					continue;
				String[] splitLine = line.split("\t");

				int id = Integer.parseInt(splitLine[0]);
				String bandName = splitLine[1];
				String songLink = splitLine[2];

				voteEntries.add(new VoteEntry(id, 0, bandName, songLink));
			}
			req.setAttribute("voteEntries", voteEntries);

		}

		else {
			List<String> votesLines = Files.readAllLines(votesFilePath);
			List<String> bandsLines = Files.readAllLines(bandsFilePath);
			List<VoteEntry> voteEntries = new ArrayList<>();

			for (String line : votesLines) {

				if (line == null || line.isEmpty())
					continue;

				String[] splitLine = line.split("\t");

				if (splitLine.length != 2)
					continue;
				int id = Integer.parseInt(splitLine[0]);
				int numberOfVotes = Integer.parseInt(splitLine[1]);

				String bandName = bandsLines.get(id - 1).split("\t")[1];
				String songLink = bandsLines.get(id - 1).split("\t")[2];

				voteEntries.add(new VoteEntry(id, numberOfVotes, bandName, songLink));

			}
			voteEntries.sort(Comparator.comparing(VoteEntry::getNumberOfVotes,
					(a, b) -> Integer.valueOf(b).compareTo(a.intValue())));
			req.setAttribute("voteEntries", voteEntries);

			List<VoteEntry> winnerSongs = new ArrayList<>();
			int maxVotes = voteEntries.get(0).numberOfVotes;
			for (VoteEntry entry : voteEntries) {
				if (entry.numberOfVotes >= maxVotes) {
					winnerSongs.add(entry);
				}
			}
			req.setAttribute("winnerSongs", winnerSongs);

		}
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * This class models a single entry containing information about bands and
	 * number of votes scored
	 * 
	 * @author Marko Mesarić
	 *
	 */
	public static class VoteEntry {
		/**
		 * band id
		 */
		private int id;
		/**
		 * number of votes 
		 */
		private int numberOfVotes;
		/**
		 * name of band
		 */
		private String bandName;
		/**
		 * youtube link to song
		 */
		private String songLink;

		/**
		 * 
		 * @param id {@link #id}
		 * @param numberOfVotes {@link #numberOfVotes}
		 * @param bandName {@link #bandName}
		 * @param songLink {@link #songLink}
		 */
		public VoteEntry(int id, int numberOfVotes, String bandName, String songLink) {
			super();
			this.id = id;
			this.numberOfVotes = numberOfVotes;
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
		 * Getter for number of votes 
		 * @return {@link #numberOfVotes}
		 */
		public int getNumberOfVotes() {
			return numberOfVotes;
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
