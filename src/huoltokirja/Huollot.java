/**
 * 
 */
package huoltokirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Huollot-luokka yksittäisen pyörän huoltojen tallettamiseen.
 * @author Henri
 * @version 18.3.2021
 *
 */
public class Huollot implements Iterable<Huolto> {
    private final static String tiedostonNimi ="huollot/huollot.dat";       // Tiedosto johon huollot tallennetaan
    private final Collection<Huolto> huollot= new ArrayList<Huolto>();      // tietorakenne huoltojen tallentamiseen
    
    
    /**
     * oletusmuodostaja
     */
    public Huollot() {
        // Ei tarvita mitään vielä tässä vaiheessa
    }
    
    
    /**
     * Lisätään huolto valmiiseen tietorakenteeseen
     * @param huolto lisättävä huolto
     */
    public void lisaa(Huolto huolto) {
        huollot.add(huolto);
    }
    
    
    /**
     * Lukee huoltojen tiedot tiedostosta.
     * @throws ApuException virhe jos huoltojen lukeminen epäonnistuu
     * TODO:testit?
     */
    public void lueTiedosto() throws ApuException {
        //tiedostonNimi = hakemisto + "/huollot.dat";
        File ftied = new File(tiedostonNimi); 
        
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while (fi.hasNext()) {
                String s = "";
                s = fi.nextLine();
                Huolto huolto = new Huolto();
                huolto.parse(s); // voisi palauttaa onnistuuko parsiminen BOOL
                lisaa(huolto);
            }
        } catch (FileNotFoundException ex) {
            throw new ApuException("Ei saa luettua tiedostoa " +tiedostonNimi);
       // } catch (IOException e) {
       //     throw new ApuException("Ongelmia tiedoston kanssa " +ftied.getAbsolutePath());    
        }
    }
    
    
    /**
     * Tallettaa huoltojen tiedot tiedostoon huollot.dat
     * @throws ApuException jos tallennus epäonnistuu
     * @example
     * <pre>
     * hid |pid  |nimi                         |ajotunnit  |Toimenpiteet  
     * 1   |1    |Takaiskarin huolto           |100        |Puhdistus, O-renkaiden vaihto (servicekit123)   
     * 2   |1    |Takajarrupalojen vaihto      |120        |Shimano 123 - jarrupalat
     * 3   |2    |Takapak. ja ketjun vaihto    |200        |Shimano678 ja Shimano 987, Lisäksi voimansiirron puhdistus ja vaihteiston säätö.
     * </pre>
     * TODO:testit?
     */
    public void tallenna() throws ApuException {
        File ftied = new File(tiedostonNimi);
        
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
     * Palauttaa listan annetun pyörän huolloista. //TODO:testit
     * @param pyoraNro Pyörä jonka huoltoja etsitään
     * @return Viite listaan pyörän huolloista
     * @throws IndexOutOfBoundsException virhe jos yritetään etsiä sellaisen pyörän huoltoa, jota ei ole lisätty
     * TODO:testit
     */
    public List<Huolto> annaHuollot(int pyoraNro) throws IndexOutOfBoundsException {
        List<Huolto> loydetyt = new ArrayList<Huolto>();
        for (Huolto huolto: huollot)
            if (huolto.getPyoraNro() == pyoraNro) loydetyt.add(huolto);
        return loydetyt;
    }
    
    
    /**
     * Testataan huollot-luokan toimintaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Huollot huollot = new Huollot();
        
        // Luetaan aiemmin lisätyt huollot tiedostosta
        try {
            huollot.lueTiedosto();
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
            huollot.tallenna();
        } catch (ApuException e) {
            e.printStackTrace();
        }
    }
  
    
    @Override
    public Iterator<Huolto> iterator() {
        // TODO Auto-generated method stub
        return null;
    }
}
