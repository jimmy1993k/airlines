package nl.jimmyk.airlines.Airlines.utilities;

import nl.jimmyk.airlines.Airlines.model.Airport;

import java.lang.*;

/**
 * Distance Calculator
 *
 * Calculates the distance between 2 geographical points
 *
 * GeoDataSource.com (C) All Rights Reserved 2017
 * Refactored a little bit
 */
public class DistanceCalculator {
    /**
     * Calculates the distance between 2 airports
     *
     * @param airportA an aiport
     * @param airportB an airport
     * @param units The unit to return the distance in (Kilometers, Miles or Nautical Miles
     * @return double
     */
    public static double distance(Airport airportA, Airport airportB, EUnits units) {
        return distance(airportA.getLatitude(), airportA.getLongitude(), airportB.getLatitude(), airportB.getLongitude(), units);
    }

    /**
     * Calculates the distance between 2 points
     *
     * @param lat1 Latitude point 1
     * @param lon1 Longitude point 2
     * @param lat2 Latitude point 2
     * @param lon2 Longitude point 2
     * @param units The unit to return the distance in (Kilometers, Miles or Nautical Miles
     * @return double
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2, EUnits units) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        switch(units) {
            case KILOMETERS: dist = dist * 1.609344; break;
            case NAUTICAL_MILES: dist = dist * 0.8684; break;
            case MILES: break;
        }
        return (dist);
    }

    /**
     * This function converts decimal degrees to radians
     *
     * @param deg The degrees to convert to radials
     * @return double
     */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * This function converts radians to decimal degrees
     *
     * @param rad The radials to convert to decimals
     * @return double
     */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}