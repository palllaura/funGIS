package ee.smit.fungis;

import ee.smit.fungis.repository.LocationRepository;
import ee.smit.fungis.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;

@SpringBootTest
class LocationServiceTests {

	private LocationService service;
	private LocationRepository repository;

	@BeforeEach
	void setup() {
		repository = mock(LocationRepository.class);
		service = new LocationService(repository);
	}

}
