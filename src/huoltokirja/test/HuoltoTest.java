package huoltokirja.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import huoltokirja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.03.18 15:11:23 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class HuoltoTest {



  // Generated by ComTest BEGIN
  /** testToString92 */
  @Test
  public void testToString92() {    // Huolto: 92
    Huolto huolto1 = new Huolto(); 
    huolto1.parse(" 1  |  1  |  Jarruhuolto  | 75 | puhdistin ja vaihdoin"); 
    assertEquals("From: Huolto line: 95", "1|1|Jarruhuolto|75|puhdistin ja vaihdoin", huolto1.toString()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse113 */
  @Test
  public void testParse113() {    // Huolto: 113
    Huolto huolto2 = new Huolto(); 
    huolto2.parse(" 2  |  2  |  Iskari  | 100 | Öljynvaihto"); 
    assertEquals("From: Huolto line: 116", "Iskari", huolto2.getNimi()); 
    assertEquals("From: Huolto line: 117", 100, huolto2.getAjotunnit()); 
    assertEquals("From: Huolto line: 118", "2|2|Iskari|100|Öljynvaihto", huolto2.toString());
    assertEquals("From: Huolto line: 119", 2, huolto2.getTunnusNro()); 
    huolto2.rekisteroi();  // rekisteröinti kasvattaa tunnusNro arvoa yhdellä
    assertEquals("From: Huolto line: 121", 3, huolto2.getTunnusNro()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testRekisteroi138 */
  @Test
  public void testRekisteroi138() {    // Huolto: 138
    Huolto huolto3 = new Huolto(); 
    huolto3.rekisteroi(); 
    assertEquals("From: Huolto line: 141", 4, huolto3.getTunnusNro());  // TunnusNro = 4, koska huolto 2 rekisteröitiin kahdesti.
    Huolto huolto4 = new Huolto(); 
    huolto4.rekisteroi(); 
    assertEquals("From: Huolto line: 144", 5, huolto4.getTunnusNro()); 
  } // Generated by ComTest END
}