package programme;

/**
 * Classe Récepteur RZ. Elle récupère le signal (Information Float)
 * qui suit une modulation RZ et renvoie le résultat en Information
 *  Boolean
 * @author R. Annereau, N. Buzy-Debat, A. Courtois, A. Maréchal
 * 
 */

public class RecepteurRZ extends Recepteur {

	/**
	 * Constructeur
	 * @param utilisationSondes
	 * @param ampMin
	 * @param ampMax
	 * @param nbEch
	 */
	public RecepteurRZ(boolean utilisationSondes, float ampMin, float ampMax,
			int nbEch) {
		super(utilisationSondes, ampMin, ampMax, nbEch);

	}

	/**
	 * Emettre : Analyse l'information reçue et décode 
	 * en RZ
	 * @exception InformationNonConforme
	 * 
	 */
	@Override
	public void emettre() throws InformationNonConforme {

		int i = 1, j = 0;
		float sum = 0.0f;
		int nbBits = informationRecue.nbElements() / nbEch;
		Boolean[] tabBool = new Boolean[nbBits];
		

		for (i = 0; i < nbBits ; i++) {
			for (j = 0; j < nbEch; j++) {
				sum += informationRecue.iemeElement((i*nbEch)+j);
			}

			if (( 8 *sum / nbEch) > ((ampMax + ampMin) / 2)) {
				tabBool[i] = true;
			} else {
				tabBool[i] = false;
			}
			sum = 0;
		}
		
		Information<Boolean> informationBool = new Information<Boolean>(tabBool);
		informationEmise = informationBool;

		if (utilisationSondes) {
			SondeLogique sl = new SondeLogique("sl2");
			sl.recevoir(informationEmise);
		}

		for (DestinationInterface<Boolean> dest : destinationsConnectees)
			dest.recevoir(informationEmise);
	}
}
