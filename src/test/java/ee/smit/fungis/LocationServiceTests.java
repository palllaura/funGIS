package ee.smit.fungis;

import ee.smit.fungis.dto.LocationInputDTO;
import ee.smit.fungis.entity.Location;
import ee.smit.fungis.repository.LocationRepository;
import ee.smit.fungis.service.LocationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class LocationServiceTests {

	private LocationService service;
	private LocationRepository repository;

	@BeforeEach
	void setup() {
		repository = mock(LocationRepository.class);
		service = new LocationService(repository);
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

	/**
	 * Helper method to create a correct LocationInputDto.
	 * @return dto.
	 */
	private LocationInputDTO createCorrectInputDto() {
		LocationInputDTO dto = new LocationInputDTO();
		dto.setType("Point");
		dto.setCoordinates(List.of(1111.0, 2222.0));
		dto.setDescription("One huge portobello");
		return dto;
	}


	@Test
	void testGetAllLocationsTriggersCorrectMethodInRepository() {
		service.getAllLocations();
		verify(repository, times(1)).findAll();
	}

	@Test
	void testGetAllLocationsReturnsEmptyListWhenNoLocations() {
		when(repository.findAll()).thenReturn(List.of());
		List<Location> result = service.getAllLocations();
		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	void testGetAllLocationsReturnsCorrectLocations() {
		Location location1 = createTestLocation();
		Location location2 = createTestLocation();

		when(repository.findAll()).thenReturn(List.of(location1, location2));
		List<Location> result = service.getAllLocations();
		Assertions.assertEquals(List.of(location1, location2), result);
	}

	@Test
	void testAddLocationCorrectTriggersSaveInRepository() {
		LocationInputDTO dto = createCorrectInputDto();
		boolean result = service.addLocation(dto);
		verify(repository, times(1)).save(any(Location.class));
		Assertions.assertTrue(result);
	}

	@Test
	void testAddLocationFailsGeometryTypeIsMissing() {
		LocationInputDTO dto = createCorrectInputDto();
		dto.setType(null);
		boolean result = service.addLocation(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddLocationFailsGeometryTypeIsIncorrect() {
		LocationInputDTO dto = createCorrectInputDto();
		dto.setType("");
		boolean result = service.addLocation(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddLocationFailsCoordinatesAreMissing() {
		LocationInputDTO dto = createCorrectInputDto();
		dto.setCoordinates(null);
		boolean result = service.addLocation(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testAddLocationFailsCoordinatesAreIncorrect() {
		LocationInputDTO dto = createCorrectInputDto();
		dto.setCoordinates(List.of(1111.0));
		boolean result = service.addLocation(dto);
		Assertions.assertFalse(result);
	}

	@Test
	void testDeleteLocationCorrectTriggersDeleteInRepository() {
		Long id = 555L;
		boolean result = service.deleteLocationById(id);
		verify(repository, times(1)).deleteById(id);
		Assertions.assertTrue(result);
	}

	@Test
	void testDeleteLocationByIdResultsFalseWhenNotFound() {
		Long id = 555L;
		doThrow(new EmptyResultDataAccessException(1)).when(repository).deleteById(id);
		boolean result = service.deleteLocationById(id);
		Assertions.assertFalse(result);
	}

	@Test
	void testEditLocationSuccess() {
		Long id = 555L;
		Location location = new Location();
		location.setId(id);

		LocationInputDTO dto = new LocationInputDTO();
		dto.setType("Point");
		dto.setCoordinates(List.of(25.0, 59.0));
		dto.setDescription("Updated description");

		when(repository.findById(id)).thenReturn(Optional.of(location));

		boolean result = service.editLocation(id, dto);

		Assertions.assertTrue(result);
		verify(repository).save(any(Location.class));
	}

	@Test
	void testEditLocationFailsOnInvalidInput() {
		LocationInputDTO dto = new LocationInputDTO();
		boolean result = service.editLocation(555L, dto);
		Assertions.assertFalse(result);
	}

}
