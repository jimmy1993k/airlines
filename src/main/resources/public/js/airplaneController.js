function AirplaneController(airport, airplane) {
    var self = this;

    self.airport = airport;
    self.airplane = airplane;
    self.modal = $('#airplaneModal');

    this.showModal();
}

_.extend(AirplaneController.prototype, {
    showModal: function () {
        var self = this;
        self.modal.find('#airplaneTitle').html(self.formatTitle() + ' <i class="fa fa-plane"></i>');

        self.modal.find('#airplaneAirports').html("");
        self.modal.modal('show');

        ajaxJsonCall('GET', '/api/airports/list', null, function (result) {
            _.each(result, function (airport) {
                var cardContainer = $('<div class="card animated fadeIn mb-2"></div>');
                var cardBody = $('<div class="card-body"></div>');
                var cardTitle = $('<h5 class="card-title">' + airport.name + '</h5>');
                var cardText = $('<p class="card-text"></p>');
                var airportSpecifics = $('<ul class="list-unstyled"></ul>');
                var specifics = {
                    "Planes": airport.airplanes.length,
                    "Distance": ""
                };
                _.each(specifics, function(value, key) {
                    var element = $('<li> ' + key + ': ' + value + ' </li>');
                    airportSpecifics.append(element);
                });
                var button = $('<button type="button" class="btn btn-primary">Select</a>');

                cardContainer.append(cardBody.append(cardTitle).append(cardText.append(airportSpecifics).append(button)));
                self.modal.find('#airplanesAirports').append($('<div class="col-4"></div>').append(cardContainer));

                button.on('click', function () {
                    new AirplaneController(self.airport, airplane);
                    self.modal.modal('hide');
                });
            });
        });
    },
    formatTitle: function () {
        return this.airplane.name + ' (' + this.airport.name + ', ' + this.airport.city + ', ' + this.airport.country + ')'
    }
});
