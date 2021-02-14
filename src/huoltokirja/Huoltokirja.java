/**
 * 
 */
package huoltokirja;

/**
 * @author Henri
 * @version Feb 11, 2021
 *
 */
public class Huoltokirja {
    private final Pyorat pyorat = new Pyorat(); 

    /**
     * palauttaa pyörien lukumäärän
     * @return pyörien lukumäärä
     */
    public int getPyoria() {
        return pyorat.getLkm();
    }
    
    
    /**
     * @param pyora pyörä joka lisätään
     * @throws ApuException jos pyörää ei voida lisätä
     * TODO: Testit
     */
    public void lisaa(Pyora pyora) throws ApuException {
        pyorat.lisaa(pyora);
    }
    
    
    /**
     * @param i monesko pyörä palautetaan
     * @return viite pyörään, joka paikassa i
     * @throws IndexOutOfBoundsException jos i laiton
     */
    public Pyora annaPyora(int i) throws IndexOutOfBoundsException {
        return pyorat.anna(i);
    }
    
    
    /**
     * Tallentaa pyörien tiedot huoltokirjaan
     * @throws ApuException jos tallentaminen epäonnistuu
     */
    public void talleta() throws ApuException {
        pyorat.talleta();
    }
   
    
     /**
     * @param nimi mitä tiedostoa luetaan
     * @throws ApuException kps lukeminen epäonnistuu
     */
    public void lueTiedosto(String nimi) throws ApuException {
        pyorat.lueTiedosto(nimi);
    }


    //TODO: poista
    
    
    /**
     * Pääohjelma luokan testaamista varten.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Huoltokirja huoltokirja = new Huoltokirja();
        
        try {
            Pyora pyora1 = new Pyora();
            pyora1.arvoPyora();
            pyora1.rekisteroi();
        
            Pyora pyora2 = new Pyora();
            pyora2.arvoPyora();
            pyora2.rekisteroi();
        
            huoltokirja.lisaa(pyora1);
            huoltokirja.lisaa(pyora2);
        
            for (int i = 0; i < huoltokirja.getPyoria(); i++) {
                huoltokirja.annaPyora(i).tulosta(System.out);
            }
        } catch (ApuException e) {
            System.out.println(e.getMessage());
        }
    }

}
