package nl.jimmyk.airlines.Airlines.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Fuel in kg
     */
    @Column
    private double fuel;

    /**
     * The airport the airplane is currently in
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="airport_id")
    private Airport airport;

    /**
     * Speed in knots
     */
    @Column
    private int speed;

    public Airplane() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
