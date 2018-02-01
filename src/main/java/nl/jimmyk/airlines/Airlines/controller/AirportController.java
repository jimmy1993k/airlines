package nl.jimmyk.airlines.Airlines.controller;

import nl.jimmyk.airlines.Airlines.model.Airport;
import nl.jimmyk.airlines.Airlines.repository.AirportRepository;
import nl.jimmyk.airlines.Airlines.utilities.DistanceCalculator;
import nl.jimmyk.airlines.Airlines.utilities.EUnits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    @Autowired
    private AirportRepository airportRepository;

    @RequestMapping(value="/list", method=RequestMethod.GET)
    public Iterable<Airport> getAllAirports(){
        return this.airportRepository.findAll();
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public Airport createAirport(@RequestBody Airport airport){
        this.airportRepository.save(airport);
        return airport;
    }

    @RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    public void deleteAirport(@PathVariable long id) {
        this.airportRepository.delete(id);
    }

    @RequestMapping(value="/{airportIdA}/distance/{airportIdB}", method=RequestMethod.GET)
    public double getDistance(@PathVariable long airportIdA, @PathVariable long airportIdB) {
        Airport airportA = this.airportRepository.findOne(airportIdA);
        Airport airportB = this.airportRepository.findOne(airportIdB);
        return DistanceCalculator.distance(airportA, airportB, EUnits.KILOMETERS);
    }
}
