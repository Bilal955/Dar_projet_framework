package main;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import template.TemplateTools;
import tools.Tools;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		Map<String, String> map = new HashMap<>();
		map.put("nom", "Dupont");
		map.put("prenom", "louis");
		map.put("pseudo", "Xp75");
		
		String tmp = Tools.fileToString("ressources/hello.html");
		System.out.println(TemplateTools.varToRealValue(tmp, map));
	}

}
