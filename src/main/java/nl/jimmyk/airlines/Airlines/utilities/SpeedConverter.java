package nl.jimmyk.airlines.Airlines.utilities;

/**
 * Converts units of speed
 */
public class SpeedConverter {
    public static double fromKnotsToKilometerPerHour(double knots) {
        return knots * 1.852;
    }
}
