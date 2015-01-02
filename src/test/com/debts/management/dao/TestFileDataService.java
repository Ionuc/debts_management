package test.com.debts.management.dao;

import main.com.debts.management.dao.FileDataService;

import org.junit.Test;

public class TestFileDataService {

	private FileDataService fileDataService = new FileDataService("test");
	@Test
	public void testWriteInfo() {
		fileDataService.writeInfo("Test");
	}
}
