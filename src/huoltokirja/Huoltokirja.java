package huoltokirja;
import java.util.List;

/**
 * Huoltokirja, joka sisältää pyöriä ja huoltoja. 
 * @author Henri Leinonen
 * @version 19.4.2021
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
     * @example
     * <pre name="test">
     * #THROWS ApuException
     * #import java.util.List;
     * Pyora pyora = new Pyora();
     * pyora.parse(" 1 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123");
     * Huoltokirja huoltokirja = new Huoltokirja();
     * huoltokirja.getHuoltoja() === 0;
     * Huolto huolto1 = new Huolto(1); 
     * huolto1.parse(" 1  |  1  |1.1.2019|50.00|  Iskari  | 100 | Öljynvaihto");
     * huoltokirja.lisaa(huolto1);
     * huoltokirja.getHuoltoja() === 1;
     * Huolto huolto2 = new Huolto(1); 
     * huolto2.parse(" 1  |  1  |1.2.2019|25.00|  Jarrut  | 150 | palat");
     * huoltokirja.korvaaTailisaa(huolto2);
     * huoltokirja.getHuoltoja() === 1;
     * List<Huolto> loydetyt = huoltokirja.annaHuollot(pyora);
     * loydetyt.get(0).getNimi() === "Jarrut";
     * Huolto huolto3 = new Huolto(1); 
     * huolto3.parse(" 2  |  1  |1.2.2019|25.00|  Jarrut  | 150 | palat");
     * huoltokirja.korvaaTailisaa(huolto3);
     * huoltokirja.getHuoltoja() === 2;
     * </pre>
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
     * @example
     * <pre name="test">
     * #THROWS ApuException
     * Huoltokirja huoltokirja = new Huoltokirja();
     * huoltokirja.getHuoltoja() === 0;
     * Huolto huolto1 = new Huolto(1); 
     * huolto1.parse(" 1  |  1  |1.1.2019|50.00|  Iskari  | 100 | Öljynvaihto");
     * huoltokirja.lisaa(huolto1);
     * huoltokirja.getHuoltoja() === 1;
     * Huolto huolto2 = new Huolto(1); 
     * huolto2.parse(" 2  |  2  |2.2.2019|150.00|  Vaihteisto  | 150 | Pakka");
     * huoltokirja.lisaa(huolto2);
     * huoltokirja.getHuoltoja() === 2;
     * huoltokirja.poista(huolto1);
     * huoltokirja.getHuoltoja() === 1;
     * huoltokirja.poista(huolto2);
     * huoltokirja.getHuoltoja() === 0;
     * </pre>
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
     * @example
     * <pre name="test">
     * #THROWS ApuException
     * #import java.util.List;
     * Huoltokirja huoltokirja = new Huoltokirja();
     * Pyora pyora = new Pyora();
     * pyora.parse(" 4 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123");
     * huoltokirja.lisaa(pyora);
     * Pyora pyora2 = new Pyora();
     * pyora2.parse(" 5 |  Kaupunkipyörä  |  Helkama   | Jopo  | 2020   | fgh236");
     * huoltokirja.lisaa(pyora2);
     * Pyora pyora3 = new Pyora();
     * pyora3.parse(" 5 |  Gravel  |  Helkama   | CS2800  | 2010   | GR4321");
     * huoltokirja.lisaa(pyora3);
     * List<Pyora> loydetyt = huoltokirja.etsiPyorat("*K*", 1);
     * loydetyt.size() === 2;
     * loydetyt.get(0) == pyora2 === true;
     * loydetyt.get(1) == pyora === true;
     * loydetyt = huoltokirja.etsiPyorat("*Helkama*", 2);
     * loydetyt.size() === 3;
     * loydetyt = huoltokirja.etsiPyorat("*S*", 3);
     * loydetyt.size() === 1;
     * loydetyt.get(0) == pyora3 === true;
     * loydetyt = huoltokirja.etsiPyorat("*20*", 4);
     * loydetyt.size() === 3;
     * loydetyt.get(0) == pyora === true;
     * loydetyt.get(1) == pyora3 === true;
     * loydetyt.get(2) == pyora2 === true;
     * loydetyt = huoltokirja.etsiPyorat("*1*", 5);
     * loydetyt.size() === 2;
     * loydetyt.get(0) == pyora === true;
     * loydetyt.get(1) == pyora3 === true;
     * </pre>
     */
    public List<Pyora> etsiPyorat(String ehto, int k) {
        return pyorat.etsi(ehto, k);
    }
    
    
    /**
     * Palauttaa hakuehdot täyttävät huollot
     * @param ehto hakuehto
     * @param k kenttä jonka mukaan haetaan
     * @param pyoranTunnus minkä pyörän huoltoja etsitään
     * @return tietorakenne jossa hakuehdon täyttävät huollot tallennettuna
     * @example
     * <pre name="test">
     * #THROWS ApuException
     * Huoltokirja huoltokirja = new Huoltokirja();
     * Huolto huolto = new Huolto();
     * huolto.parse(" 2  |  2  | 1.1.2019|200  | Iskari  | 100 | Öljynvaihto");
     * huoltokirja.lisaa(huolto);
     * Huolto huolto2 = new Huolto();
     * huolto2.parse(" 3  |  3  | 1.6.2020|300  | Jarru  | 120 | palat");
     * huoltokirja.lisaa(huolto2);
     * Huolto huolto3 = new Huolto();
     * huolto3.parse(" 3  |  2  | 1.12.2021|400  | Vaihteisto  | 140 | Pakka");
     * huoltokirja.lisaa(huolto3);
     * List<Huolto> loydetyt = huoltokirja.etsiHuollot("*2019*", 2, 2);
     * loydetyt.size() === 1;
     * loydetyt.get(0) == huolto === true;
     * loydetyt = huoltokirja.etsiHuollot("*300*", 3, 3);
     * loydetyt.size() === 1;
     * loydetyt.get(0) == huolto2 === true;
     * loydetyt = huoltokirja.etsiHuollot("*i*", 4, 2);
     * loydetyt.size() === 2;
     * loydetyt.get(0) == huolto === true;
     * loydetyt.get(1) == huolto3 === true;
     * </pre>
     */
    public List<Huolto> etsiHuollot(String ehto, int k, int pyoranTunnus) {
        return huollot.etsi(ehto, k, pyoranTunnus);
    }
    
    
    /**
     * Palauttaa taulukon tietyn vuoden hinnoista. Taulukossa hinnat kuukausittain. 0 = tammikuu, 11 = joulukuu.
     * @param vuosi minkä vuoden hinnat haetaan
     * @return taulukko hinnoista
     * @example
     * <pre name="test">
     * #THROWS ApuException
     * Huoltokirja huoltokirja = new Huoltokirja();
     * Huolto huolto = new Huolto();
     * huolto.parse(" 2  |  2  | 1.1.2020|200  | Iskari  | 100 | Öljynvaihto");
     * huoltokirja.lisaa(huolto);
     * Huolto huolto2 = new Huolto();
     * huolto2.parse(" 3  |  3  | 1.6.2020|300  | Jarru  | 100 | palat");
     * huoltokirja.lisaa(huolto2);
     * Huolto huolto3 = new Huolto();
     * huolto3.parse(" 4  |  4  | 1.12.2019|400  | Jarru  | 100 | palat");
     * huoltokirja.lisaa(huolto3);
     * double[] hinnat = huoltokirja.annaHinnat(2020);
     * hinnat[0] ~~~ 200;hinnat[1] ~~~ 0; hinnat[2] ~~~ 0; hinnat[3] ~~~ 0; hinnat[4] ~~~ 0;hinnat[5] ~~~ 300;
     * hinnat[6] ~~~ 0; hinnat[7] ~~~ 0; hinnat[8] ~~~ 0; hinnat[9] ~~~ 0; hinnat[10] ~~~ 0; hinnat[11] ~~~ 0;
     * hinnat = huoltokirja.annaHinnat(2019);
     * hinnat[0] ~~~ 0; hinnat[1] ~~~ 0; hinnat[2] ~~~ 0; hinnat[3] ~~~ 0; hinnat[4] ~~~ 0; hinnat[5] ~~~ 0;
     * hinnat[6] ~~~ 0; hinnat[7] ~~~ 0; hinnat[8] ~~~ 0; hinnat[9] ~~~ 0; hinnat[10] ~~~ 0; hinnat[11] ~~~ 400;
     * </pre>
     */
    public double[] annaHinnat(int vuosi) {
        return huollot.annaHinnat(vuosi);
    }
    
    
    /**
     * Palauttaa vuosiluvut, jolloin huoltoja on tehty
     * @return Vuosiluvut taulukossa merkkijonona
     * @example
     * <pre name="test">
     * #THROWS ApuException
     * Huoltokirja huoltokirja = new Huoltokirja();
     * Huolto huolto = new Huolto();
     * huolto.parse(" 2  |  2  | 1.1.2020|200  | Iskari  | 100 | Öljynvaihto");
     * huoltokirja.lisaa(huolto);
     * Huolto huolto2 = new Huolto();
     * huolto2.parse(" 3  |  3  | 1.6.2021|300  | Jarru  | 100 | palat");
     * huoltokirja.lisaa(huolto2);
     * Huolto huolto3 = new Huolto();
     * huolto3.parse(" 4  |  4  | 1.12.2018|400  | Jarru  | 100 | palat");
     * huoltokirja.lisaa(huolto3);
     * List<String> vuodet = huoltokirja.annaVuodet();
     * vuodet.size() === 3;
     * vuodet.get(0) ==="2018";
     * vuodet.get(1) ==="2020";
     * vuodet.get(2) ==="2021";
     * </pre>
     */
    public List<String> annaVuodet() {
        return huollot.annaVuodet();
        
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
