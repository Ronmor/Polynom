package myMath;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * This class represents the requested tests or checks for Ex0
 * This class represents the first version of my Monom test
 * @author Ron Mor
 */
public class MonomTests {

    private Monom monom;

    @Before
    public void setup() {
        monom = new Monom("2X");
    }

    @Test
    public void fTest() {
        Assert.assertEquals(10, monom.f(5), 0);
    }

    @Test
    public void addTest() {
        Monom toAdd = new Monom("X");
        monom.add(toAdd);
        verifyMonom(3, 1);
    }

    @Test
    public void multiplyTest() {
        Monom toMultiply = new Monom("-x^3");
        monom.multiply(toMultiply);
        verifyMonom(-2, 4);
    }
    @Test
    public void derivativeTest() {
        monom.derivative();
        verifyMonom(2,0);
    }

    private void verifyMonom(double coefficient, int power) {
        Assert.assertEquals(coefficient, monom.get_coefficient(), 0);
        Assert.assertEquals(power, monom.get_power(), 0);
    }
}
