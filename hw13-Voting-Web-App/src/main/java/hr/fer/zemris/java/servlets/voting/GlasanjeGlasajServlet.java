package hr.fer.zemris.java.servlets.voting;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is called with a single parameter, id of the band which got the
 * up-vote. It's job is to check if results txt file exists and create it, if it
 * isn't present. Increases the number of total votes for song given by passed
 * id.
 * 
 * @author Marko Mesarić
 *
 */
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	// private volatile boolean voted = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// if (voted) {
		// resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
		// return;
		// }

		String id = req.getParameter("id");

		if (id == null)
			return;

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

		Path filePath = Paths.get(fileName);
		synchronized (this.getClass()) {
			if (!Files.exists(filePath)) {

				String bandsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
				Path bandsFilePath = Paths.get(bandsFileName);
				if (bandsFilePath == null)
					return;

				List<String> lines = Files.readAllLines(bandsFilePath);

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < lines.size(); i++) {
					sb.append((i + 1) + "\t0\n");
				}

				writeToOuput(filePath, sb.toString().substring(0, sb.length() - 1));
			}

			List<String> lines = Files.readAllLines(Paths.get(fileName));

			StringBuilder resultBuilder = new StringBuilder();

			for (String line : lines) {
				if (line.startsWith(id)) {
					String[] splitLine = line.split("\t");
					int counter = Integer.parseInt(splitLine[1]);
					line = splitLine[0] + "\t" + (++counter);
				}
				resultBuilder.append(line).append("\n");
			}
			writeToOuput(filePath, resultBuilder.toString().substring(0, resultBuilder.length() - 1));
			// voted= true;
			resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
		}

	}

	/**
	 * Auxiliary method used for writing given content into output file
	 * 
	 * @param filePath
	 *            path to output file
	 * @param content
	 *            content to be written
	 * @throws IOException
	 *             in case of error
	 */
	public void writeToOuput(Path filePath, String content) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(filePath, Charset.forName("UTF-8"));
		writer.write(content);
		writer.close();
	}
}
