package nl.jimmyk.airlines.Airlines.repository;

import nl.jimmyk.airlines.Airlines.model.Flight;
import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<Flight, Long> {}
