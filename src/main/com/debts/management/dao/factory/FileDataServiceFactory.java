package main.com.debts.management.dao.factory;

import main.com.debts.management.dao.FileDataService;

public class FileDataServiceFactory {

	private static FileDataService FILE_DATA_SERVICE;

	public static FileDataService getFileDataService(String classCaller) {
		if (FILE_DATA_SERVICE == null) {
			FILE_DATA_SERVICE = new FileDataService(classCaller);
		}
		return FILE_DATA_SERVICE;
	}
}
