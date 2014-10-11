package programme;
/**
 * Classe abstraite Recepteur
 * 
 * @author Antonin & Antoine & Romain & Nicolas
 * 
 */
public abstract class Recepteur extends Transmetteur<Float, Boolean> {

	boolean utilisationSondes; // Utilise-t-on une sonde ?

	float ampMin;

	float ampMax;

	int nbEch;

	/**
	 * Constructeur
	 * @param utilisationSondes
	 * @param ampMin
	 * @param ampMax
	 * @param nbEch
	 */
	public Recepteur(boolean utilisationSondes, float ampMin, float ampMax,
			int nbEch) {
		this.utilisationSondes = utilisationSondes;
		this.ampMax = ampMax;
		this.ampMin = ampMin;
		this.nbEch = nbEch;

	}

	/**
	 * Recevoir : Information passée en paramètre  = info reçue
	 * @param information
	 */
	@Override
	public void recevoir(Information<Float> information)
			throws InformationNonConforme {
		informationRecue = information;
		emettre();
	}

}