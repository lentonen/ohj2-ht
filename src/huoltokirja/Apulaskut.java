/**
 * 
 */
package huoltokirja;

/**
 * Luokka huoltokirjassa käytetyille laskuille.
 * @author Henri
 * @version 13.4.2021
 *
 */
public class Apulaskut {

    /**
     * Arvotaan satunnainen kokonaisluku välille [ala,yla]
     * @param ala arvonnan alaraja
     * @param yla arvonnan yläraja
     * @return satunnainen luku väliltä [ala,yla]
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    }
    
    
    /**
     * Tutkitaan onko jono sama kuin maski jossa * on jokeri.
     * Maski saa sisältää alussa ja lopussa turhia välilyöntejä
     * @param jono tutkittava jono
     * @param maski jono joka sisältää *-merkkejä
     * @return true jos samat maskin mielessä
     * 
     * @example
     * <pre name="test">
     * onkoSamat("Matti Nykänen","    matti* ") === true;
     * onkoSamat("Matti","Matti")               === true;           
     * onkoSamat("Matti","matti")               === true;        
     * onkoSamat("Matti","Katti")               === false;
     * </pre>
     */
    public static boolean onkoSamat(final String jono, final String maski) {
        String maski2 = maski.trim().toUpperCase();
        String jono2 = jono.toUpperCase();
        maski2 = maski2.replaceAll("\\.", "\\\\.");
        maski2 = maski2.replaceAll("\\*", ".*");
        return jono2.matches(maski2);
    }
}
