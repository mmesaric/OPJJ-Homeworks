package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class QueryFilterTest {

	@Test
	public void testFilterDirectQuery() {
		String query = " jmbag = \"0000000003\"";
		List<String> lines = readFromFile();
		StudentDatabase db = new StudentDatabase(lines);
		QueryParser parser = new QueryParser(query);
		StudentRecord expected = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);

		 StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
		
		Assert.assertEquals(true, parser.isDirectQuery());
		Assert.assertEquals(true, expected.equals(r));
	}
	
	@Test
	public void testFilterNonDirectQuery() {
		String query = "  jmbag = \"0000000003\" AND lastName LIKE \"B*\"";
		List<String> lines = readFromFile();
		StudentDatabase db = new StudentDatabase(lines);
		QueryParser parser = new QueryParser(query);
		StudentRecord expected = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);

		List<StudentRecord> studentRecords = db.filter(new QueryFilter(parser.getQuery()));
		
		Assert.assertEquals(false, parser.isDirectQuery());
		Assert.assertEquals(true, expected.equals(studentRecords.get(0)));
	}
	
	@Test
	public void testFilterEmptyResult() {
		String query = " jmbag = \"0000000003\" AND lastName LIKE \"L*\"";
		List<String> lines = readFromFile();
		StudentDatabase db = new StudentDatabase(lines);
		QueryParser parser = new QueryParser(query);

		List<StudentRecord> studentRecords = db.filter(new QueryFilter(parser.getQuery()));
		
		Assert.assertEquals(false, parser.isDirectQuery());
		Assert.assertEquals(0, studentRecords.size());
	}
	
	@Test
	public void testResultSetSizeFour() {
		String query = "   lastName LIKE \"B*\"";
		List<String> lines = readFromFile();
		StudentDatabase db = new StudentDatabase(lines);
		QueryParser parser = new QueryParser(query);

		List<StudentRecord> studentRecords = db.filter(new QueryFilter(parser.getQuery()));
		
		Assert.assertEquals(false, parser.isDirectQuery());
		Assert.assertEquals(4, studentRecords.size());
	}
	
	@Test
	public void testQueryWithTabsAndSpacesExampleOne() {
		String query = "  lastName=\"Bosnić\"";
		List<String> lines = readFromFile();
		StudentDatabase db = new StudentDatabase(lines);
		QueryParser parser = new QueryParser(query);
		StudentRecord expected = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);

		List<StudentRecord> studentRecords = db.filter(new QueryFilter(parser.getQuery()));
		
		Assert.assertEquals(false, parser.isDirectQuery());
		Assert.assertEquals(true, expected.equals(studentRecords.get(0)));
	}
	
	@Test
	public void testQueryWithTabsAndSpacesExampleTwo() {
		String query = "  lastName =\"Bosnić\"\r\n" + 
				"";
		List<String> lines = readFromFile();
		StudentDatabase db = new StudentDatabase(lines);
		QueryParser parser = new QueryParser(query);
		StudentRecord expected = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);

		List<StudentRecord> studentRecords = db.filter(new QueryFilter(parser.getQuery()));
		
		Assert.assertEquals(false, parser.isDirectQuery());
		Assert.assertEquals(true, expected.equals(studentRecords.get(0)));
	}
	
	@Test
	public void testQueryWithTabsAndSpacesExampleThree() {
		String query = "  lastName= \"Bosnić\"\r\n";
		List<String> lines = readFromFile();
		StudentDatabase db = new StudentDatabase(lines);
		QueryParser parser = new QueryParser(query);
		StudentRecord expected = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);

		List<StudentRecord> studentRecords = db.filter(new QueryFilter(parser.getQuery()));
		
		Assert.assertEquals(false, parser.isDirectQuery());
		Assert.assertEquals(true, expected.equals(studentRecords.get(0)));
	}
	
	@Test
	public void testQueryWithTabsAndSpacesExampleFour() {
		String query = "  lastName= \"Bosnić\"\r\n";
		List<String> lines = readFromFile();
		StudentDatabase db = new StudentDatabase(lines);
		QueryParser parser = new QueryParser(query);
		StudentRecord expected = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);

		List<StudentRecord> studentRecords = db.filter(new QueryFilter(parser.getQuery()));
		
		Assert.assertEquals(false, parser.isDirectQuery());
		Assert.assertEquals(true, expected.equals(studentRecords.get(0)));
	}

	public static List<String> readFromFile() {
		try {
			List<String> lines = Files.readAllLines(Paths.get("src/main/resources/database.txt"),
					StandardCharsets.UTF_8);
			return lines;

		} catch (IOException e) {
			System.out.println("Cannot read file: database.txt");
			System.exit(1);
		}

		return null;
	}

}
