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
 * @version 2021.04.18 21:39:01 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class HuollotTest {



  // Generated by ComTest BEGIN
  /** 
   * testLueTiedosto49 
   * @throws ApuException when error
   */
  @Test
  public void testLueTiedosto49() throws ApuException {    // Huollot: 49
    Huollot huollot = new Huollot(); 
    Huolto huolto1 = new Huolto(1); huolto1.arvoHuolto(); huolto1.rekisteroi(); huollot.lisaa(huolto1); 
    Huolto huolto2 = new Huolto(2); huolto2.arvoHuolto(); huolto2.rekisteroi(); huollot.lisaa(huolto2); 
    String hakemisto = "testihuollot"; 
    String tiedNimi = hakemisto +"/huollot.dat"; 
    File ftied = new File(tiedNimi); 
    ftied.getParentFile().mkdirs(); 
    ftied.delete(); 
    try {
    huollot.lueTiedosto(hakemisto); 
    fail("Huollot: 63 Did not throw ApuException");
    } catch(ApuException _e_){ _e_.getMessage(); }
    huollot.tallenna(hakemisto); 
    huollot = new Huollot();  // Tehdään uusi huollot-olio vanhan päälle
    assertEquals("From: Huollot line: 66", 0, huollot.getLkm());  // Uudessa ei pitäisi olla yhtäkään huoltoa
    huollot.lueTiedosto(hakemisto); 
    assertEquals("From: Huollot line: 68", 2, huollot.getLkm());  // Uudessa pitäisi nyt olla kaksi aiemmin lisättyä huoltoa
    List<Huolto> eka = huollot.annaHuollot(1); 
    eka.get(0).equals(huolto1); 
    try {
    eka.get(1).equals(huolto1); 
    fail("Huollot: 71 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); } // Pyörälle 1 on lisätty vain yksi huolto
    List<Huolto> toka = huollot.annaHuollot(2); 
    toka.get(0).equals(huolto2); 
    try {
    toka.get(1).equals(huolto2); 
    fail("Huollot: 74 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); } // Pyörälle 2 on lisätty vain yksi huolto
    assertEquals("From: Huollot line: 75", true, ftied.delete());  // tuhoaa .dat-tiedoston
    assertEquals("From: Huollot line: 76", true, ftied.getParentFile().delete());  // tuhoaa testikansion
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaHuollot143 */
  @Test
  public void testAnnaHuollot143() {    // Huollot: 143
    Huollot huollot = new Huollot(); 
    Huolto huolto1 = new Huolto(1); huolto1.arvoHuolto(); huolto1.rekisteroi(); huollot.lisaa(huolto1); 
    assertEquals("From: Huollot line: 146", true, huollot.annaHuollot(1).get(0) == huolto1); 
    try {
    assertEquals("From: Huollot line: 147", false, huollot.annaHuollot(1).get(1) == huolto1); 
    fail("Huollot: 147 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    Huolto huolto2 = new Huolto(1); huolto2.arvoHuolto(); huolto2.rekisteroi(); huollot.lisaa(huolto2); 
    assertEquals("From: Huollot line: 149", true, huollot.annaHuollot(1).get(1) == huolto2); 
    Huolto huolto3 = new Huolto(2); huolto3.arvoHuolto(); huolto3.rekisteroi(); huollot.lisaa(huolto3); 
    assertEquals("From: Huollot line: 151", true, huollot.annaHuollot(2).get(0) == huolto3); 
    try {
    assertEquals("From: Huollot line: 152", true, huollot.annaHuollot(3).get(0) == huolto3); 
    fail("Huollot: 152 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testKorvaaTaiLisaa167 */
  @Test
  public void testKorvaaTaiLisaa167() {    // Huollot: 167
    Huollot huollot = new Huollot(); 
    Huolto huolto1 = new Huolto(1); 
    huolto1.parse(" 1  |  1  |1.1.2019|50.00|  Iskari  | 100 | Öljynvaihto"); 
    huollot.lisaa(huolto1); 
    Huolto huolto2 = new Huolto(1); 
    huolto2.parse(" 2  |  1  |1.2.2019|25.00|  Jarrut  | 150 | palat"); 
    huollot.lisaa(huolto2); 
    assertEquals("From: Huollot line: 175", 1, huolto1.getTunnusNro()); 
    assertEquals("From: Huollot line: 176", 2, huolto2.getTunnusNro()); 
    assertEquals("From: Huollot line: 177", true, huollot.annaHuollot(1).get(0) == huolto1); 
    assertEquals("From: Huollot line: 178", true, huollot.annaHuollot(1).get(1) == huolto2); 
    Huolto huolto3 = new Huolto(); 
    huolto3.parse(" 1  |  1  |1.1.2019|51.00|  Iskari  | 100 | Öljynvaihto"); 
    assertEquals("From: Huollot line: 181", "Iskari", huolto3.getNimi()); 
    huollot.korvaaTaiLisaa(huolto3); 
    assertEquals("From: Huollot line: 183", true, huollot.annaHuollot(1).get(0) == huolto3); 
    assertEquals("From: Huollot line: 184", "Iskari", huollot.annaHuollot(1).get(0).getNimi()); 
    Huolto huolto4 = new Huolto(); 
    huolto4.parse(" 3  |  1  |1.3.2019|150.00|  Satulatolppa | 175 | Tiivisteet"); 
    huollot.korvaaTaiLisaa(huolto4); 
    assertEquals("From: Huollot line: 188", true, huollot.annaHuollot(1).get(2) == huolto4); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testEtsi281 */
  @Test
  public void testEtsi281() {    // Huollot: 281
    Huollot huollot = new Huollot(); 
    Huolto huolto = new Huolto(); 
    huolto.parse(" 2  |  2  | 1.1.2019|200  | Iskari  | 100 | Öljynvaihto"); 
    huollot.lisaa(huolto); 
    Huolto huolto2 = new Huolto(); 
    huolto2.parse(" 3  |  3  | 1.6.2020|300  | Jarru  | 120 | palat"); 
    huollot.lisaa(huolto2); 
    Huolto huolto3 = new Huolto(); 
    huolto3.parse(" 3  |  2  | 1.12.2021|400  | Vaihteisto  | 140 | Pakka"); 
    huollot.lisaa(huolto3); 
    List<Huolto> loydetyt = huollot.etsi("*2019*", 2, 2); 
    assertEquals("From: Huollot line: 293", 1, loydetyt.size()); 
    assertEquals("From: Huollot line: 294", true, loydetyt.get(0) == huolto); 
    loydetyt = huollot.etsi("*300*", 3, 3); 
    assertEquals("From: Huollot line: 296", 1, loydetyt.size()); 
    assertEquals("From: Huollot line: 297", true, loydetyt.get(0) == huolto2); 
    loydetyt = huollot.etsi("*i*", 4, 2); 
    assertEquals("From: Huollot line: 299", 2, loydetyt.size()); 
    assertEquals("From: Huollot line: 300", true, loydetyt.get(0) == huolto); 
    assertEquals("From: Huollot line: 301", true, loydetyt.get(1) == huolto3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaHinnat321 */
  @Test
  public void testAnnaHinnat321() {    // Huollot: 321
    Huollot huollot = new Huollot(); 
    Huolto huolto = new Huolto(); 
    huolto.parse(" 2  |  2  | 1.1.2020|200  | Iskari  | 100 | Öljynvaihto"); 
    huollot.lisaa(huolto); 
    Huolto huolto2 = new Huolto(); 
    huolto2.parse(" 3  |  3  | 1.6.2020|300  | Jarru  | 100 | palat"); 
    huollot.lisaa(huolto2); 
    Huolto huolto3 = new Huolto(); 
    huolto3.parse(" 4  |  4  | 1.12.2019|400  | Jarru  | 100 | palat"); 
    huollot.lisaa(huolto3); 
    double[] hinnat = huollot.annaHinnat(2020); 
    assertEquals("From: Huollot line: 333", 200, hinnat[0], 0.000001); 
    assertEquals("From: Huollot line: 334", 300, hinnat[5], 0.000001); 
    assertEquals("From: Huollot line: 335", 0, hinnat[11], 0.000001); 
    hinnat = huollot.annaHinnat(2019); 
    assertEquals("From: Huollot line: 337", 400, hinnat[11], 0.000001); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testAnnaVuodet364 
   * @throws IndexOutOfBoundsException when error
   */
  @Test
  public void testAnnaVuodet364() throws IndexOutOfBoundsException {    // Huollot: 364
    Huollot huollot = new Huollot(); 
    Huolto huolto = new Huolto(); 
    huolto.parse(" 2  |  2  | 1.1.2020|200  | Iskari  | 100 | Öljynvaihto"); 
    huollot.lisaa(huolto); 
    assertEquals("From: Huollot line: 370", "2020", huollot.annaVuodet().get(0)); 
    try {
    assertEquals("From: Huollot line: 371", "", huollot.annaVuodet().get(1)); 
    fail("Huollot: 371 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    Huolto huolto2 = new Huolto(); 
    huolto2.parse(" 3  |  3  | 1.1.2021|200  | Jarru  | 100 | palat"); 
    huollot.lisaa(huolto2); 
    assertEquals("From: Huollot line: 375", "2021", huollot.annaVuodet().get(1)); 
    Huolto huolto3 = new Huolto(); 
    huolto3.parse(" 4  |  4  | 1.1.2019|200  | Jarru  | 100 | palat"); 
    huollot.lisaa(huolto3); 
    assertEquals("From: Huollot line: 379", "2019", huollot.annaVuodet().get(0)); 
    assertEquals("From: Huollot line: 380", "2020", huollot.annaVuodet().get(1)); 
    assertEquals("From: Huollot line: 381", "2021", huollot.annaVuodet().get(2)); 
  } // Generated by ComTest END
}