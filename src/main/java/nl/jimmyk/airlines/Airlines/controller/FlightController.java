package nl.jimmyk.airlines.Airlines.controller;

import nl.jimmyk.airlines.Airlines.model.*;
import nl.jimmyk.airlines.Airlines.repository.AirplaneRepository;
import nl.jimmyk.airlines.Airlines.repository.AirportRepository;
import nl.jimmyk.airlines.Airlines.repository.FlightRepository;
import nl.jimmyk.airlines.Airlines.utilities.FlightCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private AirplaneRepository airplaneRepository;

    @RequestMapping(value="/prepare", method=RequestMethod.POST)
    public Flight prepareFlight(@RequestBody FlightInstructions flightInstructions) {
        Airplane airplane = airplaneRepository.findOne(flightInstructions.getAirplaneId());
        Airport toAirport = airportRepository.findOne(flightInstructions.getToAirportId());
        Airport fromAirport = airportRepository.findOne(flightInstructions.getFromAirportId());

        LocalDateTime scheduledTakeOffTime = LocalDateTime.now();
        Flight flight = new Flight();
        flight.setAirplane(airplane);
        flight.setFromAirport(fromAirport);
        flight.setToAirport(toAirport);

        double distance = FlightCalculator.getDistance(flight);
        flight.setDistance(distance);
        flight.setScheduledTakeOffTime(scheduledTakeOffTime);
        flight.setScheduledLandingTime(FlightCalculator.getLandingTime(flight, scheduledTakeOffTime));
        flight.setFuelNeeded(airplane.getFuelUsage() * distance);
        flightRepository.save(flight);

        return flight;
    }

    @RequestMapping(value="/fly/{flightId}", method=RequestMethod.POST)
    public Flight flyFlight(@PathVariable long flightId) {
        Flight flight = flightRepository.findOne(flightId);
        Airplane airplane = flight.getAirplane();

        LocalDateTime takeOffTime = LocalDateTime.now();
        flight.setTakeOffTime(takeOffTime);

        airplane.setFuel(airplane.getFuel() - flight.getFuelNeeded());
        flight.setStatus(EFlightStatus.FINISHED);

        flight.setLandingTime(FlightCalculator.getLandingTime(flight, takeOffTime));
        airplane.setAirport(flight.getToAirport());

        airplaneRepository.save(airplane);
        flightRepository.save(flight);

        return flight;
    }

    /**
     * Checks if all conditions are met for a flight
     *
     * @param airplane
     * @param fromAirport
     * @param toAirport
     * @param distance
     * @param flightTime
     * @return
     */
    private boolean checkFlightConditions(Airplane airplane, Airport fromAirport, Airport toAirport, double distance, long flightTime) {
        if (airplane == null || fromAirport == null || toAirport == null) {
            //TODO throw not found exception
            return false;
        }

        if (airplane.getAirport() == null) {
            //TODO throw some exception
            return false;
        }

        if (airplane.getAirport().getId() == fromAirport.getId()) {
            //TODO throw some exception
            return false;
        }

        return true;
    }
}
