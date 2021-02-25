/**
 * 
 */
package huoltokirja;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Henri
 * @version 25.2.2021
 *
 */
public class Huollot implements Iterable<Huolto> {
    private String tiedostonNimi ="";
    private final Collection<Huolto> huollot= new ArrayList<Huolto>();
    
    
    /**
     * oletusmuodostaja
     */
    public Huollot() {
        //
    }
    
    
    /**
     * Lisätään huolto valmiiseen tietorakenteeseen
     * @param huolto lisättävä huolto
     */
    public void lisaa(Huolto huolto) {
        huollot.add(huolto);
    }
    
    
    /**
     * Lukee huoltojen tiedot tiedostosta. TODO: tee huoltojen luku loppuun
     * @param hakemisto kertoo missä tiedosto sijaitsee
     * @throws ApuException minkälainen virheilmoitus näytetään.
     */
    public void lueTiedosto(String hakemisto) throws ApuException {
        tiedostonNimi = hakemisto + "huollot.dat";
        throw new ApuException("Ei osata lukea vielä tiedostoa " +tiedostonNimi);
    }
    
    
    /**
     * Tallettaa huoltojen tiedot tiedostoon huollot.dat TODO: tee huoltojen tallettaminen loppuun
     * @throws ApuException minkälainen virheilmoitus näytetään
     */
    public void talleta() throws ApuException {
        //tiedostonNimi = hakemisto + "pyorat.dat"; TODO: tarviiko tätä jossakin vaiheessa?
        throw new ApuException("Ei osata tallentaa tiedostoon" +tiedostonNimi);
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
        
        List<Huolto> pyoranHuollot = huollot.annaHuollot(2);
        for (Huolto huolto : pyoranHuollot) {
            huolto.tulosta(System.out);
        }
        
    }
  
    
    @Override
    public Iterator<Huolto> iterator() {
        // TODO Auto-generated method stub
        return null;
    }
}
