package ee.smit.fungis.service;

import ee.smit.fungis.entity.Location;
import ee.smit.fungis.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * Get all saved locations.
     * @return locations in a list.
     */
    public List<Location> getAllLocations() {
        return repository.findAll();
    }



}
