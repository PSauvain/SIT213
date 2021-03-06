package programme;

import java.util.regex.*;

/**
 * Classe permettant d'utiliser la chaîne de transmission analogique Bruitée
 * 
 * @author Romain & Nicolas & Antonin & Antoine
 */
public class ApplicationTransmissionAnalogiqueBruitee {

	/**
	 * Programme principal Etape 3
	 * 
	 * @param chaine
	 * @param nbEch
	 * @param amplMin
	 * @param amplMax
	 * @param forme
	 * @param snr
	 * @param sondes
	 * @throws InformationNonConforme
	 */
	public static void programme(String chaine, int nbEch, float amplMin,
			float amplMax, String forme, float snr, boolean sondes)
			throws InformationNonConforme {

		// Forme
		int type;

		// Message aleatoire ?
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

		if (forme.equals("NRZ")) {
			type = 0;
		} else if (forme.equals("NRZT")) {
			type = 1;
		} else {
			type = 2;
		}

		SourceFixe source1 = null;
		EmetteurNRZ eNRZ = null;
		EmetteurNRZT eNRZT = null;
		EmetteurRZ eRZ = null;
		Recepteur recepteur = null;
		TransmetteurBruitGaussien transmetteurBruite = new TransmetteurBruitGaussien(
				sondes, snr, nbEch);
		DestinationFinale destinationFinale = new DestinationFinale();

		System.out.println("Sondes : " + sondes);
		System.out.println("Amplitude Min : " + amplMin);
		System.out.println("Amplitude Max : " + amplMax);
		System.out.println("Nombre échantillon : " + nbEch);
		System.out.println("SNR choisi : " + snr);

		if (!aleatoire) {
			source1 = new SourceFixe(chaine, sondes);
		} else
			source1 = new SourceFixe(Integer.parseInt(chaine), sondes);

		switch (type) {
		case 0:
			System.out.println("Type : NRZ");

			System.out.println(sondes + " " + amplMin + " " + amplMax + " "
					+ nbEch);
			eNRZ = new EmetteurNRZ(sondes, amplMin, amplMax, nbEch);
			source1.connecter(eNRZ);
			eNRZ.connecter(transmetteurBruite);
			recepteur = new RecepteurNRZ(sondes, amplMin, amplMax, nbEch);
			break;
		case 1:
			System.out.println("Type : NRZT");
			eNRZT = new EmetteurNRZT(sondes, amplMin, amplMax, nbEch);
			source1.connecter(eNRZT);
			eNRZT.connecter(transmetteurBruite);
			recepteur = new RecepteurNRZT(sondes, amplMin, amplMax, nbEch);
			break;
		case 2:
			System.out.println("Type : RZ");
			eRZ = new EmetteurRZ(sondes, amplMin, amplMax, nbEch);
			source1.connecter(eRZ);
			eRZ.connecter(transmetteurBruite);
			recepteur = new RecepteurRZ(sondes, amplMin, amplMax, nbEch);
			break;
		default:
			throw new InformationNonConforme(
					"Amplitude passées en paramètre non valide ... Fermeture du programme");

		}

		transmetteurBruite.connecter(recepteur);
		recepteur.connecter(destinationFinale);
		source1.emettre();

		teb = Simulateur.calculTauxErreurBinaire(source1, destinationFinale);

		if (teb != 0.0f) {
			System.out
					.println("Erreur chaine de transmission/reception TEB de "
							+ teb);
		} else

			System.out.println("TEB : chaine de tranmission OK " + teb);

	}
}