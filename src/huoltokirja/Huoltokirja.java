/**
 * 
 */
package huoltokirja;

import java.util.List;

/**
 * @author Henri
 * @version 25.2.2021
 *
 */
public class Huoltokirja {
    private final Pyorat pyorat = new Pyorat(); 
    private final Huollot huollot = new Huollot(); 

    /**
     * palauttaa pyörien lukumäärän
     * @return pyörien lukumäärä
     */
    public int getPyoria() {
        return pyorat.getLkm();
    }
    
    
    /**
     * Lsiää pyörän huoltokirjaan
     * @param pyora pyörä joka lisätään
     * @throws ApuException jos pyörää ei voida lisätä
     * TODO: Testit
     */
    public void lisaa(Pyora pyora) throws ApuException {
        pyorat.lisaa(pyora);
    }
    
    
    /**
     * Lisää huollon huoltokirjaan
     * @param huolto huolto joka lisätään
     * @throws ApuException jos huoltoa ei voida lisätä
     * TODO: Testit
     */
    public void lisaa(Huolto huolto) throws ApuException {
        huollot.lisaa(huolto);
    }
    
    
    /**
     * palauttaa pyörän, jonka tunnusnumero annettu parametrina.
     * @param i monesko pyörä palautetaan
     * @return viite pyörään, joka paikassa i
     * @throws IndexOutOfBoundsException jos i laiton
     */
    public Pyora annaPyora(int i) throws IndexOutOfBoundsException {
        return pyorat.anna(i);
    }
    
    
    /**
     * Palauttaa annetun pyörän huollot
     * @param pyora pyörä jonka huollot halutaan
     * @return huollot
     */
    public List<Huolto> annaHuollot(Pyora pyora) {
        return huollot.annaHuollot(pyora.getTunnusNro());
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
            
            int id1 = pyora1.getTunnusNro();
            int id2 = pyora2.getTunnusNro();
            
            Huolto huolto1 = new Huolto(id1); huolto1.arvoHuolto(); huolto1.rekisteroi(); huoltokirja.lisaa(huolto1);
            Huolto huolto2 = new Huolto(id1); huolto2.arvoHuolto(); huolto2.rekisteroi(); huoltokirja.lisaa(huolto2);
            Huolto huolto3 = new Huolto(id2); huolto3.arvoHuolto(); huolto3.rekisteroi(); huoltokirja.lisaa(huolto3);
            Huolto huolto4 = new Huolto(id2); huolto4.arvoHuolto(); huolto4.rekisteroi(); huoltokirja.lisaa(huolto4);
            Huolto huolto5 = new Huolto(id2); huolto5.arvoHuolto(); huolto5.rekisteroi(); huoltokirja.lisaa(huolto5);
        
            for (int i = 0; i < huoltokirja.getPyoria(); i++) {
                Pyora pyora = huoltokirja.annaPyora(i);
                System.out.println("=========================================");
                pyora.tulosta(System.out);
                List<Huolto> loytyneet = huoltokirja.annaHuollot(pyora);
                for (Huolto huolto : loytyneet) {
                    huolto.tulosta(System.out);
                }           
            }
        } catch (ApuException e) {
            System.err.println(e.getMessage());
        }
    }
}
