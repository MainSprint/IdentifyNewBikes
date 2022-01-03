package BaseClasses;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadProperties {

	static Properties prop;



	public static Properties readFile(String fileName) {
	try {
	// To read the property file
	FileInputStream file = new FileInputStream(fileName);
	prop = new Properties();
	prop.load(file);



	if (prop != null) {
	file.close();
	}



	else {
	System.out.println("Connection Failed with Property file");
	}
	} catch (Exception e) {
	System.out.println("Error in ReadProperties Class");
	}



	return prop;
	}
}
