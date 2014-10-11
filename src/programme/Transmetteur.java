package programme;
import java.util.*;

/** 
 * Classe Abstraite d'un composant transmetteur d'informations dont les éléments sont de type R en entrée et de type E en sortie;
 * l'entrée du transmetteur implémente l'interface DestinationInterface, 
 * la sortie du transmetteur implémente l'interface SourceInterface
 * @author prou
 */
    public abstract  class Transmetteur <R,E> implements  DestinationInterface <R>, SourceInterface <E> {
   
   
   /** 
   * la liste des composants destination connectés en sortie du transmetteur 
   */
      protected LinkedList <DestinationInterface <E>> destinationsConnectees;
   
   /** 
   * l'information reçue en entrée du transmetteur     */
      protected Information <R>  informationRecue;
		
   /** 
   * l'information émise en sortie du transmetteur  
   */		
      protected Information <E>  informationEmise;

   
   /** 
   * un constructeur factorisant les initialisations communes aux réalisations de la classe abstraite Transmetteur 
   */
       public Transmetteur() {
         destinationsConnectees = new LinkedList <DestinationInterface <E>> ();
         informationRecue = null;
         informationEmise = null;
      }

     
   	
   /**
    * pour obtenir la dernière information reçue en entrée du transmetteur
    * @return une information   
    */
       public Information <R>  getInformationRecue() {
         return this.informationRecue;
      }

   /**
    * pour obtenir la dernière information émise en sortie du transmetteur
	 * @return une information   
    */
       public Information <E>  getInformationEmise() {
         return this.informationEmise;
      }
 

   /**
    * pour connecter une  destination à la sortie du transmetteur 
    * @param transmetteurParfait  la destination à connecter
	 */
       public void connecter (DestinationInterface <E> destination) {
         destinationsConnectees.add(destination); 
      }

   
   /**
    * pour déconnecter une  destination de la la sortie du transmetteur 
    * @param destination  la destination à connecter
    */
       public void deconnecter (DestinationInterface <E> destination) {
         destinationsConnectees.remove(destination); 
      }

   	    
   /**
    * pour recevoir une information  de la source qui  est connectée à l'entrée du transmetteur; 
	 * Cette méthode devra, en fin d'exécution, appeller la méthode émettre.
    * @param information  l'information  à recevoir
    */
       public  abstract void recevoir(Information <R> information) throws InformationNonConforme;
      
   
    /**
    * pour émettre l'information  contenue dans l'entrée du transmetteur  
    */
      public  abstract void emettre() throws InformationNonConforme;   
   }