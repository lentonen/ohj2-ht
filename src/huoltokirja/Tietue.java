package huoltokirja;

/**
 * @author Henri Leinonen
 * @version 31.3.2021
 *
 */
public interface Tietue {
    
    
    /** 
     * @return Tietueen kenttien lukumäärä
     * @example
     * <pre name="test">
     * #import huoltokirja.Pyora;
     * Pyora pyora = new Pyora();
     * pyora.getKenttia() === 6;
     * </pre>
     */
    public abstract int getKenttia();
    
    
    /**
     * @return Ensimmäisen muokattavat kentän indeksi
     * <pre name="test">
     * Pyora pyora = new Pyora();
     * pyora.ekaKentta() === 1;
     * Huolto huolto = new Huolto();
     * huolto.ekaKentta() === 2;
     * </pre>
     */
    public abstract int ekaKentta();
    
    
    /**
     * @return ensimmäisen ison (textArea) muokattavan kentän indeksi 
     * @example
     * <pre name="test">
     * Pyora pyora = new Pyora();
     * pyora.ekaIsoKentta() === 7;
     * Huolto huolto = new Huolto();
     * huolto.ekaIsoKentta() === 6;
     * </pre>
     */
    public abstract int ekaIsoKentta();
    
    
    /**
     * Antaa kentän k nimen
     * @param k monennenko kentän nimi annetaan
     * @return kentän nimi
     * @example
     * <pre name="test">
     * Pyora pyora = new Pyora();
     * pyora.getKentanNimi(1) === "Nimi";
     * pyora.getKentanNimi(2) === "Merkki";
     * pyora.getKentanNimi(3) === "Malli";
     * pyora.getKentanNimi(4) === "Vuosimalli";
     * pyora.getKentanNimi(5) === "Runkonumero";
     * Huolto huolto = new Huolto();
     * huolto.getKentanNimi(2) === "Päivämäärä";
     * huolto.getKentanNimi(3) === "Hinta";
     * huolto.getKentanNimi(4) === "Nimi";
     * huolto.getKentanNimi(5) === "Ajotunnit";
     * huolto.getKentanNimi(6) === "Toimenpiteet";
     * 
     * </pre>
     */
    public abstract String getKentanNimi(int k) ;
    
    
    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k minkä kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     * @example
     * <pre name="test">
     * Pyora pyora = new Pyora();
     * pyora.parse(" 3 |  Fuji Rakan  |  Fuji   | Rakan  | 2000   | abc123");
     * pyora.anna(1) === "Fuji Rakan";
     * pyora.anna(2) === "Fuji";
     * pyora.anna(3) === "Rakan";
     * pyora.anna(4) === "2000";
     * pyora.anna(5) === "abc123";
     * Huolto huolto = new Huolto();
     * huolto.parse(" 2  |  2  |1.1.2020|150|  Iskari  | 100 | Öljynvaihto");
     * huolto.anna(2) === "1.1.2020";
     * huolto.anna(3) === "150.0";
     * huolto.anna(4) === "Iskari";
     * huolto.anna(5) === "100";
     * huolto.anna(6) === "Öljynvaihto";
     * </pre>
     */
    public abstract String anna(int k);
    
    
    /**
     * asettaa kentän k arvoksi parametrina tuodun merkkijonon arvon
     * @param k kenttä jonka arvo asetetaan
     * @param jono josta kentän sisältö luetaan
     * @return null jos asettaminen onnistuu, virheilmoituksen jos asettaminen epäonnistuu
     * @example
     * <pre name="test">
     * Pyora pyora = new Pyora();
     * pyora.aseta(1, "hybridi") === null;
     * pyora.anna(1) === "hybridi";
     * pyora.aseta(4, "2005s") === "vuosimalli on väärin";
     * pyora.anna(4) === "0";
     * </pre>
     */
    public abstract String aseta(int k, String jono);
    
    
    /**
     * Luo kloonin tietueesta
     * @return kloonattu tietue
     * @throws CloneNotSupportedException jos kloonausta ei tueta
     * Pyora pyora1 = new Pyora();
     * pyora1.parse(" 4 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123");
     * pyora2 = pyora1.clone();
     * pyora2.toString() === "4|Kottero|Helkama|Jopo|2000|abc123";
     * pyora1.toString() === "4|Kottero|Helkama|Jopo|2000|abc123";
     * pyora2.parse" 1 |  Maastopyörä  |  Fuji   | Rakan  | 2019   | kjh543");
     * pyora1.toString() === "4|Kottero|Helkama|Jopo|2000|abc123";
     * pyora2.toString() === "1|Maastopyörä|Fuji|Rakan|2019|kjh543";
     */
    public abstract Tietue clone() throws CloneNotSupportedException;
    
    
    /**
     * Palauttaa tietueen tiedot merkkijonona.
     * @return tietue merkkijonona
     * @example
     * <pre name="test">
     * Pyora pyora2 = new Pyora();
     * pyora2.parse(" 2 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123");
     * pyora2.toString() === "2|Kottero|Helkama|Jopo|2000|abc123"
     * </pre>
     */
    @Override
    public String toString();
    

}
