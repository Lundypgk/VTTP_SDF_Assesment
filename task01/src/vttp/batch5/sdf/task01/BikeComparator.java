package vttp.batch5.sdf.task01;

import vttp.batch5.sdf.task01.models.BikeEntry;
import java.util.Comparator;

public class BikeComparator implements Comparator<BikeEntry> {

    @Override
    public int compare(BikeEntry first, BikeEntry second) {
        int totalFirst = first.getCasual() + first.getRegistered();
        int totalSecond = second.getCasual() + second.getRegistered();
        if (totalFirst > totalSecond) {
            return -1;
        } else if (totalFirst < totalSecond) {
            return 1;
        } else return 0;
    }

}
