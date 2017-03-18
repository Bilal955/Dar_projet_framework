package requetes;

import java.util.List;

import mapping.Mapping;
import response.Response;
import response.ResponseFactory;

public class AiguilleurRequetes {
	// TODO EN faite le gestionnaire c'est la method match dans Mapping, c'est elle qui aiguille)

	public static Response aiguillage(Request req) {
		return aiguillage(req, Mapping.mappings);
	}
	
	public static Response aiguillage(Request req, List<Mapping> mappings) {
		Mapping match = null;
		for(Mapping m : mappings) {
			if(m.match(req)) {
				match = m;
				break;
			}
		}
		if(match == null) {
			System.out.println("Aucun mapping pour l'url : '"+req.getRessourceComplete()+"' Methode : "+req.getMethode());
			return ResponseFactory.nothingMatch();
		}
		try {
			return match.executeFunction(req);
		} catch (ClassNotFoundException e1) { 
			return ResponseFactory.internalError(e1);
		}
	}

}
