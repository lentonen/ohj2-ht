package huoltokirja;
import java.util.Collection;
import java.util.List;

/**
 * Huoltokirja, joka sisältää pyöriä ja huoltoja. 
 * @author Henri Leinonen
 * @version 13.4.2021
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
    
    
    /** palauttaa huoltojen lukumäärän
     * @return huoltojen lukumäärä
     */
    public int getHuoltoja() {
        return huollot.getLkm();
    }
    
    
    /**
     * Lisää pyörän huoltokirjaan
     * @param pyora pyörä joka lisätään
     * @throws ApuException jos pyörää ei voida lisätä
     * #THROWS ApuException
     * Huoltokirja huoltokirja = new Huoltokirja();
     * huoltokirja.getPyoria() === 0;
     * Pyora pyora = new Pyora();
     * pyora.parse(" 1 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123");
     * huoltokirja.lisaa(pyora);
     * huoltokirja.getPyoria() === 1;
     * huoltokirja.lisaa(pyora);
     * huoltokirja.getPyoria() === 2;
     * </pre>
     */
    public void lisaa(Pyora pyora) throws ApuException {
        pyorat.lisaa(pyora);
    }
    
    
    /**
     * korvaa pyörän tietorakenteessa.
     * @param pyora pyörä jota muokataan
     * @throws ApuException jos pyörää ei voida lisätä
     * @example
     * <pre name="test">
     * #THROWS ApuException
     * Huoltokirja huoltokirja = new Huoltokirja();
     * huoltokirja.getPyoria() === 0;
     * Pyora pyora1 = new Pyora();
     * pyora1.parse(" 1 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123");
     * huoltokirja.lisaa(pyora1);
     * huoltokirja.getPyoria() === 1;
     * Pyora pyora2 = new Pyora();
     * pyora2.parse(" 1 |  Hybridi  |  Tunturi   | Pappa | 1999   | abc124");
     * huoltokirja.korvaaTailisaa(pyora2);
     * huoltokirja.getPyoria() === 1;
     * huoltokirja.annaPyora(0).anna(1) === "Hybridi";
     * Pyora pyora3 = new Pyora();
     * pyora3.parse(" 2 |  Citypyörä  |  Trek   | City | 2010   | poi567");
     * huoltokirja.korvaaTailisaa(pyora3);
     * huoltokirja.getPyoria() === 2;
     * huoltokirja.annaPyora(1).anna(1) === "Citypyörä";
     * </pre>
     */
    public void korvaaTailisaa(Pyora pyora) throws ApuException {
        pyorat.korvaaTaiLisaa(pyora);
    }
    
    
    /**
     * korvaa huollon tietorakenteessa.
     * @param huolto huolto jota muokataan
     * @throws ApuException jos huoltoa ei voida lisätä
     * TODO: Testit
     */
    public void korvaaTailisaa(Huolto huolto) throws ApuException {
        huollot.korvaaTaiLisaa(huolto);
    }
    
    
    /**
     * Lisää huollon huoltokirjaan
     * @param huolto huolto joka lisätään
     * @throws ApuException jos huoltoa ei voida lisätä
     * @example
     * <pre name="test">
     * #THROWS ApuException
     * Huoltokirja huoltokirja = new Huoltokirja();
     * huoltokirja.getHuoltoja() === 0;
     * Huolto huolto = new Huolto();
     * huoltokirja.lisaa(huolto);
     * huoltokirja.getHuoltoja() === 1;
     * </pre>
     */
    public void lisaa(Huolto huolto) throws ApuException {
        huollot.lisaa(huolto);
    }
    
    
    
    /**
     * Poistetaan pyörä huoltokirjasta
     * @param pyora poistettava pyörä
     * @example
     * <pre name="test">
     * #THROWS ApuException
     * Huoltokirja huoltokirja = new Huoltokirja();
     * huoltokirja.getPyoria() === 0;
     * Pyora pyora = new Pyora();
     * pyora.parse(" 1 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123");
     * huoltokirja.lisaa(pyora);
     * huoltokirja.getPyoria() === 1;
     * huoltokirja.poista(pyora);
     * huoltokirja.getPyoria() === 0;
     * </pre>
     */
    public void poista(Pyora pyora) {
        pyorat.poista(pyora.getTunnusNro());
        
    }
    
    
    /**
     * Poistetaan huolto huoltokirjasta.
     * @param huolto poistettava huolto
     * TODO:testit
     */
    public void poista(Huolto huolto) {
        huollot.poista(huolto.getTunnusNro());
        
    }
    
    
    /**
     * palauttaa pyörän, joka tallennettu tietorakenteen paikkaan i.
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
     * @throws IndexOutOfBoundsException poikkeus jos yritetään etsiä sellaisen pyörän huoltoja, jota ei ole lisätty huoltokirjaan.
     * @example
     * <pre name="test">
     * #THROWS ApuException
     * #THROWS IndexOutOfBoundsException
     * Huoltokirja huoltokirja = new Huoltokirja();
     * Pyora pyora = new Pyora();
     * pyora.parse(" 1 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123");
     * Huolto huolto1 = new Huolto(1);
     * huoltokirja.lisaa(huolto1);
     * huoltokirja.annaHuollot(pyora).get(0) == huolto1 === true;
     * huoltokirja.annaHuollot(pyora).get(1) == huolto1 === false; #THROWS IndexOutOfBoundsException  // varmistetaan että muita huoltoja ei ole.
     * Huolto huolto2 = new Huolto(1);
     * huoltokirja.lisaa(huolto2);
     * huoltokirja.annaHuollot(pyora).get(1) == huolto2 === true;
     * Pyora pyora2 = new Pyora();
     * pyora.parse(" 2 |  Hybridi  |  Tunturi   | ei tiedossa  | 1950   | afvgdg123");
     * huoltokirja.annaHuollot(pyora2).get(0) == null === true; #THROWS IndexOutOfBoundsException
     * </pre>
     */
    public List<Huolto> annaHuollot(Pyora pyora) throws IndexOutOfBoundsException {
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
     * Palauttaa hakuehdot täyttävät pyörät
     * @param ehto hakuehto
     * @param k kenttä jonka mukaan haetaan
     * @return tietorakenne jossa hakuehdon täyttävät pyörät tallennettuna
     */
    public Collection<Pyora> etsiPyorat(String ehto, int k) {
        return pyorat.etsi(ehto, k);
    }
    
    
    /**
     * Palauttaa hakuehdot täyttävät huollot
     * @param ehto hakuehto
     * @param k kenttä jonka mukaan haetaan
     * @param pyoranTunnus minkä pyörän huoltoja etsitään
     * @return tietorakenne jossa hakuehdon täyttävät huollot tallennettuna
     * TODO:testit katso annahuollot
     */
    public Collection<Huolto> etsiHuollot(String ehto, int k, int pyoranTunnus) {
        return huollot.etsi(ehto, k, pyoranTunnus);
    }
    
    
    /**
     * Palauttaa taulukon hinnoista
     * @param vuosi minkä vuoden hinnat haetaan
     * @return taulukko hinnoista
     * TODO:testit ja tarkemmat kuvaukset
     */
    public double[] annaHinnat(int vuosi) {
        return huollot.annaHinnat(vuosi);
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
