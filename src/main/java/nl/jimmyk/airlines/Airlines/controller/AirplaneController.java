package nl.jimmyk.airlines.Airlines.controller;

import nl.jimmyk.airlines.Airlines.model.Airplane;
import nl.jimmyk.airlines.Airlines.repository.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/airplanes")
public class AirplaneController {

    @Autowired
    private AirplaneRepository airplaneRepository;

    @RequestMapping(value="/list", method=RequestMethod.GET)
    public Iterable<Airplane> getAllAirplanes(){
        return this.airplaneRepository.findAll();
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public Airplane createAirplane(@RequestBody Airplane airplane){
        this.airplaneRepository.save(airplane);
        return airplane;
    }

    @RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    public void deleteBooking(@PathVariable long id) {
        this.airplaneRepository.delete(id);
    }
}
