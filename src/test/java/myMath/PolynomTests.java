package myMath;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class represents the requested tests or checks for Ex0
 * This class represents the first version of my Polynom test
 * @author Ron Mor
 */
public class PolynomTests {

    private Polynom polynom;

    @Before
    public void setup() {
        polynom = new Polynom("X^2+X");
    }

    @Test
    public void addPolynomTest() {
        Polynom toAdd = new Polynom("3X-2");
        polynom.add(toAdd);
        Polynom expected = new Polynom("X^2+4X-2");
        Assert.assertTrue(polynom.equals(expected));
    }
    @Test
    public void addMonomToPolynomTest(){
        Monom toAdd = new Monom(2,2);
        polynom.add(toAdd);
        Polynom expected = new Polynom("3x^2+x");
        Assert.assertTrue(polynom.equals(expected));
    }

    @Test
    public void substractPolynomTest(){
        Polynom toSub = new Polynom("-x-x^2");
        polynom.substract(toSub);
        Polynom expected = new Polynom("2x^2+2x");
        // if you do new Polynom() it will work, but new Polynom("") should also work, so we need to fix something
        Assert.assertTrue(polynom.equals(expected));
    }

    @Test
    public void areaTest() {
        double expected = 23.0/6;
        assertEquals(expected, polynom.area(1, 2, 0.01), 0.05);
    }

    @Test
    public void multiplyTest() {
        Polynom toMul = new Polynom("X^2-X");
        polynom.multiply(toMul);
        Polynom expected = new Polynom("X^4-X^2");
        Assert.assertTrue(expected.equals(polynom));
    }

    @Test(expected = NullPointerException.class)
    public void multiplyNullTest() {
        polynom.multiply(null);
    }

    @Test
    public void fTest(){
        double expected = 0.75;
        Assert.assertEquals(expected, polynom.f(0.5), 0.0);
    }

    @Test
    public void derivativeTest(){
        Polynom expected = new Polynom("2x+1");
        assertTrue(expected.equals(polynom.derivative()));
    }

    @Test
    public void isZeroTest(){
        Polynom p = new Polynom("0X^4");
        Assert.assertTrue(p.isZero());
    }

    @Test
    public void copyTest(){
        Polynom other = (Polynom) polynom.copy();
        Assert.assertTrue(other.equals(polynom));
    }

    @Test
    public void rootTest(){
        Polynom rootTest = new Polynom("x^2-8x+15");
        double result = 3.0;
        assertEquals(result , rootTest.root(2,4,0.001),0.03);
    }
}
