package huoltokirja.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import huoltokirja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.20 13:42:23 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class HuoltoTest {



  // Generated by ComTest BEGIN
  /** testToString111 */
  @Test
  public void testToString111() {    // Huolto: 111
    Huolto huolto1 = new Huolto(); 
    huolto1.parse(" 1  |  1  |2.1.2021 |150 |  Jarruhuolto  | 75 | puhdistin ja vaihdoin"); 
    assertEquals("From: Huolto line: 114", "1|1|2.1.2021|150.00|Jarruhuolto|75|puhdistin ja vaihdoin", huolto1.toString()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse134 */
  @Test
  public void testParse134() {    // Huolto: 134
    Huolto huolto2 = new Huolto(); 
    huolto2.parse(" 2  |  2  | 1.1.2020|200  | Iskari  | 100 | Öljynvaihto"); 
    assertEquals("From: Huolto line: 137", "Iskari", huolto2.getNimi()); 
    assertEquals("From: Huolto line: 138", 100, huolto2.getAjotunnit()); 
    assertEquals("From: Huolto line: 139", "2|2|1.1.2020|200.00|Iskari|100|Öljynvaihto", huolto2.toString());
    assertEquals("From: Huolto line: 140", 2, huolto2.getTunnusNro()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testRekisteroi159 */
  @Test
  public void testRekisteroi159() {    // Huolto: 159
    Huolto huolto3 = new Huolto(); 
    huolto3.rekisteroi(); 
    assertEquals("From: Huolto line: 162", 3, huolto3.getTunnusNro()); 
    Huolto huolto4 = new Huolto(); 
    huolto4.rekisteroi(); 
    assertEquals("From: Huolto line: 165", 4, huolto4.getTunnusNro()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAvain265 */
  @Test
  public void testAvain265() {    // Huolto: 265
    Huolto huolto = new Huolto(); 
    huolto.aseta(0,"1"); 
    assertEquals("From: Huolto line: 268", "  1", huolto.avain(0)); 
    huolto.aseta(1,"12"); 
    assertEquals("From: Huolto line: 270", " 12", huolto.avain(1)); 
    huolto.aseta(2,"1.1.2021"); 
    assertEquals("From: Huolto line: 272", "2021 1 1", huolto.avain(2)); 
    huolto.aseta(3,"150.23"); 
    assertEquals("From: Huolto line: 274", "0.15023", huolto.avain(3)); 
    huolto.aseta(4,"FUJI"); 
    assertEquals("From: Huolto line: 276", "fuji", huolto.avain(4)); 
    huolto.aseta(5,"150"); 
    assertEquals("From: Huolto line: 278", "  150", huolto.avain(5)); 
    huolto.aseta(6,"Putsattiin"); 
    assertEquals("From: Huolto line: 280", "putsattiin", huolto.avain(6)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAseta305 */
  @Test
  public void testAseta305() {    // Huolto: 305
    Huolto huolto = new Huolto(); 
    assertEquals("From: Huolto line: 307", null, huolto.aseta(0,"1")); 
    assertEquals("From: Huolto line: 308", "  1", huolto.avain(0)); 
    assertEquals("From: Huolto line: 309", "Huollon tunnusnumero on väärin", huolto.aseta(0,"1a")); 
    assertEquals("From: Huolto line: 310", "  1", huolto.avain(0)); 
    assertEquals("From: Huolto line: 312", null, huolto.aseta(1,"12")); 
    assertEquals("From: Huolto line: 313", " 12", huolto.avain(1)); 
    assertEquals("From: Huolto line: 314", "Pyörän tunnusnumero on väärin", huolto.aseta(1,"12a")); 
    assertEquals("From: Huolto line: 315", " 12", huolto.avain(1)); 
    assertEquals("From: Huolto line: 317", null, huolto.aseta(2,"1.1.2021")); 
    assertEquals("From: Huolto line: 318", "2021 1 1", huolto.avain(2)); 
    assertEquals("From: Huolto line: 319", "Päivämäärä on väärin", huolto.aseta(2,"32.1.2021")); 
    assertEquals("From: Huolto line: 320", "2021 1 1", huolto.avain(2)); 
    assertEquals("From: Huolto line: 322", null, huolto.aseta(3,"150.23")); 
    assertEquals("From: Huolto line: 323", "0.15023", huolto.avain(3)); 
    assertEquals("From: Huolto line: 324", "hinta väärin", huolto.aseta(3,"150.23g")); 
    assertEquals("From: Huolto line: 325", "0.15023", huolto.avain(3)); 
    assertEquals("From: Huolto line: 327", null, huolto.aseta(4,"FUJI")); 
    assertEquals("From: Huolto line: 328", "fuji", huolto.avain(4)); 
    assertEquals("From: Huolto line: 330", null, huolto.aseta(5,"150")); 
    assertEquals("From: Huolto line: 331", "  150", huolto.avain(5)); 
    assertEquals("From: Huolto line: 332", "Ajotunnit on väärin", huolto.aseta(5,"150a")); 
    assertEquals("From: Huolto line: 333", "  150", huolto.avain(5)); 
    assertEquals("From: Huolto line: 335", null, huolto.aseta(6,"Putsattiin")); 
    assertEquals("From: Huolto line: 336", "putsattiin", huolto.avain(6)); 
  } // Generated by ComTest END
}