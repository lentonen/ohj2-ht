package huoltokirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Huollot-luokka yksittäisen pyörän huoltojen tallettamiseen.
 * @author Henri Leinonen
 * @version 20.4.2021
 *
 */
public class Huollot implements Iterable<Huolto> {
    private final static String tiedostonNimi ="/huollot.dat";        // Tiedosto johon huollot tallennetaan
    private final List<Huolto> huollot= new ArrayList<Huolto>();      // tietorakenne huoltojen tallentamiseen
    private boolean muutettu = false;                                 // Kertoo onko tietorakenteeseen tehty muutoksia
    
    
    /**
     * oletusmuodostaja
     */
    public Huollot() {
        // Ei tarvita mitään.
    }
    
    
    /**
     * Lisätään huolto valmiiseen tietorakenteeseen
     * @param huolto lisättävä huolto
     */
    public void lisaa(Huolto huolto) {
        huollot.add(huolto);
        muutettu = true;
    }
    
    
    /**
     * Lukee huoltojen tiedot tiedostosta.
     * @param hakemisto kansio josta tiedostoa yritetään lukea
     * @throws ApuException virhe jos huoltojen lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS ApuException
     * #THROWS IndexOutOfBoundsException 
     * #import java.io.File;
     * #import java.util.List;
     * Huollot huollot = new Huollot();
     * Huolto huolto1 = new Huolto(1); huolto1.arvoHuolto(); huollot.lisaa(huolto1);
     * Huolto huolto2 = new Huolto(2); huolto2.arvoHuolto(); huollot.lisaa(huolto2);
     * String hakemisto = "testihuollot";
     * String tiedNimi = hakemisto +"/huollot.dat";
     * File ftied = new File(tiedNimi);
     * ftied.getParentFile().mkdirs();
     * ftied.delete();
     * huollot.lueTiedosto(hakemisto); #THROWS ApuException
     * huollot.tallenna(hakemisto);
     * huollot = new Huollot();   // Tehdään uusi huollot-olio vanhan päälle
     * huollot.getLkm() === 0;    // Uudessa ei pitäisi olla yhtäkään huoltoa
     * huollot.lueTiedosto(hakemisto);
     * huollot.getLkm() === 2;    // Uudessa pitäisi nyt olla kaksi aiemmin lisättyä huoltoa
     * List<Huolto> eka = huollot.annaHuollot(1);
     * eka.get(0).equals(huolto1);
     * eka.get(1).equals(huolto1); #THROWS IndexOutOfBoundsException  // Pyörälle 1 on lisätty vain yksi huolto
     * List<Huolto> toka = huollot.annaHuollot(2);
     * toka.get(0).equals(huolto2);
     * toka.get(1).equals(huolto2); #THROWS IndexOutOfBoundsException  // Pyörälle 2 on lisätty vain yksi huolto
     * ftied.delete() === true;  // tuhoaa .dat-tiedoston
     * ftied.getParentFile().delete() === true;  // tuhoaa testikansion
     * </pre>
     */
    public void lueTiedosto(String hakemisto) throws ApuException {
        File ftied = new File(hakemisto + tiedostonNimi); 
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while (fi.hasNext()) {
                String s = "";
                s = fi.nextLine();
                Huolto huolto = new Huolto();
                huolto.parse(s); // voisi palauttaa onnistuuko parsiminen BOOL
                lisaa(huolto);
            }
            muutettu = false;
        } catch (FileNotFoundException ex) {
            throw new ApuException("Ei saa luettua tiedostoa " +tiedostonNimi);   
        }
    }
    
    
    /**
     * Tallettaa huoltojen tiedot tiedostoon huollot.dat
     * @param hakemisto kansio johon tiedosto tallennetaan
     * @throws ApuException jos tallennus epäonnistuu
     * @example
     * <pre>
     * hid |pid  |nimi                         |ajotunnit  |Toimenpiteet  
     * 1   |1    |Takaiskarin huolto           |100        |Puhdistus, O-renkaiden vaihto (servicekit123)   
     * 2   |1    |Takajarrupalojen vaihto      |120        |Shimano 123 - jarrupalat
     * 3   |2    |Takapak. ja ketjun vaihto    |200        |Shimano678 ja Shimano 987, Lisäksi voimansiirron puhdistus ja vaihteiston säätö.
     * </pre>
     * Testattu metodin lueTiedosto kanssa yhdessä.
     */
    public void tallenna(String hakemisto) throws ApuException {
        if (!muutettu) return;
        File ftied = new File(hakemisto + tiedostonNimi);
        ftied.getParentFile().mkdirs();     // Luo hakemistorakenteen, jos hakemistot puuttuvat
        
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for (Huolto huolto : huollot) {
                fo.println(huolto);
            }
        } catch (FileNotFoundException ex) {
            throw new ApuException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
        }
    }

     
    /**
     * Palauttaa tallennettujen huoltojen lukumäärän
     * @return huoltojen lukumäärä
     */
    public int getLkm() {
        return huollot.size();
    }
    
    
    /**
     * Palauttaa listan annetun pyörän huolloista.
     * @param pyoraNro Pyörä jonka huoltoja etsitään
     * @return viite listaan, johon tallennettu pyörän huollot
     * @throws IndexOutOfBoundsException virhe jos yritetään etsiä sellaisen pyörän huoltoa, jota ei ole lisätty
     * @example
     * <pre name="test">
     * Huollot huollot = new Huollot();
     * Huolto huolto1 = new Huolto(1); huolto1.arvoHuolto(); huollot.lisaa(huolto1);
     * huollot.annaHuollot(1).get(0) == huolto1 === true;
     * huollot.annaHuollot(1).get(1) == huolto1 === false; #THROWS IndexOutOfBoundsException
     * Huolto huolto2 = new Huolto(1); huolto2.arvoHuolto(); huollot.lisaa(huolto2);
     * huollot.annaHuollot(1).get(1) == huolto2 === true;
     * Huolto huolto3 = new Huolto(2); huolto3.arvoHuolto(); huollot.lisaa(huolto3);
     * huollot.annaHuollot(2).get(0) == huolto3 === true;
     * huollot.annaHuollot(3).get(0) == huolto3 === true; #THROWS IndexOutOfBoundsException
     * </pre>
     */
    public List<Huolto> annaHuollot(int pyoraNro) throws IndexOutOfBoundsException {
        List<Huolto> loydetyt = new ArrayList<Huolto>();
        for (Huolto huolto: huollot)
            if (huolto.getPyoraNro() == pyoraNro) loydetyt.add(huolto);
        return loydetyt;
    }
    
    
    /**
     * Korvaa (päivittää) huollon tietorakenteessa. Jos huoltoa ei löydy tietorakenteesta, niin se lisätään.
     * @param huolto jonka tietoja päivitetään
     * @example
     * <pre name="test">
     * Huollot huollot = new Huollot();
     * Huolto huolto1 = new Huolto(1); 
     * huolto1.parse(" 1  |  1  |1.1.2019|50.00|  Iskari  | 100 | Öljynvaihto");
     * huollot.lisaa(huolto1);
     * Huolto huolto2 = new Huolto(1); 
     * huolto2.parse(" 2  |  1  |1.2.2019|25.00|  Jarrut  | 150 | palat");
     * huollot.lisaa(huolto2);
     * huolto1.getTunnusNro() === 1;  
     * huolto2.getTunnusNro() === 2;
     * huollot.annaHuollot(1).get(0) == huolto1 === true;
     * huollot.annaHuollot(1).get(1) == huolto2 === true;
     * Huolto huolto3 = new Huolto();
     * huolto3.parse(" 1  |  1  |1.1.2019|51.00|  Iskari  | 100 | Öljynvaihto");
     * huolto3.getNimi() === "Iskari";
     * huollot.korvaaTaiLisaa(huolto3);
     * huollot.annaHuollot(1).get(0) == huolto3 === true;
     * huollot.annaHuollot(1).get(0).getNimi() === "Iskari";
     * Huolto huolto4 = new Huolto();
     * huolto4.parse(" 3  |  1  |1.3.2019|150.00|  Satulatolppa | 175 | Tiivisteet");
     * huollot.korvaaTaiLisaa(huolto4);
     * huollot.annaHuollot(1).get(2) == huolto4 === true;
     * </pre>
     */
    public void korvaaTaiLisaa(Huolto huolto) {
        int id = huolto.getTunnusNro();
        for (int i = 0; i < getLkm(); i++) {
            if (huollot.get(i).getTunnusNro() == id) {
                huollot.set(i, huolto);      // jos löydetään id:tä vastaava pyörä, niin korvataan se tuodulla pyörällä.
                muutettu = true;
                return;                
            }
        }
        lisaa(huolto);
    }
    
    
    /**
     * Poistetaan huolto
     * @param tunnusNro poistettavan huollon tunnusnumero.
     * @return 0 jos huoltoa ei löydy, 1 jos huolto löydetään ja poistettiin
     * @example
     * <pre name="test">
     * Huollot huollot = new Huollot();
     * Huolto huolto1 = new Huolto(1); 
     * huolto1.parse(" 1  |  1  |1.1.2019|50.00|  Iskari  | 100 | Öljynvaihto");
     * huollot.lisaa(huolto1);
     * Huolto huolto2 = new Huolto(1); 
     * huolto2.parse(" 2  |  2  |1.2.2019|25.00|  Jarrut  | 150 | palat");
     * huollot.lisaa(huolto2);
     * Huolto huolto3 = new Huolto();
     * huolto3.parse(" 3  |  3  |3.3.2019|500.00|  Iskari  | 200 | Kaikki");
     * huollot.lisaa(huolto3);
     * huollot.getLkm() === 3;
     * huollot.poista(4) === 0;
     * huollot.getLkm() === 3;
     * huollot.poista(3) === 1;
     * huollot.getLkm() === 2;
     * huollot.poista(2) === 1;
     * huollot.getLkm() === 1;
     * huollot.poista(1) === 1;
     * huollot.getLkm() === 0;
     * </pre>
     */
    public int poista(int tunnusNro) {
        for (Huolto huolto : huollot) {
            if (huolto.getTunnusNro() == tunnusNro) {
                huollot.remove(huolto);
                muutettu = true;
                return 1;
            }
        }
        return 0;  
    }


    /**
     * Etsi hakuehdot täyttävät huollot ja palauttaa ne.
     * @param ehto jolla huoltoja etsitään
     * @param k kenttä jonka mukaan etsitään
     * @param pyoranTunnus minkä pyörän huoltoja etsitään
     * @return hakuehdot täyttävät huollot
     * @example
     * <pre name="test">
     * Huollot huollot = new Huollot();
     * Huolto huolto = new Huolto();
     * huolto.parse(" 2  |  2  | 1.1.2019|200  | Iskari  | 100 | Öljynvaihto");
     * huollot.lisaa(huolto);
     * Huolto huolto2 = new Huolto();
     * huolto2.parse(" 3  |  3  | 1.6.2020|300  | Jarru  | 120 | palat");
     * huollot.lisaa(huolto2);
     * Huolto huolto3 = new Huolto();
     * huolto3.parse(" 3  |  2  | 1.12.2021|400  | Vaihteisto  | 140 | Pakka");
     * huollot.lisaa(huolto3);
     * List<Huolto> loydetyt = huollot.etsi("*2019*", 2, 2);
     * loydetyt.size() === 1;
     * loydetyt.get(0) == huolto === true;
     * loydetyt = huollot.etsi("*300*", 3, 3);
     * loydetyt.size() === 1;
     * loydetyt.get(0) == huolto2 === true;
     * loydetyt = huollot.etsi("*i*", 4, 2);
     * loydetyt.size() === 2;
     * loydetyt.get(0) == huolto === true;
     * loydetyt.get(1) == huolto3 === true;
     * </pre>
     */
    public List<Huolto> etsi(String ehto, int k, int pyoranTunnus) {
        List<Huolto> loydetyt = new ArrayList<Huolto>();
        for (int i = 0; i < getLkm(); i++) {
            if (Apulaskut.onkoSamat(huollot.get(i).anna(k), ehto) && huollot.get(i).getPyoraNro() == pyoranTunnus ) {
                loydetyt.add(huollot.get(i));
            }
        }
        Collections.sort(loydetyt, new Huolto.Vertailija(k));
        return loydetyt;
    }


    /**
     * Laskee huoltojen yhteenlasketut hinnat kuukausittain taulukkoon.
     * @param vuosi minkä vuoden hinnat haetaan
     * @return taulukollinen hintoja
     * @example
     * <pre name="test">
     * Huollot huollot = new Huollot();
     * Huolto huolto = new Huolto();
     * huolto.parse(" 2  |  2  | 1.1.2020|200  | Iskari  | 100 | Öljynvaihto");
     * huollot.lisaa(huolto);
     * Huolto huolto2 = new Huolto();
     * huolto2.parse(" 3  |  3  | 1.6.2020|300  | Jarru  | 100 | palat");
     * huollot.lisaa(huolto2);
     * Huolto huolto3 = new Huolto();
     * huolto3.parse(" 4  |  4  | 1.12.2019|400  | Jarru  | 100 | palat");
     * huollot.lisaa(huolto3);
     * double[] hinnat = huollot.annaHinnat(2020);
     * hinnat[0] ~~~ 200;
     * hinnat[5] ~~~ 300;
     * hinnat[11] ~~~ 0;
     * hinnat = huollot.annaHinnat(2019);
     * hinnat[11] ~~~ 400;
     * </pre>
     */
    public double[] annaHinnat(int vuosi) {
        double[] hinnat = new double[12];
        for (Huolto huolto: huollot) {
            String pvm = huolto.anna(2);
            String kk = pvm.substring(pvm.indexOf('.')+1, pvm.lastIndexOf('.'));
            String vv = pvm.substring(pvm.lastIndexOf('.')+1);
            if (Integer.parseInt(vv) == vuosi) {
                try {
                    int kuukausi = Integer.parseInt(kk);
                    double hinta = Double.parseDouble(huolto.anna(3)); 
                    hinnat[kuukausi-1] += hinta;
                } catch (NumberFormatException e) {
                    // Jos ei pystytä parsimaan, ei tehdä mitään ja siirrytään seuraavaan huoltoon
                }
            }
        }
        return hinnat;  
    }


    /**
     * Palauttaa vuosiluvut, jolloin huoltoja on tehty
     * @return Vuosiluvut listassa merkkijonoina
     * @example
     * <pre name="test">
     * #THROWS IndexOutOfBoundsException
     * Huollot huollot = new Huollot();
     * Huolto huolto = new Huolto();
     * huolto.parse(" 2  |  2  | 1.1.2020|200  | Iskari  | 100 | Öljynvaihto");
     * huollot.lisaa(huolto);
     * huollot.annaVuodet().get(0) === "2020";
     * huollot.annaVuodet().get(1) ===""; #THROWS IndexOutOfBoundsException
     * Huolto huolto2 = new Huolto();
     * huolto2.parse(" 3  |  3  | 1.1.2021|200  | Jarru  | 100 | palat");
     * huollot.lisaa(huolto2);
     * huollot.annaVuodet().get(1) === "2021";
     * Huolto huolto3 = new Huolto();
     * huolto3.parse(" 4  |  4  | 1.1.2019|200  | Jarru  | 100 | palat");
     * huollot.lisaa(huolto3);
     * huollot.annaVuodet().get(0) === "2019";
     * huollot.annaVuodet().get(1) === "2020";
     * huollot.annaVuodet().get(2) === "2021";
     * </pre>
     */
    public List<String> annaVuodet() {
        List<String> vuodet = new ArrayList<String>();
        for (Huolto huolto : huollot) {
            String pvm = huolto.anna(2);
            String vv = pvm.substring(pvm.lastIndexOf('.')+1);
            if (!vuodet.contains(vv)) vuodet.add(vv); 
        }
        Collections.sort(vuodet);
        return vuodet;
    }
    
    
    @Override
    public Iterator<Huolto> iterator() {
        return null;  // Ei tarvita tällä hetkellä, koska käytetään listaa huoltojen tallentamiseen.
    }
    
    
    /**
     * Testataan huollot-luokan toimintaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Huollot huollot = new Huollot();
        
        // Luetaan aiemmin lisätyt huollot tiedostosta
        try {
            huollot.lueTiedosto("huollot");
        } catch (ApuException ex) {
            System.err.println(ex.getMessage());
        }
        
        // Arvotaan huoltoja pyörälle 1
        Huolto huolto1 = new Huolto(1); huolto1.arvoHuolto(); huolto1.rekisteroi(); 
        Huolto huolto2 = new Huolto(1); huolto2.arvoHuolto(); huolto2.rekisteroi(); 
        
        // Arvotaan huoltoja pyörälle 2
        Huolto huolto3 = new Huolto(2); huolto3.arvoHuolto(); huolto3.rekisteroi();     
        Huolto huolto4 = new Huolto(2); huolto4.arvoHuolto(); huolto4.rekisteroi(); 
        Huolto huolto5 = new Huolto(2); huolto5.arvoHuolto(); huolto5.rekisteroi(); 
        
        // Lisätään arvotut huollot
        huollot.lisaa(huolto1);
        huollot.lisaa(huolto2);
        huollot.lisaa(huolto3);
        huollot.lisaa(huolto4);
        huollot.lisaa(huolto5);
        
        // Tulostetaan pyörän 1 / 2 huollot
        int i = 2; // minkä pyörän huollot tulostetaan
        System.out.println("Pyörän " +i +" huollot:\n");
        List<Huolto> pyoranHuollot = huollot.annaHuollot(i);
        for (Huolto huolto : pyoranHuollot) {
            huolto.tulosta(System.out);
        }
        
        try {
            huollot.tallenna("huollot");
        } catch (ApuException e) {
            e.printStackTrace();
        }
    }
}
