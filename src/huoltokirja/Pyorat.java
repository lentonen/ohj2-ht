package huoltokirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


/**
 * Luokka huoltokirjan pyörille. Osaa esimerkiksi lisätä ja poistaa uuden pyörän.
 * @author Henri Leinonen
 * @version 20.4.2021
 */
public class Pyorat {
    private static final int    MAX_PYORIA        = 5;                        // pyörien maksimimäärä alussa
    private int                 lkm               = 0;                        // Pyörien lukumäärä. Kertoo samalla mihin kohtaan taulukkoa seuraavan pyörän viite lisätään
    private static final String tiedostonNimi     = "/pyorat.dat";            // tiedostopolku käytettävään tiedostoon
    private Pyora[]             pyorat;                                       // Taulukko jossa viitteet tallennettuihin pyöriin
    private boolean muutettu                      = false;                    // true, jos pyorat-luokkassa lisätty tai muokattu pyöriä. False muuten.
   
    /**
     * Muodostaja pyörät-oliolle.
     */
    public Pyorat() {
        pyorat = new Pyora[MAX_PYORIA];
    }
    
    
    /**
     * Palauttaa tallennettujen pyörien lukumäärän
     * @return pyörien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Lisää uuden pyörän tietorakenteeseen. Tietorakenne kasvaa automaattisesti
     * kun pyöriä yritetään lisätä täynnä olevaan tietorakenteeseen
     * @param pyora viite lisättävään pyörään.
     * @example
     * <pre name="test">
     * #THROWS ApuException 
     * Pyorat pyorat = new Pyorat();
     * Pyora pyora1 = new Pyora(), pyora2 = new Pyora(); Pyora pyora3 = new Pyora(); Pyora pyora4 = new Pyora(); Pyora pyora5 = new Pyora();
     * pyorat.getLkm() === 0;
     * pyorat.lisaa(pyora1); pyorat.getLkm() === 1;
     * pyorat.lisaa(pyora2); pyorat.getLkm() === 2; 
     * pyorat.lisaa(pyora3); pyorat.getLkm() === 3; 
     * pyorat.anna(0) === pyora1;
     * pyorat.anna(1) === pyora2;
     * pyorat.anna(2) === pyora3;
     * pyorat.anna(3) === pyora3; #THROWS IndexOutOfBoundsException 
     * pyorat.anna(-1) === pyora3; #THROWS IndexOutOfBoundsException 
     * pyorat.lisaa(pyora4); pyorat.getLkm() === 4;
     * pyorat.lisaa(pyora5); pyorat.getLkm() === 5;
     * pyorat.lisaa(pyora5); // Voidaan lisätä 6.pyörä, koska tietorakenne kasvaa
     * </pre>
     */
    public void lisaa(Pyora pyora) {
        int vanhaPituus = pyorat.length;
        if (lkm >= vanhaPituus) {
            int uusiPituus = 2 * vanhaPituus;
            Pyora[] uusiTaulukko = new Pyora[uusiPituus];
            for (int i = 0; i < vanhaPituus; i++)
                uusiTaulukko[i] = anna(i);
            pyorat = uusiTaulukko;    
        }
        pyorat[lkm] =pyora;
        lkm++;
        muutettu = true;
    }
    
    
    /**
     * @param pyora jonka tietoja päivitetään
     * @example
     * <pre name="test">
     * Pyorat pyorat = new Pyorat();
     * pyorat.getLkm() === 0;
     * Pyora pyora1 = new Pyora();
     * pyora1.parse(" 1 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123");
     * pyorat.lisaa(pyora1);
     * pyorat.getLkm() === 1;
     * Pyora pyora2 = new Pyora();
     * pyora2.parse(" 1 |  Hybridi  |  Tunturi   | Pappa | 1999   | abc124");
     * pyorat.korvaaTaiLisaa(pyora2);
     * pyorat.getLkm() === 1;
     * pyorat.anna(0).anna(1) === "Hybridi";
     * Pyora pyora3 = new Pyora();
     * pyora3.parse(" 2 |  Citypyörä  |  Trek   | City | 2010   | poi567");
     * pyorat.korvaaTaiLisaa(pyora3);
     * pyorat.getLkm() === 2;
     * pyorat.anna(1).anna(1) === "Citypyörä";
     * </pre>
     */
    public void korvaaTaiLisaa(Pyora pyora) {
        int id = pyora.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if (pyorat[i].getTunnusNro() == id) {
                pyorat[i] = pyora;      // jos löydetään id:tä vastaava pyörä, niin korvataan se tuodulla pyörällä.
                muutettu = true;
                return;                
            }
        }
        lisaa(pyora);
    }
    
    
    /**
     * Poistetaan pyörä tietorakenteesta
     * @param tunnus poistettavan pyörän tunnusNro
     * @return 0 jos ei löydy, 1 jos poistettiin
     * @example
     * <pre name="test">
     * Pyorat pyorat = new Pyorat();
     * pyorat.getLkm() === 0;
     * Pyora pyora = new Pyora();
     * pyora.parse(" 4 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123");
     * pyorat.lisaa(pyora);
     * pyorat.getLkm() === 1;
     * pyorat.poista(1) === 0;  // pyörää ei löydy
     * pyorat.poista(4) === 1;  // pyörä löytyy
     * pyorat.getLkm() === 0;
     * </pre>
     */
    public int poista(int tunnus) {
        int poistettavanPaikka = etsiPaikka(tunnus);
        if (poistettavanPaikka < 0) return 0;  // Jos pyörää ei löydetä niin palautetaan 0.
        lkm--;
        for (int j = poistettavanPaikka; j < lkm; j++) 
            pyorat[j] = pyorat[j+1];
        pyorat[lkm] = null;
        muutettu = true;
        return 1;
    }
    
    
    /**
     * Palauttaa tunnusta vastaavan pyörän indeksin
     * @param tunnus tunnusNro jota etsitään
     * @return pyörän indeksi tietorakenteessa, palauttaa -1 jos pyörää ei löydy
     * @example
     * <pre name="test">
     * Pyorat pyorat = new Pyorat();
     * Pyora pyora1 = new Pyora();
     * Pyora pyora2 = new Pyora();
     * pyorat.etsiPaikka(1) === -1;
     * pyora1.parse(" 1 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123");
     * pyora2.parse(" 2 |  Hybridi  |  Tunturi   | Pappa | 1999   | abc124");
     * pyorat.lisaa(pyora1); pyorat.lisaa(pyora2);
     * pyorat.etsiPaikka(1) === 0;
     * pyorat.etsiPaikka(2) === 1;
     * pyorat.etsiPaikka(3) === -1;
     * </pre>
     */
    public int etsiPaikka(int tunnus) {
        int indeksi = -1;
        for (int i = 0; i < lkm; i++)
            if (tunnus == pyorat[i].getTunnusNro()) {
                indeksi = i;
            }
        return indeksi;
    }


    /**
     * Palauttaa viitteen pyörään, jonka viite on pyorat-taulukossa kohdassa i.
     * @param i pyorat-taulukon indeksi, josta viite halutaan
     * @return viite pyorat-taulukon kohdassa i olevaan pyoraan.
     * @throws IndexOutOfBoundsException virhe jos yritetään palauttaa pyörää, jota ei ole lisätty taulukkoon.
     */
    public Pyora anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi");   // Testattu lisaa-metodissa
        return pyorat[i];
    }
    
    
    /**
     * Tallettaa pyörien tiedot tiedostoon pyorat/pyorat.dat
     * @param hakemisto hakemisto johon tiedosto tallennetaan
     * @throws ApuException jos tallennus epäonnistuu
     * @example
     * Tiedoston muoto:
     * <pre>
     * id |Pyörän nimi     |Merkki     |Malli       |Vuosimalli |Runkonumero |
     * 1  |Fuji Rakan      |Fuji       |Rakan 1.3   |2019       |HD65FFHH3   |
     * 3  |Ragley Trig     |Ragley     |Trig        |2020       |RLP00IUY77S |
     * 
     * Testaaminen suoritettu metodissa lueTiedosto(String hakemisto).
     * </pre>
     */
    public void tallenna(String hakemisto) throws ApuException {
        if (!muutettu) return;
        
        File ftied = new File(hakemisto + tiedostonNimi);    
        ftied.getParentFile().mkdirs(); // Luo hakemiston mikäli sitä ei ole olemassa
        
        // Yritetään avata uusi PrintStream (Tiedoston avulla luodun FileOutputStreamin avulla), jonka avulla printataan halutut tiedot FileOutputStreamiin.
        // Otetaan kiinni poikkeus, jos tiedostoa ei löydy/saada jostakin syystä avattua.
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for (int i = 0; i < getLkm(); i++) {
                Pyora pyora = anna(i);
                fo.println(pyora);
            }
        } catch (FileNotFoundException ex) {
            throw new ApuException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
        }
    }

    
    /**
     * Lukee pyörien tiedot tiedostosta ja lisää löydetyt pyörät taulukko-tietorakenteeseen.
     * @param hakemisto hakemisto josta tiedostoa yritetään lukea
     * @throws ApuException minkälainen virheilmoitus näytetään.
     * @example
     * <pre name="test">
     * #THROWS ApuException
     * #THROWS IndexOutOfBoundsException 
     * #import java.io.File;
     * 
     * Pyorat pyorat = new Pyorat();
     * Pyora pyora6 = new Pyora(); pyora6.arvoPyora(); pyorat.lisaa(pyora6);
     * Pyora pyora7 = new Pyora(); pyora7.arvoPyora(); pyorat.lisaa(pyora7);
     * String hakemisto = "testipyorat";
     * String tiedNimi = hakemisto +"/pyorat.dat";
     * File ftied = new File(tiedNimi);
     * ftied.getParentFile().mkdirs();
     * ftied.delete();
     * pyorat.lueTiedosto(hakemisto); #THROWS ApuException
     * pyorat.tallenna(hakemisto);
     * pyorat = new Pyorat();   // Tehdään uusi pyorat-olio vanhan päälle
     * pyorat.getLkm() === 0;   // Uudessa ei pitäisi olla yhtäkään pyörää
     * pyorat.lueTiedosto(hakemisto);
     * pyorat.getLkm() === 2;   // Uudessa pitäisi nyt olla kaksi aiemmin lisättyä pyörää
     * pyorat.anna(0).equals(pyora6);
     * pyorat.anna(1).equals(pyora7);
     * pyorat.anna(2).equals(pyora7); #THROWS IndexOutOfBoundsException
     * ftied.delete() === true;  // tuhoaa .dat-tiedoston
     * ftied.getParentFile().delete() === true;  // tuhoaa testikansion
     * </pre>
     */
    public void lueTiedosto(String hakemisto) throws ApuException {
        File ftied = new File(hakemisto +tiedostonNimi); 
       
        // FileInputStream muodostaa yhteyden tiedostoon. Scanner muuttaa tietovirrasta tulevan datan merkeiksi.
        // Otetaan kiinni poikkeus, jos tiedostoa ei löydy.
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while (fi.hasNext()) {
                String s = "";
                s = fi.nextLine();
                Pyora pyora = new Pyora();
                pyora.parse(s); // voisi palauttaa onnistuuko parsiminen BOOL
                lisaa(pyora);
            }
            muutettu = false;
        } catch (FileNotFoundException ex) {
            throw new ApuException("Ei saa luettua tiedostoa " +tiedostonNimi);
        }
    }
    
    
    /**
     * Etsi hakuehdot täyttävät pyörät ja palauttaa ne.
     * @param ehto jolla pyöriä etsitään
     * @param k kenttä jonka mukaan etsitään
     * @return hakuehdot täyttävät pyörät
     * @example
     * <pre name="test">
     * #import java.util.List;
     * Pyorat pyorat = new Pyorat();
     * Pyora pyora = new Pyora();
     * pyora.parse(" 4 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123");
     * pyorat.lisaa(pyora);
     * Pyora pyora2 = new Pyora();
     * pyora2.parse(" 5 |  Kaupunkipyörä  |  Helkama   | Jopo  | 2020   | fgh236");
     * pyorat.lisaa(pyora2);
     * Pyora pyora3 = new Pyora();
     * pyora3.parse(" 5 |  Gravel  |  Helkama   | CS2800  | 2010   | GR4321");
     * pyorat.lisaa(pyora3);
     * List<Pyora> loydetyt = pyorat.etsi("*K*", 1);
     * loydetyt.size() === 2;
     * loydetyt.get(0) == pyora2 === true;
     * loydetyt.get(1) == pyora === true;
     * loydetyt = pyorat.etsi("*Helkama*", 2);
     * loydetyt.size() === 3;
     * loydetyt = pyorat.etsi("*S*", 3);
     * loydetyt.size() === 1;
     * loydetyt.get(0) == pyora3 === true;
     * loydetyt = pyorat.etsi("*20*", 4);
     * loydetyt.size() === 3;
     * loydetyt.get(0) == pyora === true;
     * loydetyt.get(1) == pyora3 === true;
     * loydetyt.get(2) == pyora2 === true;
     * loydetyt = pyorat.etsi("*1*", 5);
     * loydetyt.size() === 2;
     * loydetyt.get(0) == pyora === true;
     * loydetyt.get(1) == pyora3 === true;
     * </pre>
     */
    public List<Pyora> etsi(String ehto, int k) {
        List<Pyora> loydetyt = new ArrayList<Pyora>();
        for (int i = 0; i < getLkm(); i++) {
            if (Apulaskut.onkoSamat(anna(i).anna(k), ehto)) {
                loydetyt.add(anna(i));
            }
        }
        Collections.sort(loydetyt, new Pyora.Vertailija(k));
        return loydetyt;
    }
    
    
    /**
     * Pääohjelma Pyörät-luokan testaamista varten.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Pyorat pyorat = new Pyorat();
        
        // Luetaan aiemmin lisättyjen pyörien tiedot tiedostosta.
        // Otetaan virhe kiinni, jos ei onnistu.
        try {
            pyorat.lueTiedosto("pyorat");
        } catch (ApuException ex) {
            System.err.println(ex.getMessage());
        }
        
        // Luodaan pyörä1 arvotuilla tiedoilla ja rekisteröidään
        Pyora pyora1 = new Pyora();
        pyora1.arvoPyora();
        //pyora1.rekisteroi();
        
        //Luodaan pyörä2 arvotuilla tiedoilla ja rekisteröidään
        Pyora pyora2 = new Pyora();
        pyora2.arvoPyora();
        //pyora2.rekisteroi();
        
        // Yritetään lisätä pyörää taulukkoon
        // Jos taulukko on täynnä, tulostaa virheilmoituksen

        pyorat.lisaa(pyora1);
        pyorat.lisaa(pyora2);
            
        // Tulostetaan kaikkien pyörien tiedot näkyville
        for  (int i = 0; i < pyorat.getLkm() ; i++) {
            Pyora pyora = pyorat.anna(i);
            pyora.tulosta(System.out);
        }    

        
        // tallennetaan lisättyjen pyörien tiedot tiedostoon.
        try {
            pyorat.tallenna("pyorat");
        } catch (ApuException e) {
            System.err.println(e.getMessage());
        }
    }
}