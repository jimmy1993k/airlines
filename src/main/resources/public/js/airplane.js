function Airplane(map, flightPath, flight) {
    var self = this;
    self.map = map;
    self.flightPath = flightPath;
    self.flight = flight;

    var marker = new google.maps.Marker({
        map: map,
        data: flight.airplane.id + "",
        icon: {
            path: fontawesome.markers.PLANE,
            scale: 0.5,
            strokeWeight: 0.2,
            strokeColor: 'black',
            strokeOpacity: 1,
            fillColor: '#ffffff',
            fillOpacity: 1
        },
        clickable: false,
        position: {
            lat: flight.fromAirport.latitude,
            lng: flight.fromAirport.longitude
        },
        title: FlightController.generateTitle(flight.fromAirport, flight.toAirport)
    });
    // var interval = setInterval(function() {
    //
    // }, 5000);
}

_.extend(Airplane.prototype, {

});
