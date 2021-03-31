package huoltokirja;

import static huoltokirja.Apulaskut.rand;
import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Huolto-luokka yksittäiselle huollolle.
 * @author Henri
 * @version 31.3.2021
 */
public class Huolto implements Cloneable, Tietue{
    private int tunnusNro;                  // Numero joka yksilöi huollon
    private int pyoraNro;                   // Pyörä jota huolto koskee
    private String nimi             = "";   // Huollon nimi
    private int ajotunnit;                  // Kuinka paljon pyörällä on ajettu ennen huoltoa
    private String toimenpiteet     = "";   // Huoltotoimenpiteiden kuvaus
    private static int seuraavaNro = 1;     // Ilmaisee seuraavan vapaana olevan tunnusnumeron, static = "on olemassa, vaikka olioita ei olisi luotu."
    
    
    /**
     * Oletusmuodostaja
     */
    public Huolto() {
        // attribuutit alustetaan esittelyssä. Int-tyyppiset alustuvat automaattisesti nollaksi, jos muuta ei anneta.
    }
    
    
    /**
     * muodostaja jolle viedään pyörän numero.
     * @param pyoraID pyörä, johon huolto halutaan liittää
     */
    public Huolto(int pyoraID) {
        this.pyoraNro = pyoraID;
    }
    
    
    /**
     * Asettaa tunnusnumeron ja varmistaa, että seuraavaNro on ajantasalla. Private, koska muiden ei haluta asettavan attribuuttien arvoja.
     * @param nro asetettava tunnusnumero
     */
    private void setTunnusNro(int nro) {
        tunnusNro = nro;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    
    /**
     * Palauttaa huollon nimen
     * @return huollon nimi
     */
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * Saantimetodi ajotunneille
     * @return huollon ajotunnit
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
                +"Toimenpiteet: "+toimenpiteet + "\n");
    }
       
    
    /**
     * Palauttaa huollon tiedot merkkijonona.
     * @return huolto tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     * Huolto huolto1 = new Huolto();
     * huolto1.parse(" 1  |  1  |  Jarruhuolto  | 75 | puhdistin ja vaihdoin");
     * huolto1.toString() === "1|1|Jarruhuolto|75|puhdistin ja vaihdoin";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + 
               tunnusNro      + "|" +
               pyoraNro       + "|" +
               nimi           + "|" +
               ajotunnit      + "|" +
               toimenpiteet;
    }
    
    
    /**
     * Erottaa annetusta rivistä huollon tiedot. Huollon tiedot erotellaan | merkillä.
     * @param rivi rivi josta huollon tietoja etsitään
     * @example
     * <pre name="test">
     * Huolto huolto2 = new Huolto();
     * huolto2.parse(" 2  |  2  |  Iskari  | 100 | Öljynvaihto");
     * huolto2.getNimi() === "Iskari";
     * huolto2.getAjotunnit() === 100;
     * huolto2.toString() === "2|2|Iskari|100|Öljynvaihto"
     * huolto2.getTunnusNro() === 2;
     * huolto2.rekisteroi();                  // rekisteröinti kasvattaa tunnusNro arvoa yhdellä
     * huolto2.getTunnusNro() === 3;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        pyoraNro = Mjonot.erota(sb, '|', pyoraNro);
        nimi = Mjonot.erota(sb, '|', nimi);
        ajotunnit = Mjonot.erota(sb, '|', ajotunnit);
        toimenpiteet = Mjonot.erota(sb, '|', toimenpiteet);
    }
    
    
    /**
     * Antaa huollolle seuraavan vapaana olevan tunnusNro:n.
     * @return Vapaana oleva tunnusNro.
     * @example
     * <pre name="test">
     *  Huolto huolto3 = new Huolto();
     *  huolto3.rekisteroi();
     *  huolto3.getTunnusNro() === 4;   // TunnusNro = 4, koska huolto 2 rekisteröitiin kahdesti.
     *  Huolto huolto4 = new Huolto();
     *  huolto4.rekisteroi();
     *  huolto4.getTunnusNro() === 5;
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
     * Palauttaa tehdyt huollot
     * @return toimenpiteet huollossa
     */
    public String getToimenpiteet() {
        return toimenpiteet;
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
        
        Huolto huolto3 = new Huolto();
        huolto3.parse(" 3 |  1  |  Iskarihuolto   | 125  | Alajalkojen huolto ");
        huolto3.tulosta(System.out);
    }


    @Override
    public int getKenttia() {
        return 5;
    }


    @Override
    public int ekaKentta() {
        return 2;
    }
    
    @Override
    public int ekaIsoKentta() {
        return 4;
    }


    @Override
    public String getKentanNimi(int k) {
        switch (k) {
        case 0: return "Huollon tunnus nro" ;                                            
        case 1: return "Pyörän tunnus nro";
        case 2: return "Nimi";
        case 3: return "Ajotunnit";
        case 4: return "Toimenpiteet";
        default: return "Ei ole olemassa";
        }
    }


    @Override
    public String anna(int k) {
        switch (k) {
        case 0: return "" +tunnusNro ;                                            
        case 1: return "" +pyoraNro;
        case 2: return "" +nimi;
        case 3: return "" +ajotunnit;
        case 4: return "" +toimenpiteet;
        default: return "Ei ole olemassa";
        }
    }


    @Override
    public String aseta(int k, String jono) {
        String mj = jono.trim();
        //StringBuilder sb = new StringBuilder(mj);
        switch (k) {
        case 0:
            try {
                setTunnusNro(Integer.parseInt(mj));
                } catch (NumberFormatException e) {
                    return "Huollon tunnusnumero on väärin";
                }
            return null;
        case 1: 
            try {
                pyoraNro = Integer.parseInt(mj);
                } catch (NumberFormatException e) {
                    return "Pyörän tunnusnumero on väärin";
                }
            return null;
        case 2:
            nimi = mj;
            return null;
        case 3:
            try {
                ajotunnit = Integer.parseInt(mj);
            } catch (NumberFormatException e) {
                return "Ajotunnit on väärin";
            }
            return null;
        case 4:
            toimenpiteet = mj;
            return null;
        default: return "Ei ole olemassa";
        }
    }


    @Override
    public Huolto clone() throws CloneNotSupportedException {
        Huolto uusi;
        uusi = (Huolto)super.clone();
        return uusi;
    }
}
