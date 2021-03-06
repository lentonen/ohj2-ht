package huoltokirja.test;
// Generated by ComTest BEGIN
import java.io.File;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.*;
import huoltokirja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.20 10:52:34 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class PyoratTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa48 
   * @throws ApuException when error
   */
  @Test
  public void testLisaa48() throws ApuException {    // Pyorat: 48
    Pyorat pyorat = new Pyorat(); 
    Pyora pyora1 = new Pyora(), pyora2 = new Pyora(); Pyora pyora3 = new Pyora(); Pyora pyora4 = new Pyora(); Pyora pyora5 = new Pyora(); 
    assertEquals("From: Pyorat line: 52", 0, pyorat.getLkm()); 
    pyorat.lisaa(pyora1); assertEquals("From: Pyorat line: 53", 1, pyorat.getLkm()); 
    pyorat.lisaa(pyora2); assertEquals("From: Pyorat line: 54", 2, pyorat.getLkm()); 
    pyorat.lisaa(pyora3); assertEquals("From: Pyorat line: 55", 3, pyorat.getLkm()); 
    assertEquals("From: Pyorat line: 56", pyora1, pyorat.anna(0)); 
    assertEquals("From: Pyorat line: 57", pyora2, pyorat.anna(1)); 
    assertEquals("From: Pyorat line: 58", pyora3, pyorat.anna(2)); 
    try {
    assertEquals("From: Pyorat line: 59", pyora3, pyorat.anna(3)); 
    fail("Pyorat: 59 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    try {
    assertEquals("From: Pyorat line: 60", pyora3, pyorat.anna(-1)); 
    fail("Pyorat: 60 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    pyorat.lisaa(pyora4); assertEquals("From: Pyorat line: 61", 4, pyorat.getLkm()); 
    pyorat.lisaa(pyora5); assertEquals("From: Pyorat line: 62", 5, pyorat.getLkm()); 
    pyorat.lisaa(pyora5);  // Voidaan lisätä 6.pyörä, koska tietorakenne kasvaa
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testKorvaaTaiLisaa84 */
  @Test
  public void testKorvaaTaiLisaa84() {    // Pyorat: 84
    Pyorat pyorat = new Pyorat(); 
    assertEquals("From: Pyorat line: 86", 0, pyorat.getLkm()); 
    Pyora pyora1 = new Pyora(); 
    pyora1.parse(" 1 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123"); 
    pyorat.lisaa(pyora1); 
    assertEquals("From: Pyorat line: 90", 1, pyorat.getLkm()); 
    Pyora pyora2 = new Pyora(); 
    pyora2.parse(" 1 |  Hybridi  |  Tunturi   | Pappa | 1999   | abc124"); 
    pyorat.korvaaTaiLisaa(pyora2); 
    assertEquals("From: Pyorat line: 94", 1, pyorat.getLkm()); 
    assertEquals("From: Pyorat line: 95", "Hybridi", pyorat.anna(0).anna(1)); 
    Pyora pyora3 = new Pyora(); 
    pyora3.parse(" 2 |  Citypyörä  |  Trek   | City | 2010   | poi567"); 
    pyorat.korvaaTaiLisaa(pyora3); 
    assertEquals("From: Pyorat line: 99", 2, pyorat.getLkm()); 
    assertEquals("From: Pyorat line: 100", "Citypyörä", pyorat.anna(1).anna(1)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testPoista121 */
  @Test
  public void testPoista121() {    // Pyorat: 121
    Pyorat pyorat = new Pyorat(); 
    assertEquals("From: Pyorat line: 123", 0, pyorat.getLkm()); 
    Pyora pyora = new Pyora(); 
    pyora.parse(" 4 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123"); 
    pyorat.lisaa(pyora); 
    assertEquals("From: Pyorat line: 127", 1, pyorat.getLkm()); 
    assertEquals("From: Pyorat line: 128", 0, pyorat.poista(1));  // pyörää ei löydy
    assertEquals("From: Pyorat line: 129", 1, pyorat.poista(4));  // pyörä löytyy
    assertEquals("From: Pyorat line: 130", 0, pyorat.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testEtsiPaikka150 */
  @Test
  public void testEtsiPaikka150() {    // Pyorat: 150
    Pyorat pyorat = new Pyorat(); 
    Pyora pyora1 = new Pyora(); 
    Pyora pyora2 = new Pyora(); 
    assertEquals("From: Pyorat line: 154", -1, pyorat.etsiPaikka(1)); 
    pyora1.parse(" 1 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123"); 
    pyora2.parse(" 2 |  Hybridi  |  Tunturi   | Pappa | 1999   | abc124"); 
    pyorat.lisaa(pyora1); pyorat.lisaa(pyora2); 
    assertEquals("From: Pyorat line: 158", 0, pyorat.etsiPaikka(1)); 
    assertEquals("From: Pyorat line: 159", 1, pyorat.etsiPaikka(2)); 
    assertEquals("From: Pyorat line: 160", -1, pyorat.etsiPaikka(3)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedosto223 
   * @throws ApuException when error
   */
  @Test
  public void testLueTiedosto223() throws ApuException {    // Pyorat: 223
    Pyorat pyorat = new Pyorat(); 
    Pyora pyora6 = new Pyora(); pyora6.arvoPyora(); pyorat.lisaa(pyora6); 
    Pyora pyora7 = new Pyora(); pyora7.arvoPyora(); pyorat.lisaa(pyora7); 
    String hakemisto = "testipyorat"; 
    String tiedNimi = hakemisto +"/pyorat.dat"; 
    File ftied = new File(tiedNimi); 
    ftied.getParentFile().mkdirs(); 
    ftied.delete(); 
    try {
    pyorat.lueTiedosto(hakemisto); 
    fail("Pyorat: 236 Did not throw ApuException");
    } catch(ApuException _e_){ _e_.getMessage(); }
    pyorat.tallenna(hakemisto); 
    pyorat = new Pyorat();  // Tehdään uusi pyorat-olio vanhan päälle
    assertEquals("From: Pyorat line: 239", 0, pyorat.getLkm());  // Uudessa ei pitäisi olla yhtäkään pyörää
    pyorat.lueTiedosto(hakemisto); 
    assertEquals("From: Pyorat line: 241", 2, pyorat.getLkm());  // Uudessa pitäisi nyt olla kaksi aiemmin lisättyä pyörää
    pyorat.anna(0).equals(pyora6); 
    pyorat.anna(1).equals(pyora7); 
    try {
    pyorat.anna(2).equals(pyora7); 
    fail("Pyorat: 244 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    assertEquals("From: Pyorat line: 245", true, ftied.delete());  // tuhoaa .dat-tiedoston
    assertEquals("From: Pyorat line: 246", true, ftied.getParentFile().delete());  // tuhoaa testikansion
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testEtsi277 */
  @Test
  public void testEtsi277() {    // Pyorat: 277
    Pyorat pyorat = new Pyorat(); 
    Pyora pyora = new Pyora(); 
    pyora.parse(" 4 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123"); 
    pyorat.lisaa(pyora); 
    Pyora pyora2 = new Pyora(); 
    pyora2.parse(" 5 |  Kaupunkipyörä  |  Helkama   | Jopo  | 2020   | fgh236"); 
    pyorat.lisaa(pyora2); 
    Pyora pyora3 = new Pyora(); 
    pyora3.parse(" 5 |  Gravel  |  Helkama   | CS2800  | 2010   | GR4321"); 
    pyorat.lisaa(pyora3); 
    List<Pyora> loydetyt = pyorat.etsi("*K*", 1); 
    assertEquals("From: Pyorat line: 290", 2, loydetyt.size()); 
    assertEquals("From: Pyorat line: 291", true, loydetyt.get(0) == pyora2); 
    assertEquals("From: Pyorat line: 292", true, loydetyt.get(1) == pyora); 
    loydetyt = pyorat.etsi("*Helkama*", 2); 
    assertEquals("From: Pyorat line: 294", 3, loydetyt.size()); 
    loydetyt = pyorat.etsi("*S*", 3); 
    assertEquals("From: Pyorat line: 296", 1, loydetyt.size()); 
    assertEquals("From: Pyorat line: 297", true, loydetyt.get(0) == pyora3); 
    loydetyt = pyorat.etsi("*20*", 4); 
    assertEquals("From: Pyorat line: 299", 3, loydetyt.size()); 
    assertEquals("From: Pyorat line: 300", true, loydetyt.get(0) == pyora); 
    assertEquals("From: Pyorat line: 301", true, loydetyt.get(1) == pyora3); 
    assertEquals("From: Pyorat line: 302", true, loydetyt.get(2) == pyora2); 
    loydetyt = pyorat.etsi("*1*", 5); 
    assertEquals("From: Pyorat line: 304", 2, loydetyt.size()); 
    assertEquals("From: Pyorat line: 305", true, loydetyt.get(0) == pyora); 
    assertEquals("From: Pyorat line: 306", true, loydetyt.get(1) == pyora3); 
  } // Generated by ComTest END
}