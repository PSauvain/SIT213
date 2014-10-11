package programme;

/**
 * Classe abstraite simulant un émetteur
 * 
 * @author Antonin & Antoine & Romain & Nicolas
 */

public abstract class Emetteur extends Transmetteur<Boolean, Float> {

	boolean utilisationSondes; // Utilise-t-on une sonde ?

	float ampMin; // Amplitude minimale

	float ampMax; // Amplitude maximale

	int nbEch; // Nombre d'échantillons

	/**
	 * @param utilisationSondes
	 *            Utilisation ou non de sonde
	 * @param ampmin
	 *            Amplitude minimale
	 * @param ampmax
	 *            Amplitude maximale
	 * @param nbEch
	 *            Nombre d'échantillons Constructeur de la classe Emetteur
	 */

	public Emetteur(boolean utilisationSondes, float ampMin, float ampMax,
			int nbEch) {
		this.utilisationSondes = utilisationSondes;
		this.ampMax = ampMax;
		this.ampMin = ampMin;
		this.nbEch = nbEch;

	}

	/**
	 * @param information
	 *            Information reçue par l'émetteur de la part de la source
	 * @throws InformationNonConforme
	 *             Reçoit l'information reçue par l'émetteur de la part de la
	 *             source et fait appel à la méthode d'émission
	 * 
	 */

	@Override
	public void recevoir(Information<Boolean> information)
			throws InformationNonConforme {
		informationRecue = information;
		emettre();
	}

}