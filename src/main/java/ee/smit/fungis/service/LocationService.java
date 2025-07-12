package ee.smit.fungis.service;

import ee.smit.fungis.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final LocationRepository repository;

    /**
     * Location service constructor.
     * @param repository locationRepository.
     */
    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }
}
