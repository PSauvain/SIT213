package programme;
/**
 * Classe simulant un émetteur RZ
 * 
 * @author Antonin & Antoine & Romain & Nicolas
 */
 
 
public class EmetteurRZ extends Emetteur {

	/**
	 * @param utilisationSondes Utilisation ou non de sonde
	 * @param ampmin Amplitude minimale
	 * @param ampmax Amplitude maximale
	 * @param nbEch Nombre d'échantillons
	 */
	public EmetteurRZ(boolean utilisationSondes, float ampMin, float ampMax,
			int nbEch) {
		super(utilisationSondes, ampMin, ampMax, nbEch);
	}

	/**
	 * @throws InformationNonConforme
	 * Emission de l'information
	 */

	@Override
	public void emettre() throws InformationNonConforme {
		Float[] tabFloat=new Float [informationRecue.nbElements()*nbEch];
		int tpsBordsImpulsion=nbEch/3;
		double m=0;

		double sigma=nbEch/18;
		
		for(int i=0;i<informationRecue.nbElements();i++){
			
			//centrage de la gaussienne au milieu du temps bit
			m=0.5*(i*nbEch+i*nbEch+3*tpsBordsImpulsion); 			
			if(informationRecue.iemeElement(i)){
				
				//amplitude min sur le 1er tiers du temps bit
				for(int j=i*nbEch;j<i*nbEch+tpsBordsImpulsion;j++){ 					tabFloat[j]=0.0f;
				}
				
				//Impulsion gaussienne
				//a * exp( -(x-m)²/(2*sigma²) )
				
				for(int j=i*nbEch;j<(i+1)*nbEch;j++){
					tabFloat[j]=(float) (ampMax*Math.exp( -( (j-m)*(j-m) )/ (2*sigma*sigma) ));
					//System.out.println(tabFloat[j]);
				}
				
				for(int j=i*nbEch+2*tpsBordsImpulsion;j<i*nbEch+3*tpsBordsImpulsion;j++){ //amplitude min sur le dernier tiers du temps bit
					tabFloat[j]=0.0f;
				}
			}
			else { 
				
				//amplitude min sur le 1er tiers du temps bit
				for(int j=i*nbEch;j<i*nbEch+tpsBordsImpulsion;j++){
					tabFloat[j]=0.0f;
				}
				
				//Impulsion gaussienne
				//a * exp( -(x-m)²/(2*sigma²) )
				
				for(int j=i*nbEch;j<(i+1)*nbEch;j++)
					tabFloat[j]=(float) (ampMin*Math.exp( -( (j-m)*(j-m) )/ (2*sigma*sigma) ));
				
				//amplitude min sur le dernier tiers du temps bit
				for(int j=i*nbEch+2*tpsBordsImpulsion;j<i*nbEch+3*tpsBordsImpulsion;j++){
					tabFloat[j]=0.0f;
				}
			}
	}
		
		Information<Float> sigAnalog = new Information<Float>(tabFloat);
		informationEmise = sigAnalog;
		
		if(utilisationSondes) {
			SondeAnalogique sa1 = new SondeAnalogique("sa1");
			sa1.recevoir(informationEmise);
		}
		
		for (DestinationInterface<Float> dest : destinationsConnectees)
			dest.recevoir(informationEmise);

	}

}