package hr.fer.zemris.java.hw16.trazilica;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class represents the implementation of a search engine based on TF-IDF
 * technique of getting document vectors. Program receives a single argument.
 * Path to folder containing all articles based on which vocabulary is built. It
 * also assumes that file containing stop words for this concrete problem is
 * located in project root folder and is named "stoprijeci.txt".
 * 
 * @author Marko Mesarić
 *
 */
public class Konzola {

	/**
	 * Main method which begins the execution of the program. Firstly, calls the
	 * appropriate methods for building vocabulary and document vectors then prompts
	 * the user to enter queries and after processing the keywords returns a list of
	 * results based on highest similarity between given document and all relevant
	 * documents.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Provide only 1 argument. Path to directory containing articles.");
			System.exit(1);
		}

		try {
			List<String> stopWords = Files.readAllLines(Paths.get("stoprijeci.txt"));

			System.out.println("Building vocabulary...");
			List<String> words = getWordCount(args[0], stopWords);
			System.out.println("Vocabulary size is: " + words.size());

			Map<String, Double> idf = new HashMap<>();
			System.out.println("Building document vectors...");
			List<DocumentVector> documentVectors = getDocumentVectors(words, idf, args[0], stopWords);

			List<DocumentResult> results = new ArrayList<>();
			Scanner scanner = new Scanner(System.in);
			outerloop: while (true) {
				System.out.print("Enter command > ");
				String command = scanner.nextLine();

				String[] splitCommand = command.trim().split("\\s+");

				switch (splitCommand[0]) {
				case "query":
					List<String> queryWords = new ArrayList<>();
					StringBuilder queryBuilder = new StringBuilder();
					queryBuilder.append("Query is: [");

					for (int i = 1; i < splitCommand.length; i++) {
						if (words.contains(splitCommand[i].trim())) {
							queryWords.add(splitCommand[i].trim());
							queryBuilder.append(splitCommand[i]).append(", ");
						}
					}
					System.out
							.println(queryBuilder.toString().substring(0, queryBuilder.toString().length() - 2) + "]");
					Map<String, Integer> tf = new HashMap<>();
					for (String word : queryWords) {
						tf.put(word, tf.get(word) == null ? 1 : tf.get(word) + 1);
					}

					Map<String, Double> tfidf = new HashMap<>();

					for (Map.Entry<String, Integer> tfValue : tf.entrySet()) {
						tfidf.put(tfValue.getKey(),
								tfValue.getValue() * Math.log(documentVectors.size() / idf.get(tfValue.getKey())));
					}
					results = new ArrayList<>();
					for (DocumentVector vector : documentVectors) {
						double similarity = similarity(vector.getTfIdf(), tfidf, words);

						if (similarity != 0) {
							// System.out.println(vector.getFilePath().getFileName() + " " + similarity);
							results.add(new DocumentResult(vector.getFilePath(), similarity));
						}
					}

					results.sort(Comparator.comparing(DocumentResult::getSimilarity,
							(a, b) -> Double.valueOf(b).compareTo(a.doubleValue())));

					int i = 0;
					for (DocumentResult result : results) {
						System.out.println("[" + i++ + "] (" + String.format("%.4f", result.getSimilarity()) + ") "
								+ result.getPath());
						if (i == 10)
							break;

					}
					break;

				case "type":
					if (splitCommand.length != 2) {
						System.out.println("Invalid command");
						continue;
					}
					try {
						int index = Integer.parseInt(splitCommand[1]);
						if (index > (results.size() - 1)) {
							System.out.println("Result entry with given index not found.");
							continue;
						}

						if (results.isEmpty()) {
							System.out.println("No entries in result set!");
							continue;
						}

						DocumentResult result = results.get(index);

						byte[] encoded = Files.readAllBytes(result.getPath());
						String fileContent = new String(encoded, "UTF-8");

						System.out.println("----------------------------------------------------------------");
						System.out.println("Dokument: " + result.getPath().toString());
						System.out.println("----------------------------------------------------------------");
						System.out.println(fileContent);
						System.out.println("\n\n----------------------------------------------------------------");
						break;
					} catch (NumberFormatException e) {
						System.out.println("Invalid command");
						continue;
					}
				case "results":

					if (splitCommand.length != 1) {
						System.out.println("Invalid command");
						continue;
					}
					if (results.isEmpty()) {
						System.out.println("No entries in result set!");
						continue;
					}
					int j = 0;
					for (DocumentResult result : results) {
						System.out.println("[" + j++ + "] (" + String.format("%.4f", result.getSimilarity()) + ") "
								+ result.getPath());
						if (j == 10)
							break;
					}
					break;

				case "exit":
					if (splitCommand.length != 1) {
						System.out.println("Invalid command");
						continue;
					}
					break outerloop;
				default:
					System.out.println("Invalid command");
				}
			}

			scanner.close();
			System.out.println("Bye!");

		} catch (IOException e1) {
			System.err.println("Unable to read the file containing stop words.");
			System.exit(1);
		}
	}

	/**
	 * Auxiliary method used for calculating similarity between given query tfIdf
	 * document vector and a single document vector from document "database".
	 * 
	 * @param tfIdf
	 *            document tfIdf vector
	 * @param queryTfIdf
	 *            query document tfIdf vector
	 * @param words
	 *            list of words forming vocabulary
	 * @return similarity between two compared vectors
	 */
	private static double similarity(Map<String, Double> tfIdf, Map<String, Double> queryTfIdf, List<String> words) {
		double dotProduct = 0;
		double magnitude1 = 0;
		double magnitude2 = 0;

		for (String word : words) {
			double value = tfIdf.get(word) == null ? 0 : tfIdf.get(word);
			double queryValue = queryTfIdf.get(word) == null ? 0 : queryTfIdf.get(word);

			dotProduct += value * queryValue;
			magnitude1 += (value * value);
			magnitude2 += (queryValue * queryValue);
		}

		magnitude1 = Math.sqrt(magnitude1);
		magnitude2 = Math.sqrt(magnitude2);

		return (magnitude1 != 0 && magnitude2 != 0) ? dotProduct / (magnitude1 * magnitude2) : 0;
	}

	/**
	 * Auxiliary method which builds document vectors after vocabulary has been
	 * built. In first iteration through documents, tf values are calculated and
	 * number of appearances of each word in each document is tracked. In second
	 * iteration TF-IDF component is calculated based on formerly calculated tf and
	 * idf components.
	 * 
	 * @param words
	 *            vocabulary
	 * @param idf
	 *            idf component
	 * @param articlesDirectory
	 *            directory containing all articles
	 * @param stopWords
	 *            collection of stop words
	 * @return list of document vectors
	 * @throws IOException
	 *             in case of exception
	 */
	private static List<DocumentVector> getDocumentVectors(List<String> words, Map<String, Double> idf,
			String articlesDirectory, List<String> stopWords) throws IOException {

		File[] articles = Paths.get(articlesDirectory).toFile().listFiles();
		List<DocumentVector> vector = new ArrayList<>();

		if (articles == null) {
			throw new IOException("Given file name wasn't a directory.");
		}

		for (File article : articles) {
			if (!article.isFile())
				continue;
			Map<String, Integer> tf = new HashMap<>();

			byte[] encoded = Files.readAllBytes(article.toPath());
			String fileContent = new String(encoded, "UTF-8");

			String[] splitText = fileContent.trim().split("[^\\p{IsAlphabetic}]");

			List<String> allMatches = new ArrayList<>();

			for (String word : splitText) {
				if (!word.isEmpty() && !stopWords.contains(word.toLowerCase())) {
					allMatches.add(word.toLowerCase());
				}
			}

			for (String word : allMatches) {
				tf.put(word, tf.get(word) == null ? 1 : tf.get(word) + 1);
			}

			List<String> noDuplicates = allMatches.stream().distinct().collect(Collectors.toList());

			for (String word : noDuplicates) {
				idf.put(word, idf.get(word) == null ? 1.0 : idf.get(word) + 1);
			}

			vector.add(new DocumentVector(article.toPath(), tf));
		}

		for (DocumentVector docVector : vector) {
			Map<String, Double> tfidf = new HashMap<>();
			Map<String, Integer> tf = docVector.getTf();

			for (Map.Entry<String, Integer> tfValue : tf.entrySet()) {
				tfidf.put(tfValue.getKey(), tfValue.getValue() * Math.log(vector.size() / idf.get(tfValue.getKey())));
			}
			docVector.setTfIdf(tfidf);
		}

		return vector;
	}

	/**
	 * Method responsible for building vocabulary based on list of stop words and
	 * all articles on which vocabulary is built.
	 * 
	 * @param articlesDirectory
	 *            directory containing articles
	 * @param stopWords
	 *            collection of stop words
	 * @return vocabulary
	 * @throws IOException
	 *             in case of exception
	 */
	private static List<String> getWordCount(String articlesDirectory, List<String> stopWords) throws IOException {

		File[] articles = Paths.get(articlesDirectory).toFile().listFiles();
		List<String> words = new ArrayList<>();

		if (articles == null) {
			throw new IOException("Given file name wasn't a directory.");
		}

		for (File article : articles) {
			if (!article.isFile())
				continue;

			byte[] encoded = Files.readAllBytes(article.toPath());
			String fileContent = new String(encoded, "UTF-8");

			String[] splitText = fileContent.trim().split("[^\\p{IsAlphabetic}]");

			for (String word : splitText) {
				if (!word.isEmpty() && !stopWords.contains(word.toLowerCase()) && !words.contains(word.toLowerCase())) {
					words.add(word.toLowerCase());
				}
			}
		}
		return words;
	}
}
