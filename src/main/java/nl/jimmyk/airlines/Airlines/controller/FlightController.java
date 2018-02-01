package nl.jimmyk.airlines.Airlines.controller;

import nl.jimmyk.airlines.Airlines.model.Airplane;
import nl.jimmyk.airlines.Airlines.model.Airport;
import nl.jimmyk.airlines.Airlines.model.Flight;
import nl.jimmyk.airlines.Airlines.model.FlightInstructions;
import nl.jimmyk.airlines.Airlines.repository.AirplaneRepository;
import nl.jimmyk.airlines.Airlines.repository.AirportRepository;
import nl.jimmyk.airlines.Airlines.repository.FlightRepository;
import nl.jimmyk.airlines.Airlines.utilities.DistanceCalculator;
import nl.jimmyk.airlines.Airlines.utilities.SpeedConverter;
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

    @RequestMapping(value="/schedule", method=RequestMethod.POST)
    public void scheduleFlight(@RequestBody FlightInstructions flightInstructions) {
        Airplane airplane = airplaneRepository.findOne(flightInstructions.getAirplaneId());
        Airport toAirport = airportRepository.findOne(flightInstructions.getToAirportId());
        Airport fromAirport = airportRepository.findOne(flightInstructions.getFromAirportId());
        double distance = DistanceCalculator.distance(fromAirport, toAirport);
        long flightTime = Math.round(SpeedConverter.fromKnotsToKilometerPerHour(airplane.getSpeed()) / distance * 60 * 60);
        LocalDateTime scheduledTakeOffTime = LocalDateTime.now();
        LocalDateTime scheduledLandingTime = scheduledTakeOffTime.plusSeconds(flightTime);

        Flight flight = new Flight();
        flight.setAirplane(airplane);
        flight.setFromAirport(fromAirport);
        flight.setToAirport(toAirport);
        flight.setDistance(distance);
        flight.setScheduledTakeOffTime(scheduledTakeOffTime);
        flight.setScheduledLandingTime(scheduledLandingTime);
        flightRepository.save(flight);

        airplane.setAirport(toAirport);
        airplaneRepository.save(airplane);
    }

    /**
     *
     * @param airplane
     * @param airport
     * @return
     */
    private boolean checkFlightRequirements(Airplane airplane, Airport airport) {
        if (airplane == null || airport == null) {
            //TODO throw not found exception
            return false;
        }
        if (airplane.getAirport() == null) {
            //TODO throw some exception
            return false;
        }
        if (airplane.getAirport().getId() == airport.getId()) {
            //TODO throw some exception
            return false;
        }

        return true;
    }
}
