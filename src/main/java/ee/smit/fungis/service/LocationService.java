package ee.smit.fungis.service;

import ee.smit.fungis.dto.LocationInputDTO;
import ee.smit.fungis.entity.Location;
import ee.smit.fungis.repository.LocationRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.dao.EmptyResultDataAccessException;
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

    /**
     * Add new location to database.
     * @param dto input data.
     */
    public boolean addLocation(LocationInputDTO dto) {
        if (!"Point".equalsIgnoreCase(dto.getType())) {
            return false;
        }

        List<Double> coords = dto.getCoordinates();
        if (coords == null || coords.size() != 2) {
            return false;
        }

        Point point = new GeometryFactory(new PrecisionModel(), 4326)
                .createPoint(new Coordinate(coords.get(0), coords.get(1)));

        Location location = new Location();
        location.setCoordinates(point);
        location.setDescription(dto.getDescription());

        try {
            repository.save(location);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Delete location from database.
     * @param id ID of location to delete.
     * @return true if location was deleted, false if not found or error.
     */
    public boolean deleteLocationById(Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

}
