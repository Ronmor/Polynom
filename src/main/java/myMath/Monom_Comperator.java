package myMath;

import java.util.Comparator;

public class Monom_Comperator implements Comparator<Monom> {
    @Override
    public int compare(Monom o1, Monom o2) {
        if (o1.get_power() == o2.get_power()) {
            return 0;
        } else if (o1.get_power() > o2.get_power())
        {    return 1;  }
        else return -1;


        // ******** add your code below *********
    }
}
