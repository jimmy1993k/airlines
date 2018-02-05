function AirportController(map, airport) {
    var self = this;

    self.map = map;
    self.airport = airport;
    self.modal = $('#airportModal');

    this.showModal();
}

_.extend(AirportController.prototype, {
    showModal: function () {
        var self = this;
        self.modal.find('#airportTitle').html(self.formatTitle() + ' <i class="fa fa-building"></i>');
        self.modal.find('#airportAirplanes').html("");
        self.modal.modal('show');

        _.each(self.airport.airplanes, function (airplane) {
            var card = AirportController.generateAirplaneCard(airplane);
            var button = $('<button type="button" class="btn btn-primary float-right">Select</a>');
            button.on('click', function () {
                new AirplaneController(self.map, self.airport, airplane);
                self.modal.modal('hide');
            });
            card.find('p.card-text').append(button);
            self.modal.find('#airportAirplanes').append(card);
        });
    },
    formatTitle: function () {
        return this.airport.name + ' (' + this.airport.city + ', ' + this.airport.country + ')'
    }
});

// Static method
AirportController.generateAirplaneCard = function(airplane) {
    var cardContainer = $('<div class="card border-primary animated fadeIn mb-2"></div>');
    var cardBody = $('<div class="card-body"></div>');
    var cardTitle = $('<h5 class="card-title">' + airplane.name + '</h5>');
    var cardText = $('<p class="card-text"></p>');
    var airplaneSpecifics = $('<ul class="list-unstyled"></ul>');
    var specifics = {
        "Speed": airplane.speed + " km/h",
        "Fuel": airplane.fuel + " L",
        "Fuel usage": airplane.fuelUsage + " L/km"
    };
    _.each(specifics, function(value, key) {
        airplaneSpecifics.append($('<li> ' + key + ': ' + value + ' </li>'));
    });
    cardContainer.append(cardBody.append(cardTitle).append(cardText.append(airplaneSpecifics)));

    return $('<div class="col-4"></div>').append(cardContainer);
};
