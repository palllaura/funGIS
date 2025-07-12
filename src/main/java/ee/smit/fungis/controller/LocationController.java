package ee.smit.fungis.controller;

import ee.smit.fungis.service.LocationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {
    private final LocationService service;

    /**
     * Location controller constructor.
     * @param service locationService.
     */
    public LocationController(LocationService service) {
        this.service = service;
    }
}
