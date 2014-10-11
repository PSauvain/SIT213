package programme;

import java.util.regex.Pattern;

/**
 * Classe permettant d'utiliser la chaîne de transmission au cours des etapes du
 * fil rouge.
 * 
 * @author Romain Annereau
 * @author Nicolas Buzy-Debat
 * @author Antonin Marechal
 * @author Antoine Courtois
 */

public class Simulateur {

	/**
	 * Programme Main Simulateur
	 * @param args
	 * @throws InformationNonConforme
	 */
	public static void main(String[] args) throws InformationNonConforme {

		// Valeurs connues
		int etape = 1;

		int nbEch = 10;

		boolean sondes = false;

		// NRZ : 0, NRZT : 1, RZ :2
		String forme = "RZ";

		float amplitudeMax = 1.0f;
		float amplitudeMin = 0.0f;

		// Valeurs inconnues
		int nombreAEmettre=50;
		boolean aleatoire = true;
		String chaineAEmettre="10001101001";

		// TODO SNR et décalage temporel

		if (args.length == 0) {
			System.out.println("Valeurs par défaut");

		} else {

			// GESTION des arguments
			for (int i = 0; i < args.length; i++) {
				// si un des paramètres est -etape
				if (args[i].equals("-etape")) {

					// et si l'argument suivant est un chiffre
					if (Pattern.matches("\\d", args[i + 1])) {

						// en fonction de l'argument on passe a l'étape i
						switch (args[i + 1]) {
						case "1":
							etape = 1;
							break;
						case "2":
							etape = 2;
							break;
						default:
							System.out
									.println("L'étape n'est voulue n'est pas encore implémentée, ou non disponible pour le moment");
							System.exit(1);
						}
					} else {
						throw new InformationNonConforme(
								"Message passée en paramètre non valide ... Fermeture du programme");
					}
				} else {
					// MESSAGE
					if (args[i].equals("-mess")) {
						if (Pattern.matches("\\d{1,6}", args[i + 1])) {
							nombreAEmettre = Integer.parseInt(args[i + 1]);
							aleatoire = true;
						} else if (Pattern.matches("\\d{7,}", args[i + 1])) {
							chaineAEmettre = args[i + 1];
							aleatoire = false;
						} else {
							throw new InformationNonConforme(
									"Message passée en paramètre non valide ... Fermeture du programme");
						}
					} else {
						// NB ECHANTILLONS
						if (args[i].equalsIgnoreCase("-nbEch")) {
							if (Pattern.matches("\\d{1,}", args[i + 1])) {
								if (Integer.parseInt(args[i + 1]) > 0) {
									nbEch = Integer.parseInt(args[i + 1]);
								}
							} else {
								throw new InformationNonConforme(
										"nbEchantillons passés en paramètre non valide ... Fermeture du programme");
							}
						} else {
							// AMPLITUDE
							if (args[i].equals("-ampl")) {
								if ((Pattern.matches("-?\\d{1,}\\.?\\d?",
										args[i + 1]) && Pattern.matches(
										"-?\\d{1,}\\.?\\d?", args[i + 2]))) {
									if (Float.parseFloat(args[i + 1]) < Float
											.parseFloat(args[i + 2])) {
										amplitudeMin = Float
												.parseFloat(args[i + 1]);
										amplitudeMax = Float
												.parseFloat(args[i + 2]);
									}
								} else {
									throw new InformationNonConforme(
											"Amplitude passées en paramètre non valide ... Fermeture du programme");
								}
							} else {
								// SONDES
								if (args[i].equals("-s")) {
									sondes = true;
								} else {
									// FORME (NRZ, RZ, NRZT)
									if (args[i].equals("-form")) {
										if (Pattern.matches("[A-Z]+", args[i + 1])) {
											if (args[i + 1].equals("NRZ")) {
												forme = "NRZ";
											} else if (args[i + 1].equals("NRZT")) {
												forme = "NRZT";

											} else if (args[i + 1].equals("RZ")) {
												forme = "RZ";

											} else {
												throw new InformationNonConforme(
														"ERREUR FORME : NRZ OU RZ OU NRZT");
											}
										} else {
											throw new InformationNonConforme(
													"ERREUR FORME : NRZ OU RZ OU NRZT");
										}
									}
									else {
										if(args[i].equals("-help")){
											System.out.println("\t-etape e\n"+
"précise l’étape, donc la chaîne de transmission à "+
"utiliser. "+
"Le paramètre e "+
"peut prendre les valeurs 1 à 7. "+
"Par défaut le simulateur doit utiliser la chaîne "+
"de transmission de l’étape 1. "+
"\n\t-mess m\n"+
"précise le message ou la longueur du message à "+
"émettre : "+
"Si m est une suite de 0 et de 1 de longueur au "+
"moins égale à 7, m est le message à émettre. "+
"Si m comporte au plus 6 chiffres décimaux et co "+
"rrespond à la représentation en base 10 d'un entier "+
", "+
"cet entier est la longueur du message q "+
"ue le simulateur doit générer et transmettre. "+
"Par défaut le simulateur doit générer et transme "+
"ttre un message de longueur 100. "+
"\n\t-s\n"+
"utilisation de sondes "+
"Par défaut le simulateur n’utilise pas de sondes "+
"\n\t-form f\n"+
"précise la forme d’onde du signal analogique. "+
"\nLe paramètre f "+
"peut prendre les valeurs suivantes : "+
"NRZ forme d'onde rectangulaire "+
"NRZT forme d'onde trapézoïdale (temps de mont"+
"ée ou de descente à 1/3 du temps bit) "+
"RZ forme d'onde impulsionnelle (amplitude "+
"min sur le premier et dernier tiers du temps bit, "+
"impulsionnelle sur le tiers central ave"+
"c un max au milieu du temps bit égal à l’amplitude "+
"max)"+
"\nPar défaut le simulateur doit utiliser la forme d"+
"’onde RZ pour le signal analogique."+
"\n\t-nbEch ne\n"+
"précise le nombre d’échantillons par bit. "+
"Le paramètre doit être une valeur entière positi"+
"ve."+
"\nPar défaut le simulateur doit utiliser 10 échant"+
"illons par bit."+
"\n\t-ampl min max\n"+
"précise l’amplitude min et max du signal analo"+
"gique. "+
"\nLes paramètres min"+
"et max"+
"doivent être des valeurs flottantes (avec min < ma"+
"x)."+
"Par défaut le simulateur doit utiliser 0.0f com"+
"me min et 1.0f comme max."+
"\n\t-snr s\n"+
"précise la valeur du rapport signal sur bruit ("+
"SNR)."+
")"+
"\nLe paramètre s "+
"doit être une valeur flottante. "+
"Par défaut le simulateur doit utiliser 0.7f com"+
"me SNR."+
"\n\t-ti i dt ar\n"+
"précise le décalage temporel (dt : en nombre d’"+
"échantillons) entre le i ème trajet indirect du s"+
"ignal et"+
"le trajet direct, "+
"précise aussi l’amplitude relative (ar) du sig"+
"nal du i ème trajet indirect par rapport à celle "+
"du signal"+
"du trajet direct. "+
"Les paramètres i"+
", dt "+
"et ar "+
"doivent être respectivement une valeur entière (de "+
"1 à 5), une valeur entière "+
"et une valeur flottante. "+
"Par défaut le simulateur doit utiliser 0 et 0. "+
"0f pour tous les trajets indirects (5 au maximum)");

										}
									}
								}
							}
						}
					}
				}
			}
		}
		/**
		 * Deux types d'étapes
		 * 
		 */
		switch (etape) {
		case (1):
			if (aleatoire) {
				programme.ApplicationLogiqueParfaite.programme(nombreAEmettre+"", sondes);
			} else {
				programme.ApplicationLogiqueParfaite.programme(chaineAEmettre, sondes);
			}
			break;
		case (2):
			if(aleatoire){
				programme.ApplicationTransmissionAnalogiqueParfaite.programme(nombreAEmettre+"", nbEch, amplitudeMin, amplitudeMax, forme, sondes);
			}
			else{
				programme.ApplicationTransmissionAnalogiqueParfaite.programme(chaineAEmettre, nbEch, amplitudeMin, amplitudeMax, forme, sondes);
			}
			break;
		default:
			System.out.println("Etape indisponible");
		}
	}

	/**
	 * @param source
	 *            Source à utiliser
	 * @param destination
	 *            Destination à utiliser
	 * @return Le taux d'erreur binaire Calcule le Taux d'Erreur Binaire de
	 *         notre chaîne de transmission
	 */
	public static float calculTauxErreurBinaire(Source<Boolean> source,
			Destination<Boolean> destination) {
		int nbErreur = 0;
		Information<Boolean> informationEmise = source.getInformationEmise();
		Information<Boolean> informationRecue = destination
				.getInformationRecue();

		for (int i = 1; i < informationRecue.nbElements(); i++) {
			if (!informationRecue.iemeElement(i).equals(
					informationEmise.iemeElement(i))) {
				nbErreur++;
			}
		}
		float teb = ((float) nbErreur / (float) informationEmise.nbElements());
		return teb;
	}
}