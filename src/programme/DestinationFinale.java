package programme;
/**
 * Classe simulant la destination finale de la chaîne de transmission
 * 
 * @author Romain & Nicolas
 */

public class DestinationFinale extends Destination<Boolean> {

	/**
	 * @param information
	 *            Une information de type Boolean
	 * @throws InformationNonConforme
	 *             Réceptionne l'information reçue
	 */
	public void recevoir(Information<Boolean> information)
			throws InformationNonConforme {
		informationRecue = information;
	}
}
