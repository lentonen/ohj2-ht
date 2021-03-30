package huoltokirja;

import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;
import static huoltokirja.Apulaskut.*;      // Static = luokkaa voidaan käyttää ilman, että yhtään luokan oliota on luotu. 
                                            //          luokan sisällä olevia funktioita voidaan käyttää ilman luokan nimeä (Esim. Apulaskut.rand(1,2) --> rand(1,2))

/**
 * Pyörä, joka huolehtii esimerkiksi tunnusNro:staan.
 * @author Henri
 * @version 19.3.2021
 */
public class Pyora implements Cloneable{
    private int tunnusNro;                  // Numero joka yksilöi pyörän  
    private String nimi     = "";           // Pyörän nimi                 // kenttä 1
    private String merkki   = "";           // Pyörän merkki               // kenttä 2
    private String malli    = "";           // Pyörän malli                // kenttä 3
    private int vuosimalli  = 0 ;           // Pyörän vuosimalli           // kenttä 4
    private String runkoNro = "";           // Pyörän runkonumero          // kenttä 5
    private static int seuraavaNro = 1;     // Ilmaisee seuraavan vapaana olevan tunnusnumeron, static = "on olemassa, vaikka olioita ei olisi luotu." 
                                            // Static-attribuutti on yhteinen kaikille luokasta luoduille olioille. Eli jokaiselle pyörälle ei alusteta omaa attribuuttia.
    
    
    /**
     * Palauttaa pyörään liittyvien kenttien lukumäärän
     * @return kenttien lukumäärä
     */
    public int getKenttia() {
        return 6;
    }
    
    
    /**
     * Ensimmäisen mielekkään kentän numero
     * @return ensimmäisen kentän indeksi
     */
    public int ekaKentta() {
        return 1;
    }
    
    
    /**
     * Asettaa tunnusnumeron ja varmistaa, että seuraavaNro on 
     * ajantasalla.
     * @param nro asetettava tunnusnumero
     */
    private void setTunnusNro(int nro) {
        tunnusNro = nro;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    
    /**
     * Antaa kentän k nimen
     * @param k monennenko kentän nimi annetaan
     * @return kentän nimi
     */
    public String getKentanNimi(int k) {
        switch (k) {
        case 0: return "Tunnus nro" ;
        case 1: return "Nimi";
        case 2: return "Merkki";
        case 3: return "Malli";
        case 4: return "Vuosimalli";
        case 5: return "Runkonumero";
        default: return "Ei ole olemassa";
        }
    }
    
    
    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k minkä kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String anna(int k) {
        switch (k) {
        case 0: return "" +tunnusNro;
        case 1: return "" +nimi;
        case 2: return "" +merkki;
        case 3: return "" +malli;
        case 4: return "" +vuosimalli;
        case 5: return "" +runkoNro;
        default: return "Ei ole olemassa";
        }
    }
    
    
    /**
     * asettaa kentän k arvoksi parametrina tuodun merkkijonon arvon
     * @param k kenttä jonka arvo asetetaan
     * @param jono josta kentän sisältö luetaan
     * @return null jos asettaminen onnistuu, virheilmoituksen jos asettaminen epäonnistuu
     * @example
     * <pre name="test">
     * Pyora pyora3 = new Pyora();
     * pyora3.aseta(0,"a") === "Tunnusnumero on väärin";
     * pyora3.aseta(0,"3") === null;
     * pyora3.aseta(4,"2008a") === "vuosimalli on väärin";
     * pyora3.aseta(4,"2008") === null;
     * </pre>
     */
    public String aseta(int k, String jono) {
        String mj = jono.trim();
        //StringBuilder sb = new StringBuilder(mj);
        switch (k) {
        case 0:
            try {
            setTunnusNro(Integer.parseInt(mj));
            } catch (NumberFormatException e) {
                return "Tunnusnumero on väärin";
            }
            return null;
        case 1: 
            nimi = mj;
            return null;
        case 2:
            merkki = mj;
            return null;
        case 3:
            malli = mj;
            return null;
        case 4:
            try {
                vuosimalli = Integer.parseInt(mj);
                } catch (NumberFormatException e) {
                    return "vuosimalli on väärin";
                }
            return null;
        case 5:
            runkoNro = mj;
            return null;
        default: return "Ei ole olemassa";
        }
    }
    
    
    /**
     * Palauttaa pyörän nimen
     * @return pyörän nimi
     * @example
     * <pre name="test">
     * Pyora pyora1 = new Pyora();
     * pyora1.getNimi() === "";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * Luo kloonin pyörästä
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     * Pyora pyora4 = new Pyora();
     * pyora4.parse(" 4 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123");
     * Pyora kopio = pyora4.clone();
     * pyora4.parse(" 4 |  Kaupunkipyörä  |  Helkama   | Jopo  | 2000   | abc123");
     * kopio.toString().equals(pyora4.toString()) === false;
     * </pre>
     */
    @Override
    public Pyora clone() throws CloneNotSupportedException {
        Pyora uusi;
        uusi = (Pyora)super.clone();
        return uusi;
    }
    
    
    /**
     * Tulostetaan pyörän tiedot parametrina tuotuun tietovirtaan
     * @param os tietovirta johon tiedot tulostetaan
     */
    public void tulosta(OutputStream os) {
        PrintStream out = new PrintStream(os);    // OutputStream käytössä tulevaisuutta varten. Printstreamilla mm. prinln-metodi. 
        out.println("ID: "+tunnusNro + "\n"       // OutputStreamin hankala käyttö ohitetaan luomalla printstream ja käyttämällä sitä tulostamiseen.
                + "Nimi: "+nimi + "\n"            // Printstreamiin annetttu sisältö muutetaan OutPutStreamille sopivaksi automaattisesti.
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
     *  Pyora pyora6 = new Pyora();
     *  pyora6.rekisteroi();
     *  pyora6.getTunnusNro() === 7;
     *  Pyora pyora7 = new Pyora();
     *  pyora7.rekisteroi();
     *  pyora7.getTunnusNro() === 8;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    
    /**
     * Palauttaa pyörän tiedot merkkijonona.
     * @return pyörä tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     * Pyora pyora2 = new Pyora();
     * pyora2.parse(" 2 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123");
     * pyora2.toString() === "2|Kottero|Helkama|Jopo|2000|abc123"
     * </pre>
     */
    @Override
    public String toString() {
        return "" + 
               tunnusNro      + "|" +
               nimi           + "|" +
               merkki         + "|" +
               malli          + "|" +
               vuosimalli     + "|" +
               runkoNro;
    }
    
    
    /**
     * Erottaa annetusta rivistä pyörän tiedot. Pyörän tiedot erotellaan tolppamerkillä | 
     * @param rivi annettu rivi josta pyörän tietoja etsitään
     * @example
     * <pre name="test">
     * Pyora pyora5 = new Pyora();
     * pyora5.parse(" 5 |  Fuji Rakan  |  Fuji   | Rakan  | 2000   | abc123");
     * pyora5.getNimi() === "Fuji Rakan";
     * pyora5.toString() === "5|Fuji Rakan|Fuji|Rakan|2000|abc123"
     * pyora5.getTunnusNro() === 5;
     * pyora5.rekisteroi();                  // rekisteröinti kasvattaa tunnusNro arvoa yhdellä
     * pyora5.getTunnusNro() === 6;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        merkki = Mjonot.erota(sb, '|', merkki);
        malli = Mjonot.erota(sb, '|', malli);
        vuosimalli = Mjonot.erota(sb, '|', vuosimalli);
        runkoNro = Mjonot.erota(sb, '|', runkoNro);
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
        nimi        = "Fuji Rakan" + rand(1,9999);      // rand arpoo satunnaisen luvun väliltä [1,9999]
        merkki      = "Fuji";
        malli       = "Rakan";
        vuosimalli  = rand(1990, 2021);                 // rand arpoo satunnaisen luvun väliltä [1990, 2021]
        runkoNro    = "AVK4DFG" + rand(1,9999);         // rand arpoo satunnaisen luvun väliltä [1,9999]
    }
    

    /**
     * Pääohjelma Pyora-olion testaamiseen.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        // Luodaan ensimmäinen pyörä arvotuilla tiedoilla, rekisteröidään ja tulostetaan
        Pyora pyora = new Pyora();
        pyora.arvoPyora();              
        pyora.rekisteroi();             
        pyora.tulosta(System.out);
        
        // Luodaan toinen pyörä arvotuilla tiedoilla, rekisteröidään ja tulostetaan
        Pyora pyora2 = new Pyora();
        pyora2.arvoPyora();
        pyora2.rekisteroi();
        pyora2.tulosta(System.out);
        
        // Luodaan kolmas pyörä ja muodostetaan pyörän tiedot annetusta merkkijonosta.
        Pyora pyora3 = new Pyora();
        pyora3.parse(" 3 |  Fuji Rakan  |  Fuji   | Rakan  | 2000   | abc123");
    }
}
