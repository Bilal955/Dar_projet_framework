package template;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tools.Tools;

public class TemplateTools {

	private static String varRegex = "(\\$\\{\\s*(.+)\\s*\\})";
	
	
	
	public static String executeTemplateFile(String filename, Map<String, String> values) throws FileNotFoundException {
		return varToRealValue(Tools.fileToString(filename), values);
	}
	
	// Ajouter une fonction page fichier dans fichier
	
	
	// C'est mal car String immutable, mais bon ... pas le courage de jouer avec les indices des StringBuilder
	public static String varToRealValue(String content, Map<String, String> values) {
		Pattern pattern = Pattern.compile(varRegex);
		Matcher matcher = pattern.matcher(content);
		String myRes = content;
		while(matcher.find()) {
			String allExpr = matcher.group(1);
			String varName = matcher.group(2).trim();
			String newValue = values.get(varName);
			myRes = myRes.replace(allExpr, newValue);
		}
		return myRes;
	}
	
	
}
