/**
 * 
 */
package huoltokirja;
//import huoltokirja.ApuException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Huoltokirjaan kirjatut pyörät. Osaa esimerkiksi lisätä ja poistaa uuden pyörän.
 * @author Henri
 * @version 18.3.2021
 *
 */
public class Pyorat {
    private static final int MAX_PYORIA     = 10;                        // pyörien maksimimäärä luokassa
    private int              lkm            = 0;                        // mihin kohtaan taulukkoa seuraavan pyörän viite lisätään
    private String           tiedostonNimi  = "";                       // tiedosto johon tiedot on tallennettu
    private Pyora[]          pyorat;                                    // Taulukko jossa viitteet tallennettuihin pyöriin

   
    /**
     * Muodostaja pyörät-oliolle.
     */
    public Pyorat() {
        pyorat = new Pyora[MAX_PYORIA];
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
     * @throws IndexOutOfBoundsException virhe jos yritetään palauttaa laitonta indeksiä.
     */
    public Pyora anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi");
        return pyorat[i];
    }
    
    
    /**
     * Tallettaa pyörien tiedot tiedostoon pyorat.dat
     * @param tiednimi tallennettavan tiedoston nimi
     * @throws ApuException jos tallennus epäonnistuu
     * @example
     * <pre>
     * id |Pyörän nimi     |Merkki     |Malli       |Vuosimalli |Runkonumero |
     * 1  |Fuji Rakan      |Fuji       |Rakan 1.3   |2019       |HD65FFHH3   |
     * 3  |Ragley Trig     |Ragley     |Trig        |2020       |RLP00IUY77S |
     * </pre>
     * TODO:testit
     */
    public void tallenna(String tiednimi) throws ApuException {
        File ftied = new File(tiednimi + "/pyorat.dat");
        
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
     * Lukee pyörien tiedot tiedostosta.
     * @param hakemisto kertoo missä tiedosto sijaitsee
     * @throws ApuException minkälainen virheilmoitus näytetään.
     * TODO:testit
     */
    public void lueTiedosto(String hakemisto) throws ApuException {
        tiedostonNimi = hakemisto + "/pyorat.dat";
        File ftied = new File(tiedostonNimi); 
        
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
     * Palauttaa tallennettujen pyörien lukumäärän
     * @return pyörien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * Pääohjelma Pyörät-luokan testaamista varten.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Pyorat pyorat = new Pyorat();
        
        try {
            pyorat.lueTiedosto("pyorat");
        } catch (ApuException ex) {
            System.err.println(ex.getMessage());
        }
        

        Pyora pyora1 = new Pyora();
        pyora1.arvoPyora();
        pyora1.rekisteroi();
        
        Pyora pyora2 = new Pyora();
        pyora2.arvoPyora();
        pyora2.rekisteroi();
        
        try {
            pyorat.lisaa(pyora1);
            pyorat.lisaa(pyora2);
            
            for  (int i = 0; i < pyorat.getLkm() ; i++) {
                Pyora pyora = pyorat.anna(i);
                pyora.tulosta(System.out);
            }
                
        } catch (ApuException e) {
            System.err.println(e.getMessage());
        }
        
        try {
            pyorat.tallenna("pyorat");
        } catch (ApuException e) {
            e.printStackTrace();
        }
    }
}
    
