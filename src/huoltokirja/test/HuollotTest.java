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
 * @version 2021.04.08 20:21:04 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class HuollotTest {



  // Generated by ComTest BEGIN
  /** 
   * testLueTiedosto48 
   * @throws ApuException when error
   */
  @Test
  public void testLueTiedosto48() throws ApuException {    // Huollot: 48
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
    fail("Huollot: 62 Did not throw ApuException");
    } catch(ApuException _e_){ _e_.getMessage(); }
    huollot.tallenna(hakemisto); 
    huollot = new Huollot();  // Tehdään uusi huollot-olio vanhan päälle
    assertEquals("From: Huollot line: 65", 0, huollot.getLkm());  // Uudessa ei pitäisi olla yhtäkään huoltoa
    huollot.lueTiedosto(hakemisto); 
    assertEquals("From: Huollot line: 67", 2, huollot.getLkm());  // Uudessa pitäisi nyt olla kaksi aiemmin lisättyä huoltoa
    List<Huolto> eka = huollot.annaHuollot(1); 
    eka.get(0).equals(huolto1); 
    try {
    eka.get(1).equals(huolto1); 
    fail("Huollot: 70 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); } // Pyörälle 1 on lisätty vain yksi huolto
    List<Huolto> toka = huollot.annaHuollot(2); 
    toka.get(0).equals(huolto2); 
    try {
    toka.get(1).equals(huolto2); 
    fail("Huollot: 73 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); } // Pyörälle 2 on lisätty vain yksi huolto
    assertEquals("From: Huollot line: 74", true, ftied.delete());  // tuhoaa .dat-tiedoston
    assertEquals("From: Huollot line: 75", true, ftied.getParentFile().delete());  // tuhoaa testikansion
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaHuollot142 */
  @Test
  public void testAnnaHuollot142() {    // Huollot: 142
    Huollot huollot = new Huollot(); 
    Huolto huolto1 = new Huolto(1); huolto1.arvoHuolto(); huolto1.rekisteroi(); huollot.lisaa(huolto1); 
    assertEquals("From: Huollot line: 145", true, huollot.annaHuollot(1).get(0) == huolto1); 
    try {
    assertEquals("From: Huollot line: 146", false, huollot.annaHuollot(1).get(1) == huolto1); 
    fail("Huollot: 146 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    Huolto huolto2 = new Huolto(1); huolto2.arvoHuolto(); huolto2.rekisteroi(); huollot.lisaa(huolto2); 
    assertEquals("From: Huollot line: 148", true, huollot.annaHuollot(1).get(1) == huolto2); 
    Huolto huolto3 = new Huolto(2); huolto3.arvoHuolto(); huolto3.rekisteroi(); huollot.lisaa(huolto3); 
    assertEquals("From: Huollot line: 150", true, huollot.annaHuollot(2).get(0) == huolto3); 
    try {
    assertEquals("From: Huollot line: 151", true, huollot.annaHuollot(3).get(0) == huolto3); 
    fail("Huollot: 151 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testKorvaaTaiLisaa166 */
  @Test
  public void testKorvaaTaiLisaa166() {    // Huollot: 166
    Huollot huollot = new Huollot(); 
    Huolto huolto1 = new Huolto(1); huolto1.rekisteroi(); huollot.lisaa(huolto1); 
    Huolto huolto2 = new Huolto(1); huolto2.rekisteroi(); huollot.lisaa(huolto2); 
    assertEquals("From: Huollot line: 170", 3, huolto1.getTunnusNro());  // 3 koska samassa testiluokassa aiemmin lisätty 2 pyörää.
    assertEquals("From: Huollot line: 171", 4, huolto2.getTunnusNro()); 
    assertEquals("From: Huollot line: 172", true, huollot.annaHuollot(1).get(0) == huolto1); 
    assertEquals("From: Huollot line: 173", true, huollot.annaHuollot(1).get(1) == huolto2); 
    Huolto huolto3 = new Huolto(); 
    huolto3.parse(" 3  |  1  |  Iskari  | 100 | Öljynvaihto"); 
    assertEquals("From: Huollot line: 176", "Iskari", huolto3.getNimi()); 
    huollot.korvaaTaiLisaa(huolto3); 
    assertEquals("From: Huollot line: 178", true, huollot.annaHuollot(1).get(0) == huolto3); 
    assertEquals("From: Huollot line: 179", "Iskari", huollot.annaHuollot(1).get(0).getNimi()); 
  } // Generated by ComTest END
}