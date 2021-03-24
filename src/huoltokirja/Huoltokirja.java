package huoltokirja;

import java.util.List;

/**
 * @author Henri
 * @version 23.3.2021
 *
 */
public class Huoltokirja {
    private Pyorat pyorat = new Pyorat();           // Huoltokirjan pyörät 
    private Huollot huollot = new Huollot();        // Huoltokirjan huollot

    
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
     * korvaa pyörän tietorakenteessa.
     * @param pyora pyörä jota muokataan
     * @throws ApuException jos pyörää ei voida lisätä
     * TODO: Testit
     */
    public void korvaaTailisaa(Pyora pyora) throws ApuException {
        pyorat.korvaaTaiLisaa(pyora);
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
     * TODO: testit
     */
    public List<Huolto> annaHuollot(Pyora pyora) {
        return huollot.annaHuollot(pyora.getTunnusNro());
    }
    
    
    /**
     * Tallentaa pyörien ja huoltojen tiedot tiedostoihin
     * Yrittää tallettaa huollot, jos pyörien tallentaminen epäonnistuu.
     * Jos jompikumpi epäonnistuu, heittää lopuksi poikkeuksen, joka näyttää mikä epäonnistui.
     * @throws ApuException jos pyörien tai huoltojen tallentaminen epäonnistuu
     */
    public void talleta() throws ApuException {
        String virhe="";
        try {
            pyorat.tallenna("pyorat");
        } catch (ApuException e) {
            virhe = e.getMessage();
        }
        try {
            huollot.tallenna("huollot");
        } catch (ApuException e) {
            virhe += e.getMessage();
        }
        if (!virhe.equals("")) throw new ApuException(virhe);
    }
   
    
     /**
     * @throws ApuException kps lukeminen epäonnistuu
     */
    public void lueTiedosto() throws ApuException {
        pyorat = new Pyorat();      // Tyhjentää olemassaolevan pyorat-olion
        huollot = new Huollot();    // Tyhjentää olemassaolevan huollot-olion
     
        pyorat.lueTiedosto("pyorat");                // Lukee tiedot pyoristä
        huollot.lueTiedosto("huollot");              // Lukee tiedot huolloista
        
    }
    
    
    /**
     * Pääohjelma luokan testaamista varten.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Huoltokirja huoltokirja = new Huoltokirja();
        
        try {
            huoltokirja.lueTiedosto();
        } catch (ApuException e1) {
            System.err.println("Tiedoston luku meni pieleen.");
        }
        
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
        
        try {
            huoltokirja.talleta();
        }catch (ApuException e) {
            System.err.println(e.getMessage());
        }
    }
}
