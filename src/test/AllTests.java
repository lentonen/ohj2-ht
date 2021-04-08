package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Testsuite huoltokirjalle
 * @author Henri Leinonen
 * @version 8.4.2021
 */
@RunWith(Suite.class)
@SuiteClasses({
    huoltokirja.test.HuollotTest.class,
    huoltokirja.test.HuoltoTest.class,
    huoltokirja.test.PyoraTest.class,
    huoltokirja.test.PyoratTest.class,
    huoltokirja.test.TietueTest.class 
    })

public class AllTests {
    //
}
