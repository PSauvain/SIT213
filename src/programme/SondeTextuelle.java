package programme;
/** 
 * Classe réalisant l'affichage (textuel) d'information composée d'éléments de type T
 * @author prou
 */
    public class SondeTextuelle <T> extends Sonde <T> {
   
       public SondeTextuelle(String nom) {
         super(nom);
      }
   
       public void recevoir (Information <T> information) { 		 		 	
         informationRecue = information;
         System.out.println(nom + " : " + information);
      }
   	 
   	
   
   
   }