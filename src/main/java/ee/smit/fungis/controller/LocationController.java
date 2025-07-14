package ee.smit.fungis.controller;

import ee.smit.fungis.dto.LocationInputDTO;
import ee.smit.fungis.entity.Location;
import ee.smit.fungis.service.LocationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class LocationController {
    private final LocationService service;

    /**
     * Location controller constructor.
     * @param service locationService.
     */
    public LocationController(LocationService service) {
        this.service = service;
    }

    /**
     * Get all locations from the database.
     * @return map of locations.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getAllLocations() {
        List<Location> locations = service.getAllLocations();

        List<Map<String, Object>> features = locations.stream().map(location -> {
            Map<String, Object> geometry = Map.of(
                    "type", "Point",
                    "coordinates", List.of(
                            location.getCoordinates().getX(),
                            location.getCoordinates().getY()
                    )
            );

            Map<String, Object> properties = Map.of(
                    "id", location.getId(),
                    "description", location.getDescription()
            );

            return Map.of(
                    "type", "Feature",
                    "geometry", geometry,
                    "properties", properties
            );
        }).toList();

        return Map.of(
                "type", "FeatureCollection",
                "features", features
        );
    }

    /**
     * Add new location.
     * @param input dto with location data.
     * @return response entity with result message.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addLocation(@RequestBody LocationInputDTO input) {
        if (service.addLocation(input)) {
            return ResponseEntity.ok(Map.of(
                    "message", "Location added successfully!"
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Failed to save location."
            ));
        }
    }

}
