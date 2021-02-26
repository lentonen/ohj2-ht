/**
 * 
 */
package huoltokirja;

import static huoltokirja.Apulaskut.rand;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author Henri
 * @version 25.2.2021
 *
 */
public class Huolto {
    private int tunnusNro;
    private int pyoraNro;
    private String nimi             = "";
    private int ajotunnit;
    private String toimenpiteet     = "";
    
    private static int seuraavaNro = 1;  // static = "tämä on olemassa, vaikka olioita ei olisi luotukaan." TODO:tarvitaanko missään?
    
    
    /**
     * Oletusmuodostaja
     */
    public Huolto() {
        //
    }
    
    
    /**
     * muodostaja jolle viedään pyörän numero.
     * @param pyoraID pyörä, johon huolto halutaan liittää
     */
    public Huolto(int pyoraID) {
        this.pyoraNro = pyoraID;
    }
    
    
    /**
     * Palauttaa huollon nimen
     * @return huollon nimi
     * @example
     * <pre name="test">
     * Huolto huolto = new Huolto();
     * huolto.getNimi() === "";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * Saantimetodi ajotunneille
     * @return huollon ajotunnit
     * @example
     * <pre name="test">
     * Huolto huolto = new Huolto();
     * huolto.getAjotunnit() === 0;
     * </pre>
     */
    public int getAjotunnit() {
        return ajotunnit;
    }
    
    
    /**
     * Tulostetaan huollon tiedot parametrina tuotuun tietovirtaan
     * @param os tietovirta johon tiedot tulostetaan
     */
    public void tulosta(OutputStream os) {
        PrintStream out = new PrintStream(os);              // OutputStream käytössä tulevaisuutta varten. Printstreamilla mm. prinln-metodi. 
        out.println("HuoltoID: "+tunnusNro + "\n"           // OutputStreamin hankala käyttö ohitetaan luomalla printstream ja käyttämällä sitä tulostamiseen.
                + "PyöräID: "+pyoraNro + "\n"
                + "Nimi: "+nimi + "\n" 
                +"Ajotunnit: "+ajotunnit + "\n" 
                +"Malli: "+toimenpiteet + "\n");
    }
       
    
    /**
     * Antaa huollolle seuraavan vapaana olevan tunnusNro:n.
     * @return Vapaana oleva tunnusNro.
     * @example
     * <pre name="test">
     *  Huolto huolto1 = new Huolto();
     *  huolto1.rekisteroi();
     *  huolto.getTunnusNro() === 1;
     *  Huolto huolto2 = new Huolto();
     *  huolto2.rekisteroi();
     *  huolto2.getTunnusNro() === 2;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    
    /**
     * Palauttaa huollon tunnusnumeron.
     * @return tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * Palauttaa huollon pyoraNro.
     * @return pyörän numero
     */
    public int getPyoraNro() {
        return pyoraNro;
    }
    
    /**
     * Arpoo mallin vuoksi dataan huoltoja eri tiedoilla
     */
    public void arvoHuolto() {
        nimi         = "Huolto " + rand(1,9999);
        ajotunnit    = rand(1,500);
        toimenpiteet = "Vaihdettiin osanro " +rand(1,9999) + " ja putsattiin osa " +rand(1,200);
    }
    

    /**
     * Pääohjelma Huolto-olion testaamiseen.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Huolto huolto = new Huolto(1);
        huolto.arvoHuolto();
        huolto.rekisteroi();
        huolto.tulosta(System.out);
        
        Huolto huolto2 = new Huolto(5);
        huolto2.arvoHuolto();
        huolto2.rekisteroi();
        huolto2.tulosta(System.out);
    }
}
