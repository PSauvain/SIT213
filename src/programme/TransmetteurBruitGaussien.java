package programme;

import java.util.Random;

public class TransmetteurBruitGaussien extends Transmetteur<Float, Float> {

	private boolean utilisationSondes;

	private float snr;

	private int nbEch;

	@Override
	public void recevoir(Information<Float> information)
			throws InformationNonConforme {
		informationRecue = information;
		emettre();
	}

	/**
	 * @param utilisationSondes
	 *            Constructeur de TransmetteurParfait
	 */
	public TransmetteurBruitGaussien(boolean utilisationSondes, float snr,
			int nbEch) {
		this.utilisationSondes = utilisationSondes;
		this.snr = snr;
		this.nbEch = nbEch;
	}

	@Override
	public void emettre() throws InformationNonConforme {
		double bruit;
		double puissanceBruit = 0; // = puissance de bruit
		float puissanceSignal = 0.0f; // Ps= (1/N) Sum(Sn²) de n=0 à N-1
		float a1, a2; // variables aléatoires uniformément distribuées dans
						// [0,1[
		float sum = 0;
		Float[] tabFloat = new Float[informationRecue.nbElements()];

		int nbBits = informationRecue.nbElements() / nbEch;

		Random rand1 = new Random();
		Random rand2 = new Random();

		for (int i = 0; i < nbBits; i++) {
			sum = 0.0f;
			for (int j = i * nbEch; j < (i + 1) * nbEch; j++) {
				sum += informationRecue.iemeElement(j)
						* informationRecue.iemeElement(j);
			}
			puissanceSignal = (1.0f / (float) nbEch) * sum;

			puissanceBruit = Math.pow(10,
					(Math.log10(puissanceSignal) - (snr / 10.0f)) / 10.0f);

			for (int j = i * nbEch; j < (i + 1) * nbEch; j++) {
				a1 = rand1.nextFloat();
				a2 = rand2.nextFloat();
				bruit = puissanceBruit + Math.sqrt(-2 * Math.log(1 - a1))
						+ Math.cos(2 * Math.PI * a2);
				tabFloat[j] = (float) (informationRecue.iemeElement(j) + bruit);
				// System.out.println(tabFloat[j]);
			}
		}

		Information<Float> information = new Information<Float>(tabFloat);
		informationEmise = information;

		if (this.utilisationSondes) {
			SondeAnalogique sa = new SondeAnalogique(
					"Sortie transmetteur Bruité");
			sa.recevoir(informationEmise);
		}

		for (DestinationInterface<Float> dest : destinationsConnectees)
			dest.recevoir(informationEmise);
	}

}
