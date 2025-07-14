package ee.smit.fungis;

import ee.smit.fungis.controller.LocationController;
import ee.smit.fungis.entity.Location;
import ee.smit.fungis.service.LocationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class LocationControllerTests {

    private LocationService service;
    private LocationController controller;

    @BeforeEach
    void setup() {
        service = mock(LocationService.class);
        controller = new LocationController(service);
    }

    /**
     * Helper method to create a location.
     * @return location object.
     */
    private Location createTestLocation() {
        Location location = new Location();
        location.setId(555L);

        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = factory.createPoint(new Coordinate(1111, 2222));
        location.setCoordinates(point);

        location.setDescription("Exactly 3 chanterelles");

        return location;
    }

    @Test
    void testGetAllLocationsTriggersCorrectMethodInService() {
        controller.getAllLocations();
        verify(service, times(1)).getAllLocations();
    }

    @Test
    void testGetAllLocationsCorrectOutput() {
        Location location = createTestLocation();
        when(service.getAllLocations()).thenReturn(List.of(location));

        Map<String, Object> result = controller.getAllLocations();

        Assertions.assertEquals("FeatureCollection", result.get("type"));

        List<Map<String, Object>> features = (List<Map<String, Object>>) result.get("features");
        Assertions.assertEquals(1, features.size());

        Map<String, Object> feature = features.getFirst();
        Assertions.assertEquals("Feature", feature.get("type"));

        Map<String, Object> geometry = (Map<String, Object>) feature.get("geometry");
        Assertions.assertEquals("Point", geometry.get("type"));
        Assertions.assertEquals(List.of(1111.0, 2222.0), geometry.get("coordinates"));

        Map<String, Object> properties = (Map<String, Object>) feature.get("properties");
        Assertions.assertEquals(555L, properties.get("id"));
        Assertions.assertEquals("Exactly 3 chanterelles", properties.get("description"));
    }

}
