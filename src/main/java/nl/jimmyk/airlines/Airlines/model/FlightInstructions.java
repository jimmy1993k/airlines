package nl.jimmyk.airlines.Airlines.model;

public class FlightInstructions {
    private long airplaneId;
    private long fromAirportId;
    private long toAirportId;

    public long getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(long airplaneId) {
        this.airplaneId = airplaneId;
    }

    public long getFromAirportId() {
        return fromAirportId;
    }

    public void setFromAirportId(long fromAirportId) {
        this.fromAirportId = fromAirportId;
    }

    public long getToAirportId() {
        return toAirportId;
    }

    public void setToAirportId(long toAirportId) {
        this.toAirportId = toAirportId;
    }
}
