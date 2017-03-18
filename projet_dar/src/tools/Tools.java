package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import types.TypeRequete;

public class Tools {


	public static TypeRequete strToType(String s) {
		return TypeRequete.valueOf(s.toUpperCase());
	}
	
	public static void overrideFile(String filename, String s) {
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(filename));
			br.write(s);
			br.flush();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static String fileToString(String filename) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String everything = null;
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    everything = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		    try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return everything;
	}

}
