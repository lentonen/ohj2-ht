package huoltokirja;

import static huoltokirja.Apulaskut.rand;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Huolto-luokka yksittäiselle huollolle.
 * @author Henri Leinonen
 * @version 20.4.2021
 */
public class Huolto implements Cloneable, Tietue{
    private int tunnusNro;                  // Numero joka yksilöi huollon                      kenttä [0]
    private int pyoraNro;                   // Pyörä jota huolto koskee                         kenttä [1]
    private String pvm;                     // Huollon pvm.                                     kenttä [2]
    private double hinta;                   // Huollon hinta                                    kenttä [3]
    private String nimi             = "";   // Huollon nimi                                     kenttä [4]
    private int ajotunnit;                  // Kuinka paljon pyörällä on ajettu ennen huoltoa   kenttä [5]
    private String toimenpiteet     = "";   // Huoltotoimenpiteiden kuvaus                      kenttä [6]
    private static int seuraavaNro = 1;     // Ilmaisee seuraavan vapaana olevan tunnusnumeron, static = "on olemassa, vaikka olioita ei olisi luotu."
    
    
    /**
     * Luokka vertaa kahta huoltoa keskenään
     */
    public static class Vertailija implements Comparator<Huolto> {
        
        private int k;
        
        /**
         * Muodostaja jolle tuodaan parametrina käytetyn kentän numero
         * @param k kenttä jonka mukaan vertaillaan
         */
        public Vertailija(int k) {
            this.k = k;
        }
        

        @Override
        public int compare(Huolto huolto1, Huolto huolto2) {
            return huolto1.avain(k).compareTo(huolto2.avain(k));
        }
    }
    
    
    /**
     * Oletusmuodostaja
     */
    public Huolto() {
        asetaNykyinenPvm();
    }
    

    /**
     * muodostaja jolle viedään pyörän numero.
     * @param pyoraID pyörä, johon huolto halutaan liittää
     */
    public Huolto(int pyoraID) {
        this.pyoraNro = pyoraID;
        asetaNykyinenPvm();
        
    }
    
    
    /**
     * Asettaa huollolle nykyisen päivämäärän.
     */
    private void asetaNykyinenPvm() {
     // Asettaa huollolle automaattisesti nykyisen päivämäärän.
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String tanaan = formatter.format(date);
        this.pvm = tanaan;
        aseta(2, tanaan);
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
        out.println//("HuoltoID: "+tunnusNro + "\n"         // OutputStreamin hankala käyttö ohitetaan luomalla printstream ja käyttämällä sitä tulostamiseen.
                //+ "PyöräID: "+pyoraNro + "\n"
                ("Nimi: "+nimi + "\n" 
                +"Ajotunnit: "+ajotunnit + "\n" 
                +"Toimenpiteet: "+toimenpiteet + "\n");
    }
       
    
    /**
     * Palauttaa huollon tiedot merkkijonona.
     * @return huolto tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     * Huolto huolto1 = new Huolto();
     * huolto1.parse(" 1  |  1  |2.1.2021 |150 |  Jarruhuolto  | 75 | puhdistin ja vaihdoin");
     * huolto1.toString() === "1|1|2.1.2021|150.00|Jarruhuolto|75|puhdistin ja vaihdoin";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + 
               tunnusNro                              + "|" +
               pyoraNro                               + "|" +
               pvm                                    + "|" +
               String.format("%.2f", hinta )          + "|" +
               nimi                                   + "|" +
               ajotunnit                              + "|" +
               toimenpiteet;
    }
    
    
    /**
     * Erottaa annetusta rivistä huollon tiedot. Huollon tiedot erotellaan | merkillä.
     * @param rivi rivi josta huollon tietoja etsitään
     * @example
     * <pre name="test">
     * Huolto huolto2 = new Huolto();
     * huolto2.parse(" 2  |  2  | 1.1.2020|200  | Iskari  | 100 | Öljynvaihto");
     * huolto2.getNimi() === "Iskari";
     * huolto2.getAjotunnit() === 100;
     * huolto2.toString() === "2|2|1.1.2020|200.00|Iskari|100|Öljynvaihto"
     * huolto2.getTunnusNro() === 2;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        pyoraNro = Mjonot.erota(sb, '|', pyoraNro);
        pvm = Mjonot.erota(sb, '|', pvm);
        hinta = Mjonot.erota(sb, '|', hinta);
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
     *  huolto3.getTunnusNro() === 3;  
     *  Huolto huolto4 = new Huolto();
     *  huolto4.rekisteroi();
     *  huolto4.getTunnusNro() === 4;
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
    

    @Override
    public int getKenttia() {
        return 7;
    }


    @Override
    public int ekaKentta() {
        return 2;
    }
    
    
    @Override
    public int ekaIsoKentta() {
        return 6;
    }


    @Override  // testit Tietue-luokassa
    public String getKentanNimi(int k) {
        switch (k) {
        case 0: return "Huollon tunnus nro" ;                                            
        case 1: return "Pyörän tunnus nro";
        case 2: return "Päivämäärä";
        case 3: return "Hinta";
        case 4: return "Nimi";
        case 5: return "Ajotunnit";
        case 6: return "Toimenpiteet";
        default: return "Ei ole olemassa";
        }
    }


    @Override // testit Tietue-luokassa
    public String anna(int k) {
        switch (k) {
        case 0: return "" +tunnusNro ;                                            
        case 1: return "" +pyoraNro;
        case 2: return "" +pvm;
        case 3: return "" +hinta;
        case 4: return "" +nimi;
        case 5: return "" +ajotunnit;
        case 6: return "" +toimenpiteet;
        default: return "Ei ole olemassa";
        }
    }
    
    
    /**
     * Antaa kentän k sisällön vertailuun sopivana merkkijonona
     * @param k palautettavan kentän numero
     * @return kentän sisältö merkkijonona
     * @example
     * <pre name="test">
     * Huolto huolto = new Huolto();
     * huolto.aseta(0,"1");
     * huolto.avain(0) ==="  1";
     * huolto.aseta(1,"12");
     * huolto.avain(1) ===" 12";
     * huolto.aseta(2,"1.1.2021");
     * huolto.avain(2) ==="2021 1 1";
     * huolto.aseta(3,"150.23");
     * huolto.avain(3) ==="0.15023";
     * huolto.aseta(4,"FUJI");
     * huolto.avain(4) ==="fuji";
     * huolto.aseta(5,"150");
     * huolto.avain(5) ==="  150";
     * huolto.aseta(6,"Putsattiin");
     * huolto.avain(6) ==="putsattiin";
     * </pre>
     */
    public String avain(int k) {
        switch (k) {
        case 0: return "" +String.format("%3d", tunnusNro);
        case 1: return "" +String.format("%3d", pyoraNro);
        case 2: {
            StringBuilder sb = new StringBuilder(pvm);
            int pp = Integer.parseInt(Mjonot.erota(sb, '.'));
            int kk = Integer.parseInt(Mjonot.erota(sb, '.'));
            int vv = Integer.parseInt(sb.toString());
            return ""+vv+String.format("%2d", kk)+String.format("%2d", pp);
        }
        case 3: return "" +String.format("%.5f", hinta/1000);
        case 4: return "" +nimi.toLowerCase();
        case 5: return "" +String.format("%5d", +ajotunnit);
        case 6: return "" +toimenpiteet.toLowerCase();
        default: return "Ei ole olemassa";
        }
    }

    
    /**
     * @example
     * <pre name="test">
     * Huolto huolto = new Huolto();
     * huolto.aseta(0,"1") === null;
     * huolto.avain(0) ==="  1";
     * huolto.aseta(0,"1a") === "Huollon tunnusnumero on väärin";
     * huolto.avain(0) ==="  1";
     * 
     * huolto.aseta(1,"12") === null;
     * huolto.avain(1) ===" 12";
     * huolto.aseta(1,"12a") === "Pyörän tunnusnumero on väärin";
     * huolto.avain(1) ===" 12";
     * 
     * huolto.aseta(2,"1.1.2021") === null;
     * huolto.avain(2) ==="2021 1 1";
     * huolto.aseta(2,"32.1.2021") === "Päivämäärä on väärin";
     * huolto.avain(2) ==="2021 1 1";
     * 
     * huolto.aseta(3,"150.23") === null;
     * huolto.avain(3) ==="0.15023";
     * huolto.aseta(3,"150.23g") === "hinta väärin";
     * huolto.avain(3) ==="0.15023";
     * 
     * huolto.aseta(4,"FUJI") === null;
     * huolto.avain(4) ==="fuji";
     * 
     * huolto.aseta(5,"150") === null;
     * huolto.avain(5) ==="  150";
     * huolto.aseta(5,"150a") ==="Ajotunnit on väärin";
     * huolto.avain(5) ==="  150";
     * 
     * huolto.aseta(6,"Putsattiin") === null;
     * huolto.avain(6) ==="putsattiin";
     * </pre>
     */
    @Override // Testejä avain-metodin yhteydessä sekä Tietue-luokassa
    public String aseta(int k, String jono) {
        String mj = jono.trim();
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
            if (Apulaskut.onkoLaitonPvm(mj)) return "Päivämäärä on väärin";
            pvm = mj;   
            return null;
        case 3:
            try {
            hinta = Double.parseDouble(mj);
            } catch (NumberFormatException e) {
                return "hinta väärin";
            }
            return null;
        case 4:
            nimi = mj;
            return null;
        case 5:
            try {
                ajotunnit = Integer.parseInt(mj);
            } catch (NumberFormatException e) {
                return "Ajotunnit on väärin";
            }
            return null;
        case 6:
            toimenpiteet = mj;
            return null;
        default: return "Ei ole olemassa";
        }
    }


    @Override // Testit tietue-luokassa
    public Huolto clone() throws CloneNotSupportedException {
        Huolto uusi;
        uusi = (Huolto)super.clone();
        return uusi;
    }
    
    
    /**
     * Pääohjelma Huolto-luokan testaamiseen.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Huolto huolto = new Huolto(1);
        huolto.arvoHuolto();
       // huolto.rekisteroi();
        huolto.tulosta(System.out);
        
        Huolto huolto2 = new Huolto(5);
        huolto2.arvoHuolto();
       // huolto2.rekisteroi();
        huolto2.tulosta(System.out);
        
        Huolto huolto3 = new Huolto();
        huolto3.parse(" 3 |  1  |  Iskarihuolto   | 125  | Alajalkojen huolto ");
        huolto3.tulosta(System.out);
    }
}
