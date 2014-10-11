package programme;

/**
 * Classe permettant de simuler un transmetteur parfait (le signal qu'il renvoit
 * n'est pas dégradé)
 * 
 * @author Romain & Nicolas
 */

public class TransmetteurParfait extends Transmetteur<Boolean, Boolean> {

	boolean utilisationSondes; // Utilise-t-on une sonde ?

	protected Information<Boolean> informationRecue; // Information reçue par le
														// transmetteur

	protected Information<Boolean> informationEmise; // Information transmise
														// par le transmetteur

	/**
	 * @param information
	 *            de type Information<Boolean> Reçoit l'information et fait
	 *            appel à la méthode emettre() pour la retransmettre
	 */
	public void recevoir(Information<Boolean> information)
			throws InformationNonConforme {
		informationRecue = information;
		emettre();

	}

	/**
	 * @param utilisationSondes
	 *            Constructeur de TransmetteurParfait
	 */
	public TransmetteurParfait(boolean utilisationSondes) {
		this.utilisationSondes = utilisationSondes;
	}

	/**
	 * @throws InformationNonConforme
	 *             Retransmet l'infomation reçue vers le récepteur
	 */
	public void emettre() throws InformationNonConforme {
		informationEmise = informationRecue; // car transmetteur parfait

		if (this.utilisationSondes) {
			SondeLogique sl2 = new SondeLogique(
					"Sortie transmetteur parfait numérique");
			sl2.recevoir(informationEmise);
		}
		
		for (DestinationInterface<Boolean> dest : destinationsConnectees)
			dest.recevoir(informationEmise);

	}
}
