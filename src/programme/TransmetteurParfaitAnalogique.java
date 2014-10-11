package programme;

/**
 * Classe permettant de simuler un transmetteur parfait (le signal qu'il renvoit
 * n'est pas dégradé)
 * 
 * Analogique
 * 
 * @author amarecha
 */

public class TransmetteurParfaitAnalogique extends Transmetteur<Float, Float> {

	boolean utilisationSondes; // Utilise-t-on une sonde ?

	protected Information<Float> informationRecue; // Information reçue par le
													// transmetteur

	protected Information<Float> informationEmise; // Information transmise
													// par le transmetteur

	/**
	 * @param information
	 *            de type Information<Boolean> Reçoit l'information et fait
	 *            appel à la méthode emettre() pour la retransmettre
	 */
	public void recevoir(Information<Float> information)
			throws InformationNonConforme {
		informationRecue = information;
		emettre();

	}

	/**
	 * @param utilisationSondes
	 *            Constructeur de TransmetteurParfait
	 */
	public TransmetteurParfaitAnalogique(boolean utilisationSondes) {
		this.utilisationSondes = utilisationSondes;
	}

	/**
	 * @throws InformationNonConforme
	 *             Retransmet l'infomation reçue vers le récepteur
	 */
	public void emettre() throws InformationNonConforme {
		informationEmise = informationRecue; // car transmetteur parfait

		if (this.utilisationSondes) {
			SondeAnalogique sa = new SondeAnalogique(
					"Sortie transmetteur Parfait analogique");
			sa.recevoir(informationEmise);
		}
		
		for (DestinationInterface<Float> dest : destinationsConnectees)
			dest.recevoir(informationEmise);

	}
}
