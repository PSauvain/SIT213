package programme;

/**
 * Classe simulant un émetteur NRZT
 * 
 * @author Antonin & Antoine & Romain & Nicolas
 */

public class EmetteurNRZT extends Emetteur {

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

	public EmetteurNRZT(boolean utilisationSondes, float ampMin, float ampMax,
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

		int tpsMonteeOuDescente = nbEch / 3;
		float deltaMaxMin = ampMax - ampMin;
		float decoupage = deltaMaxMin / (float) tpsMonteeOuDescente;
		//System.out.println(decoupage);
		float pas = 0.0f;

		// les 1/3 de montée ou de descente on monte progressivement en
		// incrémentant de "découpage"
		// puis après on remplit les 2/3 restants comme pour le NRZ classique
		// Ceci n'est valable QUE si le dernier bit est différent du bit
		// courant, sinon on se comporte comme du NRZ classique
		// Il faut aussi savoir si on fait une montée (de 0 à 1) ou descente (de
		// 1 à 0)

		for (int i = 0; i < informationRecue.nbElements(); i++) {
			if (i != 0) {
				if (informationRecue.iemeElement(i) != informationRecue
						.iemeElement(i - 1)) {
					if (informationRecue.iemeElement(i)) {
						pas = ampMin;
						// montée
						for (int j = i * nbEch; j < i * nbEch
								+ tpsMonteeOuDescente; j++) {
							tabFloat[j] = pas;
							pas = pas + decoupage;
							//System.out.println(pas);
						}
						for (int j = i * nbEch + tpsMonteeOuDescente; j < (i + 1)
								* nbEch; j++)
							tabFloat[j] = ampMax;

					} else { // descente
						pas = ampMax;
						for (int j = i * nbEch; j < i * nbEch
								+ tpsMonteeOuDescente; j++) {
							tabFloat[j] = pas;
							pas = pas - decoupage;
							//System.out.println(pas);
						}
						for (int j = i * nbEch + tpsMonteeOuDescente; j < (i + 1)
								* nbEch; j++)
							tabFloat[j] = ampMin;
					}
				} else {
					if (informationRecue.iemeElement(i)) {
						for (int j = i * nbEch; j < (i + 1) * nbEch; j++)
							tabFloat[j] = ampMax;
					} else {
						for (int j = i * nbEch; j < (i + 1) * nbEch; j++)
							tabFloat[j] = ampMin;
					}
				}
			} else {
				if (informationRecue.iemeElement(i)) {
					for (int j = i * nbEch; j < (i + 1) * nbEch; j++)
						tabFloat[j] = ampMax;
				} else {
					for (int j = i * nbEch; j < (i + 1) * nbEch; j++)
						tabFloat[j] = ampMin;
				}
			}
		}

		Information<Float> sigAnalog = new Information<Float>(tabFloat);
		informationEmise = sigAnalog;

		if (utilisationSondes) {
			SondeAnalogique sa1 = new SondeAnalogique("sa2");
			sa1.recevoir(informationEmise);
		}
		
		for (DestinationInterface<Float> dest : destinationsConnectees)
			dest.recevoir(informationEmise);
	}

}