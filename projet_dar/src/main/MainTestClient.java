package main;

import requetes.Request;
import types.TypeRequete;

public class MainTestClient {

	public static void main(String[] args) throws Exception {
	
		System.out.println("Sock open");
		
		Request r = new Request("localhost", TypeRequete.GET, "1785-futur");
		r.addHeader("cle1", "valeur1");
		r.addHeader("cle2", "valeur2");
		r.addHeader("cle3", "valeur3");
		r.addHeader("cle4", "valeur4");	
		r.setBody("Yo comment vas tu ?\n\n\nCool\n\n\t\n\t\t");
		
		
		//System.out.println("["+r.toString()+"]");
		//Request re = RequestParser.parse(r.toString());


		
	}

}
