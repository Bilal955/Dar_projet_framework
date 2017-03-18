package launcher;

import java.io.IOException;

import constantes.ConstantesTools;
import serveur.ServerRun;

public class LaunchServeur {
	
	// http://127.0.0.1:1025

	public static void main(String[] args) throws IOException {
		System.out.println("My New Project");
		setConfig();
		ServerRun.runServeur();
	}

	
	private static void setConfig() {
		ConstantesTools.dirMapping = Configuration.dirMapping;
		ConstantesTools.dirRessources = Configuration.dirRessources;
		ConstantesTools.packageFonctions = Configuration.packageFonctions;
		ConstantesTools.dirSessions = Configuration.dirSessions;
		ConstantesTools.nbDaysCookies = Configuration.nbDaysCookies;
		
		ConstantesTools.SERVER_NAME = Configuration.SERVER_NAME;
		ConstantesTools.NB_THREAD_MAX = Configuration.NB_THREAD_MAX;
		ConstantesTools.PORT = Configuration.PORT;
	}
}
