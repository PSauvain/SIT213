package programme;
import java.util.Random;

/**
 * Classe simulant une source fixe
 * 
 * @author Romain & Nicolas
 */

public class SourceFixe extends Source<Boolean> {
	String chaineAEmettre;
	int nombreAEmettre;
	boolean utilisationSondes;

	/**
	 * @param chaineAEmettre
	 *            Chaîne de caractères de 0 et de 1
	 * @param utilisationSondes
	 * @throws InformationNonConforme
	 *             Crée une information à partir d'une séquence booléenne
	 *             définie par l'utilisateur et l'envoie sur un support de
	 *             transmission
	 */
	public SourceFixe(String chaineAEmettre, boolean utilisationSondes)
			throws InformationNonConforme {

		this.chaineAEmettre = chaineAEmettre;
		this.utilisationSondes = utilisationSondes;

		Boolean[] info = new Boolean[chaineAEmettre.length()];

		for (int i = 0; i < chaineAEmettre.length(); i++) { // itération de la
															// chaîne de 0 et de
															// 1
			switch (chaineAEmettre.charAt(i)) { // conversion 0/1 -> équivalent
												// booléen
			case '0':
				info[i] = false;
				break;
			case '1':
				info[i] = true;
				break;
			}
		}

		Information<Boolean> infos = new Information<Boolean>(info);
		informationGeneree = infos;

	}

	/**
	 * @param nombreAEmettre
	 * @param utilisationSondes
	 *            Crée une information à partir d'une séquence booléenne
	 *            aléatoire de taille fixée par l'utilisateur et l'envoie sur un
	 *            support de transmission
	 */
	public SourceFixe(int nombreAEmettre, boolean utilisationSondes) {

		this.nombreAEmettre = nombreAEmettre;
		this.utilisationSondes = utilisationSondes;

		Boolean[] info = new Boolean[nombreAEmettre];
		Random rand = new Random();
		int rand_int;
		for (int i = 0; i < nombreAEmettre; i++) {
			rand_int = rand.nextInt(2); // nombre aléatoire entre 0 inclus et 2
										// exclu -> 0 ou 1
			if (rand_int == 1) // conversion 0/1 -> équivalent booléen
				info[i] = true;
			else
				info[i] = false;

		}
		Information<Boolean> infos = new Information<Boolean>(info);
		informationGeneree = infos;
	}

	/**
	 * @throws InformationNonConforme
	 *             Emet l'information sur le canal de transmission
	 */
	public void emettre() throws InformationNonConforme {
		informationEmise = informationGeneree; // Pas de pertes

		if (utilisationSondes) {
			SondeLogique sl1 = new SondeLogique("sl1");
			sl1.recevoir(informationEmise);
		}
		
		for (DestinationInterface<Boolean> dest : destinationsConnectees)
			dest.recevoir(informationEmise);

	}

	/**
	 * Getter de InformationEmise
	 */
	public Information<Boolean> getInformationEmise() {
		return informationEmise;
	}

}
