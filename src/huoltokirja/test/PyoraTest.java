package huoltokirja.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import huoltokirja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.02.11 15:18:26 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class PyoraTest {



  // Generated by ComTest BEGIN
  /** testGetNimi30 */
  @Test
  public void testGetNimi30() {    // Pyora: 30
    Pyora pyora = new Pyora(); 
    assertEquals("From: Pyora line: 32", "", pyora.getNimi()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testRekisteroi59 */
  @Test
  public void testRekisteroi59() {    // Pyora: 59
    Pyora pyora = new Pyora(); 
    pyora.rekisteroi(); 
    assertEquals("From: Pyora line: 62", 1, pyora.getTunnusNro()); 
    Pyora pyora2 = new Pyora(); 
    pyora2.rekisteroi(); 
    assertEquals("From: Pyora line: 65", 2, pyora2.getTunnusNro()); 
  } // Generated by ComTest END
}