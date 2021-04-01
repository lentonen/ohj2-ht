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
     * </pre>
     */
    public abstract int ekaKentta();
    
    
    /**
     * @return ensimmäisen ison (textArea) muokattavan kentän indeksi 
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
     * </pre>
     */
    public abstract String getKentanNimi(int k) ;
    
    
    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k minkä kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     * TODO:testit
     */
    public abstract String anna(int k);
    
    
    /**
     * asettaa kentän k arvoksi parametrina tuodun merkkijonon arvon
     * @param k kenttä jonka arvo asetetaan
     * @param jono josta kentän sisältö luetaan
     * @return null jos asettaminen onnistuu, virheilmoituksen jos asettaminen epäonnistuu
     * TODO:testit
     */
    public abstract String aseta(int k, String jono);
    
    
    /**
     * Luo kloonin tietueesta
     * @return kloonattu tietue
     * @throws CloneNotSupportedException jos kloonausta ei tueta
     * TODO: testit
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
