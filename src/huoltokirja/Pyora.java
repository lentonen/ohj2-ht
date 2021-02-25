/**
 * 
 */
package huoltokirja;

import java.io.OutputStream;
import java.io.PrintStream;
import static huoltokirja.Apulaskut.*; //MITÄ STATIC TÄSSÄ TARKOITTAA?

/**
 * Pyörä, joka huolehtii esimerkiksi tunnusNro:staan.
 * @author Henri
 * @version Feb 11, 2021
 *
 */
public class Pyora {
    private int tunnusNro;
    private String nimi     = "";
    private String merkki   = "";
    private String malli    = "";
    private int vuosimalli  = 0 ;
    private String runkoNro = "";
    
    private static int seuraavaNro = 1;  // static = "tämä on olemassa, vaikka olioita ei olisi luotukaan."
    
    
    /**
     * Palauttaa pyörän nimen
     * @return pyörän nimi
     * @example
     * <pre name="test">
     * Pyora pyora = new Pyora();
     * pyora.getNimi() === "";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * Tulostetaan pyörän tiedotparametrina tuotuun tietovirtaan
     * @param os tietovirta johon tiedot tulostetaan
     */
    public void tulosta(OutputStream os) {
        PrintStream out = new PrintStream(os);    // OutputStream käytössä tulevaisuutta varten. Printstreamilla mm. prinln-metodi. 
        out.println("ID: "+tunnusNro + "\n"       // OutputStreamin hankala käyttö ohitetaan luomalla printstream ja käyttämällä sitä tulostamiseen.
                + "Nimi: "+nimi + "\n" 
                +"Merkki: "+merkki + "\n" 
                +"Malli: "+malli + "\n"
                +"vuosimalli: "+vuosimalli + "\n"
                +"Runkonumero: "+runkoNro + "\n");
    }
       
    
    /**
     * Antaa pyörälle seuraavan vapaana olevan tunnusNro:n.
     * @return Vapaana oleva tunnusNro.
     * @example
     * <pre name="test">
     *  Pyora pyora = new Pyora();
     *  pyora.rekisteroi();
     *  pyora.getTunnusNro() === 1;
     *  Pyora pyora2 = new Pyora();
     *  pyora2.rekisteroi();
     *  pyora2.getTunnusNro() === 2;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    
    /**
     * Palauttaa pyörän tunnusnumeron.
     * @return tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * Arpoo mallin vuoksi dataan pyörän hieman eri nimellä ja runkonumerolla
     */
    public void arvoPyora() {
        nimi        = "Fuji Rakan" + rand(1,9999);
        merkki      = "Fuji";
        malli       = "Rakan";
        vuosimalli  = rand(1990, 2021);
        runkoNro    = "AVK4DFG" + rand(1,9999);
    }
    

    /**
     * Pääohjelma Pyora-olion testaamiseen.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Pyora pyora = new Pyora();
        pyora.arvoPyora();
        pyora.rekisteroi();
        pyora.tulosta(System.out);
        
        Pyora pyora2 = new Pyora();
        pyora2.arvoPyora();
        pyora2.rekisteroi();
        pyora2.tulosta(System.out);
    }

}
