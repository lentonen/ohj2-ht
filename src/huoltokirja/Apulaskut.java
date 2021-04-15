/**
 * 
 */
package huoltokirja;

import fi.jyu.mit.ohj2.Mjonot;

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
    
    
    /**
     * Tarkistaa onko syötetty pvm laiton
     * @param pvm Päivämäärä merkkijonona
     * @return true jos päivä on laiton, false jos ei
     */
     public static boolean onkoLaitonPvm(String pvm) {
         try {
             StringBuilder sb = new StringBuilder(pvm);
             int pp = Integer.parseInt(Mjonot.erota(sb, '.'));
             int kk = Integer.parseInt(Mjonot.erota(sb, '.'));
             int vv = Integer.parseInt(sb.toString());
             
             // Taulukko karkausvuoden huomioimista varten
             int[][] kkPituudet ={ 
                     // 1  2   3   4   5   6   7   8   9   10  11  12    0-rivi normaalivuosi, 1-rivi karkausvuosi
                     { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
                     { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }};
             
             if (kk > 12 || pp > kkPituudet[karkausvuosi(vv)][kk-1] || pp < 1 || kk < 1 || vv < 2020 || vv > 2050) return true;
             return false; 
         }
         catch (NumberFormatException e) {
             return true;
        }
        catch (ArrayIndexOutOfBoundsException e) {
             return true;
        }
     }
     
     
     
     /**
      * Palautetaan tieto, onko tutkittava vuosi karkausvuosi vai ei
      * @param vv tutkittava vuosi
      * @return 1 jos on karkausvuosi ja 0 jos ei ole
      * @example
      * <pre name="test">
      *   karkausvuosi(1900) === 0
      *   karkausvuosi(1900) === 0
      *   karkausvuosi(1901) === 0
      *   karkausvuosi(1996) === 1
      *   karkausvuosi(2000) === 1
      *   karkausvuosi(2001) === 0
      *   karkausvuosi(2004) === 1
      * </pre>
      */
     public static int karkausvuosi(int vv) {
         if ( vv % 400 == 0 ) return 1;
         if ( vv % 100 == 0 ) return 0;
         if ( vv % 4 == 0 ) return 1;
         return 0;
     }
}
