package gestionnaire;

import java.util.concurrent.Executors;

import constantes.ConstantesTools;

public class ThreadTools {

	public static void tmp() {
		int processusDispo = Runtime.getRuntime().availableProcessors();
		System.out.println("Nb processus dispo = "+processusDispo);
		Executors.newFixedThreadPool(ConstantesTools.NB_THREAD_MAX);
	}

}
