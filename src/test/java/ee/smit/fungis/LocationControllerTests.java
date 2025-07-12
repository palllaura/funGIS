package ee.smit.fungis;

import ee.smit.fungis.controller.LocationController;
import ee.smit.fungis.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;

@SpringBootTest
class LocationControllerTests {

    private LocationService service;
    private LocationController controller;

    @BeforeEach
    void setup() {
        service = mock(LocationService.class);
        controller = new LocationController(service);
    }
}
