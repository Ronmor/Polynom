package myMath;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 *
 * @author Boaz
 */
public class Polynom implements Polynom_able {

    private List<Monom> monoms = new LinkedList<>(); // monoms are sorted from 0 to highest power

    /**
     *  following method sums two Polynoms.
     * @param p1
     */
    @Override
    public void add(Polynom_able p1) {
        Iterator<Monom> iterator = p1.iteretor();
        while (iterator.hasNext()) {
            add(iterator.next());
        }
    }

    /**
     *
     * @param m1 Monom
     *           following public method adds m1 into a specified Polynom.
     *           finding spot for storing Monom data than adding.
     */
    @Override
    public void add(Monom m1) {
        if (m1.get_power() >= monoms.size()) { // the new monom power is highest than the current highest power
            for (int power = monoms.size(); power < m1.get_power(); power++) {
                monoms.add(new Monom(0, power));
            }
            monoms.add(m1);
        } else {
            monoms.get(m1.get_power()).add(m1);
        }
    }

    /**
     * following method subtracts input from a Polynom.
     * @param p1
     */
    @Override
    public void substract(Polynom_able p1) {
        Iterator<Monom> iterator = p1.iteretor();
        while (iterator.hasNext()) {
            Monom monom = iterator.next();
            Monom toAdd = new Monom(-1 * monom.get_coefficient(), monom.get_power());
            add(toAdd);
        }
        cleanHighPowerZeroMonoms();
    }

    private void cleanHighPowerZeroMonoms() {
        for (int power = monoms.size() - 1; power >= 0; power--) {
            if (monoms.get(power).get_coefficient() == 0) {
                monoms.remove(power);
            } else {
                break;
            }
        }
    }

    /**
     *
     * @param p1
     *
     * @throws NullPointerException if p1 is null
     */
    @Override
    public void multiply(Polynom_able p1) {
        List<Polynom> aggregate = new LinkedList<>();
        // we will multiply a copy of this polynom by each monom of p1 and store the results in this list
        Polynom toMul = new Polynom();
        Iterator<Monom> iterator = p1.iteretor();
        while (iterator.hasNext()) {
            toMul.add(iterator.next());
        }
        for (int myPower = 0; myPower < monoms.size(); myPower++) {
            Polynom toAggregate = new Polynom();
            Polynom temp = (Polynom) copy();
            for (int otherPower = 0; otherPower < toMul.monoms.size(); otherPower++) {
                Monom toAdd = new Monom(monoms.get(myPower));
                toAdd.multiply(toMul.monoms.get(otherPower));
                toAggregate.add(toAdd);
            }
            aggregate.add(toAggregate);
        }
        monoms.clear();
        // zero the value of this polynom
        aggregate.forEach(this::add);
        // add each stored multiplication result to this polynom
    }

    public Polynom() { //zero polynom
        this.monoms.clear();
    }

    /**
     * following constructor initializes a new Polynom from any given valid input.
     * @param str
     */
    public Polynom(String str) {
        Polynom Init = new Polynom();
        String[] monomsStrings = monomStrings(str);
        for (String monomString : monomsStrings) {
            Init.add(new Monom(monomString));
        }
        for (Monom monom : Init.monoms ) {
            this.add(monom);
        }
        cleanHighPowerZeroMonoms();
    }

    /**
     * Let p1,p2 be valid Polynoms.
     * Method returns true or false whether p1==p2 ;
     * @param p1
     * @return
     */
    @Override
    public boolean equals(Polynom_able p1) {
        Polynom polynom = new Polynom();
        Iterator<Monom> iterator = p1.iteretor();
        while (iterator.hasNext()) {
            polynom.add(iterator.next());
        }
        if (monoms.size() != polynom.monoms.size()) {
            return false;
        }
        for (int power = 0; power < monoms.size(); power++) {
            if (monoms.get(power).get_coefficient() != polynom.monoms.get(power).get_coefficient()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isZero() {
        return monoms.isEmpty();
    }

    /**
     * following method returns root as detailed:
     * @param x0 starting point
     * @param x1 end point
     * @param eps step (positive) value
     * @return  x : f(x)=0 && |x-x'| <= eps.
     */
    @Override
    public double root(double x0, double x1, double eps) {
        // sign(f(x0)) != sign(f(x1)) => Ex x0<x<x1 || x1<x<x0 && f(x) = 0
        // x' s.t. |x-x'|<=eps
        double fx0 = f(x0);
        double midPoint = Math.min(x0, x1) + Math.abs(x1 - x0) / 2;
        double fMiddle = f(midPoint);
        if (Math.abs(fMiddle) < eps) {
            return midPoint;
        } else if (fx0 * fMiddle > 0) { // both have the same sign
            return root(midPoint, x1, eps);
        } else {
            return root(x0, midPoint, eps);
        }
    }

    /**
     * following method preforms a deep copy for a Polynom.
     * @return
     */
    @Override
    public Polynom_able copy() {
        Polynom polynom = new Polynom();
        for (Monom monom : monoms) {
            polynom.add(new Monom(monom));
        }
        return polynom;
    }

    /**
     * following method preforms a derivative for a Polynom
     * @return
     */
    @Override
    public Polynom_able derivative() {
        Polynom polynom = new Polynom();
        for (Monom monom1 : monoms) {
            Monom monom = new Monom(monom1);
            monom.derivative();
            polynom.add(monom);
        }
        return polynom;
    }

    /**
     * following method calculates area for a Polynom using Riemann's integral
     * @param x0
     * @param x1
     * @param eps
     * @return approximated area of a function's graph , between [x0,x1] range.
     */
    @Override
    public double area(double x0, double x1, double eps) {
        int rectangles = (int) ((Math.max(x1, x0) - Math.min(x1, x0)) / eps);
        double sum = 0;
        for (int i = 0; i < rectangles; i++) {
            double height = f(x0);
            if (height<=0){
                x0 += eps;
                continue;
            }
            sum += eps * height;
            x0 += eps;
        }
        return sum;
    }

    @Override
    public Iterator<Monom> iteretor() {
        return monoms.iterator();
    }

    /**
     * following method returns polynom's value at a given input.
     * @param x
     * @return P(X);
     */
    @Override
    public double f(double x) {
        if (isZero()) {
            return 0;
        }
        double ans = 0;
        for (int power = 0; power < monoms.size(); power++) {
            ans += monoms.get(power).f(x);
        }
        return ans;
    }

    @Override
    public String toString() {
        String str = "";
        for (int power = monoms.size() - 1; power >= 0; power--) {
            str += monoms.get(power).toString();
        }
        return str.startsWith("+") ? str.substring(1, str.length()) : str;
    }

    // ********** add your code below ***********
    private static String[] monomStrings(String str) {
        return str.replaceAll(" ", "").replaceAll("-", "+-").split("\\+");
    }
}
