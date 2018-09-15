package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class StudentDatabaseTest {


	@Test
	public void testGetForJMBAG() {

		List<String> lines = readFromFile();
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		StudentRecord record2 = new StudentRecord("0000000017", "Grđan", "Goran", 2);

		
		Assert.assertEquals(record, studentDatabase.forJMBAG("0000000001"));
		Assert.assertEquals(record2, studentDatabase.forJMBAG("0000000017"));
	}
	
	@Test
	public void testFilterAlwaysTrue() {
		
		List<String> lines = readFromFile();
		StudentDatabase studentDatabase = new StudentDatabase(lines);

		Assert.assertEquals(63, studentDatabase.filter((record)-> true).size());

	}
	
	@Test
	public void testFilterAlwaysFalse() {
		
		List<String> lines = readFromFile();
		StudentDatabase studentDatabase = new StudentDatabase(lines);

		Assert.assertEquals(0, studentDatabase.filter((record)-> false).size());

	}

	public List<String> readFromFile() {
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
