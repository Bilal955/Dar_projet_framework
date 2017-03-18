package controller;

import java.io.FileNotFoundException;
import java.util.Iterator;

import org.json.JSONObject;
import org.json.JSONTokener;

import constantes.ConstantesTools;
import requetes.RequestToFunc;
import response.Response;
import response.ResponseFactory;
import session.Etat;
import tools.Tools;

public class PointsFonctions {
	
	private final String filename = ConstantesTools.dirRessources + "listePoints.json";
	private final String idFilename = ConstantesTools.dirRessources + "id.txt";
	
	private JSONObject getRootFromFile() throws FileNotFoundException {
		JSONTokener tokener = new JSONTokener(Tools.fileToString(filename));
		return new JSONObject(tokener);
	}
	
	private int getnextId() {
		int next = 0;
		try {
			next = Integer.parseInt(Tools.fileToString(idFilename).trim());
		} catch (NumberFormatException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return next;
	}
	
	private int nextId() {
		int next = getnextId();
		Tools.overrideFile(idFilename, (next+1)+"");
		return next;
	}
	
	public Response majPoint(RequestToFunc r, Response resp, Etat etat) {
		JSONObject root;
		try {
			root = getRootFromFile();
		} catch (FileNotFoundException e) {
			return ResponseFactory.internalError("File not exist");
		}
		String id = r.getPathRessource().get(1);
		String x = r.getQuery("x");
		String y = r.getQuery("y");
		
		
		System.out.println("X = "+x+" Y = "+y);
		root.getJSONObject(id).accumulate("x", x);
		root.getJSONObject(id).accumulate("y", y);
		Tools.overrideFile(filename, root.toString());
		
		return resp;
	}
	
	
	public Response createPoint(RequestToFunc r, Response resp, Etat etat) {
		JSONObject root;
		try {
			root = getRootFromFile();
		} catch (FileNotFoundException e) {
			return ResponseFactory.internalError("File not exist");
		}
		int id = nextId();
		System.out.println("=====> "+r.getBody());
		JSONObject jBody = new JSONObject(new JSONTokener(r.getBody()));
		int x = jBody.getInt("x");
		int y = jBody.getInt("y");
		JSONObject coord = new JSONObject();
		coord.put("x", x+"");
		coord.put("y", y+"");
		root.put(id+"", coord);
		Tools.overrideFile(filename, root.toString());
		resp.setBody(id+"");
		return resp;
	}
	
	public Response deletePoint(RequestToFunc r, Response resp, Etat etat) {
		JSONObject root;
		try {
			root = getRootFromFile();
		} catch (FileNotFoundException e) {
			return ResponseFactory.internalError("File not exist");
		}
		String id = r.getPathRessource().get(1);
		root.remove(id);
		Tools.overrideFile(filename, root.toString());
		resp.setBody("C'est ok !!");
		return resp;
	}
	
	public Response getX(RequestToFunc r, Response resp, Etat etat) {
		JSONObject root;
		try {
			root = getRootFromFile();
		} catch (FileNotFoundException e) {
			return ResponseFactory.internalError("File not exist");
		}
		String id = r.getPathRessource().get(1);
		String x = root.getJSONObject(id).getString("x");
		resp.setBody("Points "+id+" [x="+x+"]\n");
		
		return resp;
	}

	public Response getY(RequestToFunc r, Response resp, Etat etat) {
		JSONObject root;
		try {
			root = getRootFromFile();
		} catch (FileNotFoundException e) {
			return ResponseFactory.internalError("File not exist");
		}
		
		String id = r.getPathRessource().get(1);
		String y = root.getJSONObject(id).getString("y");
		resp.setBody("Points "+id+" [y="+y+"]\n");
		return resp;
	}
	
	public Response listAll(RequestToFunc r, Response resp, Etat etat) {
		JSONObject root;
		try {
			root = getRootFromFile();
		} catch (FileNotFoundException e) {
			return ResponseFactory.internalError("File not exist");
		}
	
		StringBuilder res = new StringBuilder();
		Iterator<?> it = root.keys();
		while(it.hasNext()) {
			String cle = (String)it.next();
			JSONObject o = root.getJSONObject(cle);
			String x = o.getString("x");
			String y = o.getString("y");
			res.append("Points "+cle+" [x="+x+", y="+y+"]</br>");

		}
		/*Set<String> cles = (Set<String>)root.keySet();
		for(String cle : cles) {
			JSONObject o = root.getJSONObject(cle);
			String x = o.getString("x");
			String y = o.getString("y");
			res.append("Points "+cle+" [x="+x+", y="+y+"]\n");
		}*/
		resp.setBody(res.toString());
		return resp;
	}
	
	
}
