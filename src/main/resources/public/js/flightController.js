function FlightController(airport, flight) {
    var self = this;

    self.airport = airport;
    self.flight = flight;
    self.modal = $('#flightModal');

    this.showModal();
}

_.extend(FlightController.prototype, {
});
