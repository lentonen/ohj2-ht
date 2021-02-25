/**
 * 
 */
package huoltokirja;
//import huoltokirja.ApuException;

/**
 * Huoltokirjaan kirjatut pyörät. Osaa esimerkiksi lisätä ja poistaa uuden pyörän.
 * @author Henri
 * @version 25.2.2021
 *
 */
public class Pyorat {
    private static final int MAX_PYORIA     = 5;                        // pyörien maksimimäärä luokassa
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
     * Tallettaap pyörien tiedot tiedostoon pyorat.dat TODO: tee tiedoston tallettaminen loppuun
     * @throws ApuException minkälainen virheilmoitus näytetään
     */
    public void talleta() throws ApuException {
        //tiedostonNimi = hakemisto + "pyorat.dat"; TODO: tarviiko tätä jossakin vaiheessa?
        throw new ApuException("Ei osata tallentaa tiedostoon" +tiedostonNimi);
    }

    
    /**
     * Lukee pyörien tiedot tiedostosta. TODO: tee tiedoston luku loppuun
     * @param hakemisto kertoo missä tiedosto sijaitsee
     * @throws ApuException minkälainen virheilmoitus näytetään.
     */
    public void lueTiedosto(String hakemisto) throws ApuException {
        tiedostonNimi = hakemisto + "pyorat.dat";
        throw new ApuException("Ei osata lukea vielä tiedostoa " +tiedostonNimi);
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
    }
}
    
