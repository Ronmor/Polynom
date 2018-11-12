
package myMath;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and b is an integer (summed a none negative),
 * see: https://en.wikipedia.org/wiki/Monomial
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply.
 *
 * @author Boaz
 *
 */
public class Monom implements function {

    public Monom(double a, int b) {
        this.set_coefficient(a);
        this.set_power(b);
    }

    public Monom(Monom ot) {
        this(ot._coefficient, ot._power);
    }

    // ***************** add your code below **********************

    /**
     * returns Monom value for given input
     * @param x
     * @return
     */

    public double f(double x) {
        return _coefficient * Math.pow(x, _power);
    }

    /**
     *
     * @param s
     * following constructor reads a String to initialize Monom.
     * matches given input into a shape of aX^b , then Parsing a and b;
     */
    public Monom(String s) {
        if (s.isEmpty()) {
            _coefficient = 0;
            _power = 0;
            return;
        }
        s = s.toLowerCase();
        if (!s.contains("x")) {
            s += "x^0";
        }
        String coefficient = "";
        int index = s.indexOf("x");
        if (index==0){
            coefficient += 1;
        }
        if (!s.contains("^")) {
            s += "^1";
        }
        coefficient +=  s.substring(0, index);
        if (coefficient.equals("-")) {
            coefficient += 1;
        }
        this._coefficient = coefficient.isEmpty() ? 1 : Double.parseDouble(coefficient);
        if (s.charAt(index + 1) != '^' || index + 1 >= s.length()) {
            this._power = 1;
            return;
        }
        String power = s.substring(index + 2, s.length());
        this._power = Integer.parseInt(power);
    }

    public void add(Monom m1) {
        if (_power == m1._power) {
            _coefficient += m1._coefficient;
        }
    }

    public void multiply(Monom m1) {
        _power += m1._power;
        _coefficient *= m1._coefficient;
    }

    public void derivative() {
            _coefficient = _coefficient * _power;
            _power = Math.max(_power - 1, 0);

    }

    public int get_power() {
        return _power;
    }

    public double get_coefficient() {
        return _coefficient;
    }

    /**
     *this method prints Monoms with specific instructions for specific cases.
     * @return
     */
    public String toString() {
        if (_coefficient==1 && _power==0) {
            return "+1";
        }
        String str = "";
        if (_coefficient == 0) {
            return str;
        }
        if (_coefficient > 0) {
            str += "+";
        }
        if (_coefficient != 1) {
            if (_coefficient == (int) _coefficient) {
                str += (int) _coefficient;
            } else {
                str += _coefficient;
            }
        }
        if (_power == 0) {
            return str;
        }
        str += "X";
        if (_power != 1) {
            str += "^" + _power;
        }
        return str;
    }

    //****************** Private Methods and Data *****************
    private void set_coefficient(double a) {
        this._coefficient = a;
    }

    private void set_power(int p) {
        this._power = p;
    }

    private double _coefficient; //
    private int _power;
}
