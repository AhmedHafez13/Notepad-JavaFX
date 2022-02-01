package javafxnotepad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class StorageManager {
	public static String readFile(File file) {
		try (FileInputStream fis = new FileInputStream(file)) {
			int size = fis.available();
			byte[] b = new byte[size];
			fis.read(b);
			//System.out.println(new String(b));
			fis.close();
			return new String(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeFile(File file, String data) {
		try {
			//Opening a file in append mode using FileWriter
			FileWriter fileWriter = new FileWriter(file);
			//Writing text to file
			fileWriter.write(data);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
