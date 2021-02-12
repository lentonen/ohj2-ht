/**
 * 
 */
package huoltokirja;
//import huoltokirja.ApuException;

/**
 * Huoltokirjaan kirjatut pyörät. Osaa esimerkiksi lisätä ja poistaa uuden pyörän.
 * @author Henri
 * @version Feb 11, 2021
 *
 */
public class Pyorat {
    private static final int MAX_PYORIA     = 2;                        // pyörien maksimimäärä luokassa
    private int              lkm            = 0;                        // mihin kohtaan taulukkoa seuraavan pyörän viite lisätään
    private String           tiedostonNimi  = "";                       // tiedosto johon tiedot on tallennettu
    private Pyora            pyorat[]       = new Pyora[MAX_PYORIA];    // Taulukko jossa viitteet tallennettuihin pyöriin

   
    /**
     * Lisää uuden pyörän tietorakenteeseen. TODO: Expections
     * @param pyora viite lisättävään pyörään.
     * @throws ApuException virhe kun tietorakenne täynnä
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
    
    
    //TODO: talleta-metodi, joka tallettaa pyörän tiedostoon.
    //TODO: luetiedostosta, lukee pyörien tiedot tiedostosta.
    
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
            System.out.println(e.getMessage());
        }
    }
}
    
