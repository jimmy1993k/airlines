function FlightController(map, fromAirport, toAirport, airplane) {
    var self = this;

    self.map = map;
    self.fromAirport = fromAirport;
    self.toAirport = toAirport;
    self.airplane = airplane;
    self.modal = $('#flightModal');

    this.showModal();
}

_.extend(FlightController.prototype, {
    showModal: function () {
        var self = this;
        self.modal.find('#flightTitle').html(self.formatTitle());
        self.modal.find('#flights').html("");
        self.modal.modal('show');
        var payload = {
            airplaneId: self.airplane.id,
            fromAirportId: self.fromAirport.id,
            toAirportId: self.toAirport.id
        };
        ajaxJsonCall('POST', '/api/flights/prepare', payload, function (prepareResult) {
            var flight = prepareResult;
            console.log(flight);
            var card = FlightController.generateFullFlightCard(flight);
            var button = $('<button type="button" class="btn btn-primary float-right">Fly!</a>');
            button.on('click', function () {
                ajaxJsonCall('POST', '/api/flights/fly/' + flight.id, null, function (flyResult) {
                    flight = flyResult;
                    toastr.success('Flight succesfully started');
                    self.modal.modal('hide');

                    var flightPath = new google.maps.Polyline({
                        path: [
                            {lat: flight.fromAirport.latitude, lng: flight.fromAirport.longitude},
                            {lat: flight.toAirport.latitude, lng: flight.toAirport.longitude}
                        ],
                        geodesic: true,
                        strokeColor: '#FF0000',
                        strokeOpacity: 1.0,
                        strokeWeight: 2
                    });

                    flightPath.setMap(self.map);
                });
            });
            card.find('.card-body:first').append(button);
            self.modal.find('#flights').append(card);
        });
    },
    formatTitle: function () {
        return this.fromAirport.name + ' <i class="fa fa-plane"></i> ' + this.toAirport.name;
    }
});

// Static method
FlightController.generateFullFlightCard = function(flight) {
    var cardContainer = $('<div class="card border-primary animated fadeIn mb-2"></div>');
    var cardTitle = $('<h5 class="card-title">' +
        flight.fromAirport.name +
        ' <i class="fa fa-plane"></i> ' +
        flight.toAirport.name +
        ' (' +
        flight.airplane.name +
        ')</h5>');
    var cardBody = $('<div class="card-body"></div>');
    cardBody.append(cardTitle);
    var cardBodyRow = $('<div class="row"></div>');
    cardBodyRow.append(AirportController.generateAirplaneCard(flight.airplane));
    cardBodyRow.append(AirplaneController.generateAirportCard(flight.fromAirport, flight.toAirport, true));
    cardBodyRow.append(FlightController.generateFlightCard(flight));
    cardBody.append(cardBodyRow);
    cardContainer.append(cardBody);
    return $('<div class="col"></div>').append(cardContainer);
};
FlightController.generateFlightCard = function(flight) {
    var cardContainer = $('<div class="card border-primary animated fadeIn mb-2"></div>');

    var cardBody = $('<div class="card-body"></div>');
    var cardTitle = $('<h5 class="card-title">Flight</h5>');
    var cardText = $('<p class="card-text"></p>');
    var flightSpecifics = $('<ul class="list-unstyled"></ul>');
    var specifics = {
        "Distance": flight.distance.toFixed(2) + ' km'
    };
    _.each(specifics, function(value, key) {
        var element = $('<li> ' + key + ': ' + value + ' </li>');
        flightSpecifics.append(element);
    });
    cardContainer.append(cardBody.append(cardTitle).append(cardText.append(flightSpecifics)));
    return $('<div class="col"></div>').append(cardContainer);
};
