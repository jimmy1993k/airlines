function AirplaneController(map, airport, airplane) {
    var self = this;

    self.map = map;
    self.airport = airport;
    self.airplane = airplane;
    self.modal = $('#airplaneModal');

    this.showModal();
}

_.extend(AirplaneController.prototype, {
    showModal: function () {
        var self = this;
        self.modal.find('#airplaneTitle').html('Flying ' + self.formatTitle() + ' <i class="fa fa-plane"></i>' + ' to');

        self.modal.find('#airplaneAirports').html("");
        self.modal.modal('show');

        ajaxJsonCall('GET', '/api/airports/list', null, function (result) {
            _.each(result, function (airport) {
                if (airport.id === self.airport.id) {
                    return;
                }

                var valid = self.valid(airport);
                var card = AirplaneController.generateAirportCard(self.airport, airport, valid);
                var button = $('<button type="button" class="btn btn-primary float-right">Select</a>');
                button.on('click', function () {
                    new FlightController(self.map, self.airport, airport, self.airplane);
                    self.modal.modal('hide');
                });
                if (!valid) {
                    button.prop('disabled', true);
                }
                card.find('p.card-text').append(button);
                self.modal.find('#airplaneAirports').append(card);
            });
        });
    },
    valid: function(airport) {
        if (airport.airplanes.length >= 3) {
            return false;
        }
        return true;
    },
    formatTitle: function () {
        return this.airplane.name + ' (' + this.airport.name + ', ' + this.airport.city + ', ' + this.airport.country + ')'
    }
});

// Static method
AirplaneController.generateAirportCard = function(fromAirport, toAirport, valid) {
    valid = valid || false;

    var cardContainer = $('<div class="card animated fadeIn mb-2"></div>');
    if (valid) {
        cardContainer.addClass('border-primary');
    }

    var cardBody = $('<div class="card-body"></div>');
    var cardTitle = $('<h5 class="card-title">' + toAirport.name + '</h5>');
    var cardText = $('<p class="card-text"></p>');
    var airportSpecifics = $('<ul class="list-unstyled"></ul>');
    var specifics = {
        "Planes": toAirport.airplanes.length,
        "Distance": DistanceCalculator.getDistanceFromLatLonInKm(
            fromAirport.latitude,
            fromAirport.longitude,
            toAirport.latitude,
            toAirport.longitude
        ).toFixed(2) + " km"
    };
    _.each(specifics, function(value, key) {
        var element = $('<li> ' + key + ': ' + value + ' </li>');
        airportSpecifics.append(element);
    });
    cardContainer.append(cardBody.append(cardTitle).append(cardText.append(airportSpecifics)));
    return $('<div class="col-4"></div>').append(cardContainer);
};
