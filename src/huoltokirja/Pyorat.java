package huoltokirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;


/**
 * Luokka huoltokirjan pyörille. Osaa esimerkiksi lisätä ja poistaa uuden pyörän.
 * @author Henri
 * @version 19.3.2021
 */
public class Pyorat {
    private static final int    MAX_PYORIA        = 5;                        // pyörien maksimimäärä luokassa
    private int                 lkm               = 0;                        // Pyörien lukumäärä. Kertoo samalla mihin kohtaan taulukkoa seuraavan pyörän viite lisätään
    private static final String tiedostonNimi  = "pyorat/pyorat.dat";         // tiedostopolku käytettävään tiedostoon
    private Pyora[]             pyorat;                                       // Taulukko jossa viitteet tallennettuihin pyöriin

   
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
     * Lisää uuden pyörän tietorakenteeseen.
     * @param pyora viite lisättävään pyörään.
     * @throws ApuException virhe kun tietorakenne täynnä
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
     * pyorat.lisaa(pyora5); #THROWS ApuException 
     * </pre>
     */
    public void lisaa(Pyora pyora) throws ApuException {
        if (lkm >= pyorat.length) throw new ApuException("Liikaa pyöriä");
        pyorat[lkm] =pyora;
        lkm++;
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
     * @throws ApuException jos tallennus epäonnistuu
     * @example
     * Tiedoston muoto:
     * <pre>
     * id |Pyörän nimi     |Merkki     |Malli       |Vuosimalli |Runkonumero |
     * 1  |Fuji Rakan      |Fuji       |Rakan 1.3   |2019       |HD65FFHH3   |
     * 3  |Ragley Trig     |Ragley     |Trig        |2020       |RLP00IUY77S |
     * </pre>
     */
    public void tallenna() throws ApuException {
        File ftied = new File(tiedostonNimi);
        
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
     * @throws ApuException minkälainen virheilmoitus näytetään.
     * TODO:testit
     */
    public void lueTiedosto() throws ApuException {
        File ftied = new File(tiedostonNimi); 
        
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
        } catch (FileNotFoundException ex) {
            throw new ApuException("Ei saa luettua tiedostoa " +tiedostonNimi);
       // } catch (IOException e) {
       //     throw new ApuException("Ongelmia tiedoston kanssa " +ftied.getAbsolutePath());    
        }
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
            pyorat.lueTiedosto();
        } catch (ApuException ex) {
            System.err.println(ex.getMessage());
        }
        
        // Luodaan pyörä1 arvotuilla tiedoilla ja rekisteröidään
        Pyora pyora1 = new Pyora();
        pyora1.arvoPyora();
        pyora1.rekisteroi();
        
        //Luodaan pyörä2 arvotuilla tiedoilla ja rekisteröidään
        Pyora pyora2 = new Pyora();
        pyora2.arvoPyora();
        pyora2.rekisteroi();
        
        // Yritetään lisätä pyörää taulukkoon
        // Jos taulukko on täynnä, tulostaa virheilmoituksen
        try {
            pyorat.lisaa(pyora1);
            pyorat.lisaa(pyora2);
            
            // Tulostetaan kaikkien pyörien tiedot näkyville
            for  (int i = 0; i < pyorat.getLkm() ; i++) {
                Pyora pyora = pyorat.anna(i);
                pyora.tulosta(System.out);
            }    
        } catch (ApuException e) {
            System.err.println(e.getMessage());
        }
        
        // tallennetaan lisättyjen pyörien tiedot tiedostoon.
        try {
            pyorat.tallenna();
        } catch (ApuException e) {
            System.err.println(e.getMessage());
        }
    }
}