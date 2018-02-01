package nl.jimmyk.airlines.Airlines.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private double fuelNeeded;

    @Column
    private double distance;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="airplane_id")
    private Airplane airplane;

    @ManyToOne
    @JoinColumn(name="from_airport_id")
    private Airport fromAirport;

    @ManyToOne
    @JoinColumn(name="to_airport_id")
    private Airport toAirport;

    @Column
    private EFlightStatus status;

    @Column
    private LocalDateTime scheduledTakeOffTime;

    @Column
    private LocalDateTime scheduledLandingTime;

    @Column
    private LocalDateTime takeOffTime;

    @Column
    private LocalDateTime landingTime;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToOne
    private Flight previousFlight;

    @OneToOne
    private Flight nextFlight;

    public Flight() {
        this.createdAt = LocalDateTime.now();
        this.status = EFlightStatus.SCHEDULED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getFuelNeeded() {
        return fuelNeeded;
    }

    public void setFuelNeeded(double fuelNeeded) {
        this.fuelNeeded = fuelNeeded;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public Airport getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(Airport fromAirport) {
        this.fromAirport = fromAirport;
    }

    public Airport getToAirport() {
        return toAirport;
    }

    public void setToAirport(Airport toAirport) {
        this.toAirport = toAirport;
    }

    public EFlightStatus getStatus() {
        return status;
    }

    public void setStatus(EFlightStatus status) {
        this.status = status;
    }

    public LocalDateTime getTakeOffTime() {
        return takeOffTime;
    }

    public void setTakeOffTime(LocalDateTime takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    public LocalDateTime getLandingTime() {
        return landingTime;
    }

    public void setLandingTime(LocalDateTime landingTime) {
        this.landingTime = landingTime;
    }

    public LocalDateTime getScheduledTakeOffTime() {
        return scheduledTakeOffTime;
    }

    public void setScheduledTakeOffTime(LocalDateTime scheduledTakeOffTime) {
        this.scheduledTakeOffTime = scheduledTakeOffTime;
    }

    public LocalDateTime getScheduledLandingTime() {
        return scheduledLandingTime;
    }

    public void setScheduledLandingTime(LocalDateTime scheduledLandingTime) {
        this.scheduledLandingTime = scheduledLandingTime;
    }
}
