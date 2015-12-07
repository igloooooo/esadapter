package au.com.iglooit.espower.esadapter.util;

/**
 * Created by nicholaszhu on 7/12/2015.
 */
public final class StatisticUtil {
    private StatisticUtil () {

    }

    public static double round(Double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
