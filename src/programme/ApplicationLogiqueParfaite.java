package programme;

import java.util.regex.Pattern;

/**
 * Classe permettant d'utiliser la chaîne de transmission logique parfaite
 * 
 * @author Romain & Nicolas & Antonin & Antoine
 */

public class ApplicationLogiqueParfaite {

	/**
	 * Programme principale Etape 1
	 * @param chaine
	 * @param sondes
	 * @throws InformationNonConforme
	 */
	public static void programme(String chaine,boolean sondes) throws InformationNonConforme {
		
		boolean aleatoire;
		float teb;
		if (Pattern.matches("\\d{1,6}", chaine)) {
			aleatoire = true;
		} else if (Pattern.matches("\\d{7,}", chaine)) {
			aleatoire = false;
		} else {
			throw new InformationNonConforme(
					"Message passée en paramètre non valide ... Fermeture du programme");
		}

		SourceFixe source1 = null;

		if (!aleatoire)
			source1 = new SourceFixe(chaine, sondes);
		else
			source1 = new SourceFixe(Integer.parseInt(chaine), sondes);

		TransmetteurParfait tp1 = new TransmetteurParfait(sondes);
		source1.connecter(tp1);
		DestinationFinale df1 = new DestinationFinale();
		tp1.connecter(df1);
		source1.emettre();
		teb = Simulateur.calculTauxErreurBinaire(source1, df1);
		if (teb != 0.0f) {
			System.out
					.println("Erreur dans la chaine de transmission/reception avec un TEB de "
							+ teb);
		} else
			System.out
					.println("la chaine de tranmission fonctionne correctement avec un TEB de "
							+ teb);
	}

}
