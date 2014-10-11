package programme;
import java.util.*;

/** 
 * Classe Abstraite d'un composant source d'informations dont les éléments sont de type T 
 * @author prou
 */
    public  abstract class Source <T> implements  SourceInterface <T> {
   
   
   /** 
   * la liste des composants destination connectés 
   */
      protected LinkedList <DestinationInterface <T>> destinationsConnectees;
   
   /** 
   * l'information générée par la source 
   */
      protected Information <T>  informationGeneree;
   	
   /** 
   * l'information émise par la source 
   */
      protected Information <T>  informationEmise;
   	
   
   /** 
   * un constructeur factorisant les initialisations communes aux réalisations de la classe abstraite Source 
   */
       public Source () {
         destinationsConnectees = new LinkedList <DestinationInterface <T>> ();
         informationGeneree = null;
         informationEmise = null;
      }
     
   /**
    * pour obtenir la dernière information émise par la source
	 * @return une information   
    */
       public Information <T>  getInformationEmise() {
         return this.informationEmise;
      }
   
   
   /**
    * pour connecter une  destination à la source 
    * @param destination  la destination à connecter
    */
       public void connecter (DestinationInterface <T> destination) {
         destinationsConnectees.add(destination); 
      }

   
   /**
    * pour déconnecter une  destination de la source 
    * @param destination  la destination à connecter
    */
       public void deconnecter (DestinationInterface <T> destination) {
         destinationsConnectees.remove(destination); 
      }
   
   /**
    * pour émettre l'information  contenue dans la source  
    */
       public  abstract void emettre() throws InformationNonConforme;
     
   }