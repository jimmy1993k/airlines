function AirportController(airport) {
    var self = this;

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
            var cardContainer = $('<div class="card animated fadeIn"></div>');
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
                var element = $('<li> ' + key + ': ' + value + ' </li>');
                airplaneSpecifics.append(element);
            });
            var button = $('<button type="button" class="btn btn-primary">Select</a>');

            cardContainer.append(cardBody.append(cardTitle).append(cardText.append(airplaneSpecifics).append(button)));
            self.modal.find('#airportAirplanes').append($('<div class="col-4"></div>').append(cardContainer));

            button.on('click', function () {
                new AirplaneController(self.airport, airplane);
                self.modal.modal('hide');
            });
        });
    },
    formatTitle: function () {
        return this.airport.name + ' (' + this.airport.city + ', ' + this.airport.country + ')'
    }
});
