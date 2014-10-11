package programme;

/**
 * Classe simulant un émetteur NRZ
 * 
 * @author Antonin & Antoine & Romain & Nicolas
 */

public class EmetteurNRZ extends Emetteur {

	/**
	 * @param utilisationSondes
	 *            Utilisation ou non de sonde
	 * @param ampmin
	 *            Amplitude minimale
	 * @param ampmax
	 *            Amplitude maximale
	 * @param nbEch
	 *            Nombre d'échantillons
	 */

	public EmetteurNRZ(boolean utilisationSondes, float ampMin, float ampMax,
			int nbEch) {
		super(utilisationSondes, ampMin, ampMax, nbEch);
	}

	/**
	 * @throws InformationNonConforme
	 *             Emission de l'information
	 */

	@Override
	public void emettre() throws InformationNonConforme {

		Float[] tabFloat = new Float[informationRecue.nbElements() * nbEch];
		for (int i = 0; i < informationRecue.nbElements(); i++) {
			if (informationRecue.iemeElement(i)) {
				for (int j = i * nbEch; j < (i + 1) * nbEch; j++)
					tabFloat[j] = ampMax;
			} else {
				for (int j = i * nbEch; j < (i + 1) * nbEch; j++)
					tabFloat[j] = ampMin;
			}
		}

		Information<Float> sigAnalog = new Information<Float>(tabFloat);
		informationEmise = sigAnalog;

		if (utilisationSondes) {
			SondeAnalogique sa1 = new SondeAnalogique("Sortie Emetteur NRZ");
			sa1.recevoir(informationEmise);
		}
		
		for (DestinationInterface<Float> dest : destinationsConnectees)
			dest.recevoir(informationEmise);
	}

}