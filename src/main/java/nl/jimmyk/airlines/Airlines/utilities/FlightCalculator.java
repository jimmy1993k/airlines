package nl.jimmyk.airlines.Airlines.utilities;

import nl.jimmyk.airlines.Airlines.model.Flight;

import java.time.LocalDateTime;

/**
 * The flight calculator
 *
 * Has some functions to help calculate certain aspects of flights
 */
public class FlightCalculator {
    /**
     * Calculates the distance between airports for a flight
     * @param flight The flight to calculate distance for
     * @return double
     */
    public static double getDistance(Flight flight) {
        return DistanceCalculator.distance(flight.getFromAirport(), flight.getToAirport());
    }

    /**
     * Calculates the time it takes for a flight
     * @param flight The flight to calculate the flight time for
     * @return long
     */
    public static long getFlightTime(Flight flight) {
        double distance = getDistance(flight);
        return Math.round(SpeedConverter.fromKnotsToKilometerPerHour(flight.getAirplane().getSpeed()) / distance * 60 * 60);
    }

    /**
     * Calculates the landing time for a given flight
     * @param flight The flight to calculate the landing time for
     * @param takeOffTime The take off time
     * @return LocalDateTime
     */
    public static LocalDateTime getLandingTime(Flight flight, LocalDateTime takeOffTime) {
        return takeOffTime.plusSeconds(getFlightTime(flight));
    }
}
