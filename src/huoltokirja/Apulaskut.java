/**
 * 
 */
package huoltokirja;

/**
 * Luokka huoltokirjassa käytetyille laskuille.
 * @author Henri
 * @version 25.2.2021
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
}
