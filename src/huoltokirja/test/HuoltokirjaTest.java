package huoltokirja.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import huoltokirja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.11 22:47:33 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class HuoltokirjaTest {



  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTailisaa57 
   * @throws ApuException when error
   */
  @Test
  public void testKorvaaTailisaa57() throws ApuException {    // Huoltokirja: 57
    Huoltokirja huoltokirja = new Huoltokirja(); 
    assertEquals("From: Huoltokirja line: 60", 0, huoltokirja.getPyoria()); 
    Pyora pyora1 = new Pyora(); 
    pyora1.parse(" 1 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123"); 
    huoltokirja.lisaa(pyora1); 
    assertEquals("From: Huoltokirja line: 64", 1, huoltokirja.getPyoria()); 
    Pyora pyora2 = new Pyora(); 
    pyora2.parse(" 1 |  Hybridi  |  Tunturi   | Pappa | 1999   | abc124"); 
    huoltokirja.korvaaTailisaa(pyora2); 
    assertEquals("From: Huoltokirja line: 68", 1, huoltokirja.getPyoria()); 
    assertEquals("From: Huoltokirja line: 69", "Hybridi", huoltokirja.annaPyora(0).anna(1)); 
    Pyora pyora3 = new Pyora(); 
    pyora3.parse(" 2 |  Citypyörä  |  Trek   | City | 2010   | poi567"); 
    huoltokirja.korvaaTailisaa(pyora3); 
    assertEquals("From: Huoltokirja line: 73", 2, huoltokirja.getPyoria()); 
    assertEquals("From: Huoltokirja line: 74", "Citypyörä", huoltokirja.annaPyora(1).anna(1)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLisaa98 
   * @throws ApuException when error
   */
  @Test
  public void testLisaa98() throws ApuException {    // Huoltokirja: 98
    Huoltokirja huoltokirja = new Huoltokirja(); 
    assertEquals("From: Huoltokirja line: 101", 0, huoltokirja.getHuoltoja()); 
    Huolto huolto = new Huolto(); 
    huoltokirja.lisaa(huolto); 
    assertEquals("From: Huoltokirja line: 104", 1, huoltokirja.getHuoltoja()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoista117 
   * @throws ApuException when error
   */
  @Test
  public void testPoista117() throws ApuException {    // Huoltokirja: 117
    Huoltokirja huoltokirja = new Huoltokirja(); 
    assertEquals("From: Huoltokirja line: 120", 0, huoltokirja.getPyoria()); 
    Pyora pyora = new Pyora(); 
    pyora.parse(" 1 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123"); 
    huoltokirja.lisaa(pyora); 
    assertEquals("From: Huoltokirja line: 124", 1, huoltokirja.getPyoria()); 
    huoltokirja.poista(pyora); 
    assertEquals("From: Huoltokirja line: 126", 0, huoltokirja.getPyoria()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testAnnaHuollot152 
   * @throws ApuException when error
   */
  @Test
  public void testAnnaHuollot152() throws ApuException {    // Huoltokirja: 152
    Huoltokirja huoltokirja = new Huoltokirja(); 
    Pyora pyora = new Pyora(); 
    pyora.parse(" 1 |  Kottero  |  Helkama   | Jopo  | 2000   | abc123"); 
    Huolto huolto1 = new Huolto(1); 
    huoltokirja.lisaa(huolto1); 
    assertEquals("From: Huoltokirja line: 160", true, huoltokirja.annaHuollot(pyora).get(0) == huolto1); 
    try {
    assertEquals("From: Huoltokirja line: 161", false, huoltokirja.annaHuollot(pyora).get(1) == huolto1); 
    fail("Huoltokirja: 161 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); } // varmistetaan että muita huoltoja ei ole.
    Huolto huolto2 = new Huolto(1); 
    huoltokirja.lisaa(huolto2); 
    assertEquals("From: Huoltokirja line: 164", true, huoltokirja.annaHuollot(pyora).get(1) == huolto2); 
    Pyora pyora2 = new Pyora(); 
    pyora.parse(" 2 |  Hybridi  |  Tunturi   | ei tiedossa  | 1950   | afvgdg123"); 
    try {
    assertEquals("From: Huoltokirja line: 167", true, huoltokirja.annaHuollot(pyora2).get(0) == null); 
    fail("Huoltokirja: 167 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END
}