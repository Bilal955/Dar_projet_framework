package mapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import constantes.ConstantesTools;
import tools.Tools;
import types.TypeRequete;

public class MappingParseur {


	/*
	 * Parse tous les fichiers de mapping
	 */
	public static List<Mapping> loadAllMapping() {
		return loadAllMapping(ConstantesTools.dirMapping);
	}
	// Fusionner avec en haut
	private static List<Mapping> loadAllMapping(String folder) {
		List<Mapping> res = new ArrayList<>();
		File dir = new File(folder);
		for(File file : dir.listFiles()) {
			try {
				res.addAll(parse(file.getPath()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return res;
	}


	/*
	 * Parse un fichier de mapping
	 */
	private static List<Mapping> parse(String filename) throws FileNotFoundException {
		//System.out.println("Filename Mapping--> "+filename);
		JSONTokener tokener = new JSONTokener(Tools.fileToString(filename));

		JSONArray root = new JSONArray(tokener);
		List<Mapping> res = new ArrayList<Mapping>();

		for(int i=0 ; i<root.length() ; i++) {
			JSONObject cur = root.getJSONObject(i);
			String ressource = cur.getString(MappingConstantes.ressource).replaceAll("/+", "/").trim();
			if(!ressource.equals("/") && ressource.charAt(ressource.length()-1) == '/')
				ressource = ressource.substring(0, ressource.length()-1); 
			String methode = cur.getString(MappingConstantes.methode).trim();
			String fonction = cur.getString(MappingConstantes.fonction).trim();
			String classFonction = cur.getString(MappingConstantes.classOfFunction).trim();
			String contentType = cur.has(MappingConstantes.contentType) ? cur.getString(MappingConstantes.contentType) : null;

			//	System.err.println("Ressource = "+ressource);

			List<String> query = new ArrayList<>();
			JSONArray querys = cur.getJSONArray(MappingConstantes.arguments);
			for(int j=0 ; j<querys.length() ; j++) {
				String q = querys.getString(j);
				query.add(q);
			}
			Mapping mapping = new Mapping(ressource, fonction, classFonction, TypeRequete.valueOf(methode.toUpperCase()), query, contentType);
			res.add(mapping);

		}
		return res;
	}	


}
