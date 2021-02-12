/**
 * 
 */
package huoltokirja;

/**
 * @author Henri
 * @version Feb 11, 2021
 *
 */
public class ApuException extends Exception {
    private static final long serialVersionUID = 1L;
      
      
   /**
    * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
    * käytettävä viesti
    * @param viesti Poikkeuksen viesti
    */
    public ApuException(String viesti) {
              super(viesti);
    }
}
