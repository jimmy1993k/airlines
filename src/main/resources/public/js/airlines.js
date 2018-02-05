var map;

function initMap() {
    var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';
    var icons = {
        airport: {
            icon: iconBase + 'parking_lot_maps.png'
        }
    };

    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 8
    });

    ajaxJsonCall('GET', '/api/airports/list', null, function (result) {
        var airports = result;
        var latitudes = _.pluck(airports, 'latitude');
        var longitudes = _.pluck(airports, 'longitude');
        var latMax = _.max(latitudes);
        var latMin = _.min(latitudes);
        var longMax = _.max(longitudes);
        var longMin = _.min(longitudes);

        map.setCenter(new google.maps.LatLng(
            ((latMax + latMin) / 2.0),
            ((longMax + longMin) / 2.0)
        ));
        map.fitBounds(new google.maps.LatLngBounds(
            //bottom left
            new google.maps.LatLng(latMin, longMin),
            //top right
            new google.maps.LatLng(latMax, longMax)
        ));

        _.each(airports, function(airport) {
            addMarker(map, airport);
        });
    });
}

function addMarker(map, airport) {
    var marker = new google.maps.Marker({
        map: map,
        data: airport.id + "",
        icon: {
            path: fontawesome.markers.BUILDING,
            scale: 0.5,
            strokeWeight: 0.2,
            strokeColor: 'black',
            strokeOpacity: 1,
            fillColor: '#000000',
            fillOpacity: 1
        },
        clickable: true,
        position: {
            lat: airport.latitude,
            lng: airport.longitude
        },
        title: airport.name
    });
    marker.addListener('click', function(marker) {
        new AirportController(map, airport);
    });
}
