/**
 * 
 */
package huoltokirja;

/**
 * Luokka omalle poikkeukselle.
 * @author Henri
 * @version 25.2.2021
 *
 */
public class ApuException extends Exception {
    private static final long serialVersionUID = 1L;        // Versionumero, apuna jos ohjelmia jaetaan verkon yli ja toisella hlöllä eri versiot. 
                                                            //Tällöin saadaan ilmoitus erilaisita versionumeroista.
      
   /**
    * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
    * käytettävä viesti
    * @param viesti Poikkeuksen viesti
    */
    public ApuException(String viesti) {
              super(viesti);                                // Viedään oma viesti alkuperäiselle muodostajalle.
    }
}
