package nl.jimmyk.airlines.Airlines.repository;

import nl.jimmyk.airlines.Airlines.model.Airplane;
import org.springframework.data.repository.CrudRepository;

public interface AirplaneRepository extends CrudRepository<Airplane, Long> {}
