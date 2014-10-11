package programme;

/**
 * Classe Récepteur NRZT. Elle récupère le signal (Information Float)
 * qui suit une modulation NRZT et renvoie le résultat en Information
 *  Boolean
 * @author R. Annereau, N. Buzy-Debat, A. Courtois, A. Maréchal
 * 
 */

public class RecepteurNRZT extends Recepteur {
	/**
	 * Constructeur
	 * @param utilisationSondes
	 * @param ampMin
	 * @param ampMax
	 * @param nbEch
	 */
	public RecepteurNRZT(boolean utilisationSondes, float ampMin, float ampMax,
			int nbEch) {
		super(utilisationSondes, ampMin, ampMax, nbEch);

	}

	/**
	 * Emettre : Analyse l'information reçue et décode 
	 * en NRZ
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
			if ((sum / nbEch) > ((ampMax + ampMin) / 2)) {
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
