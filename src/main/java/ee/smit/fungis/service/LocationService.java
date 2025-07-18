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
import java.util.Optional;

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
        if (!validateLocationInput(dto)) {
            return false;
        }
        Location location = getLocationFromDto(dto);
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

    /**
     * Edit existing location.
     * @param id id of location.
     * @param dto dto with location data.
     * @return true if was edited, else false.
     */
    public boolean editLocation(Long id, LocationInputDTO dto) {
        if (!validateLocationInput(dto)) {
            return false;
        }
        Optional<Location> locationOptional = repository.findById(id);
        if (locationOptional.isEmpty()) {
            return false;
        }

        Location location = locationOptional.get();
        Point point = getPointFromCoordinates(dto.getGeometry().getCoordinates());
        location.setCoordinates(point);
        location.setDescription(dto.getProperties().get("description"));

        try {
            repository.save(location);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Validate location input fields.
     * @param dto dto to validate.
     * @return true if valid, else false.
     */
    private boolean validateLocationInput(LocationInputDTO dto) {
        if (!"Point".equalsIgnoreCase(dto.getGeometry().getType())) {
            System.out.println("type is not point");
            return false;
        }

        List<Double> coords = dto.getGeometry().getCoordinates();
        if (coords == null || coords.size() != 2) {
            System.out.println("coordinates not valid");
            return false;
        }
        return true;
    }

    /**
     * Create new Location from LocationInputDto.
     * @param dto LocationInputDto.
     * @return Location.
     */
    private Location getLocationFromDto(LocationInputDTO dto) {
        Location location = new Location();
        Point point = getPointFromCoordinates(dto.getGeometry().getCoordinates());
        location.setCoordinates(point);
        location.setDescription(dto.getProperties().get("description"));
        return location;
    }

    /**
     * Get point from coordinates.
     * @param coords List with coordinates.
     * @return point.
     */
    private Point getPointFromCoordinates(List<Double> coords) {
        return new GeometryFactory(new PrecisionModel(), 4326)
                .createPoint(new Coordinate(coords.get(0), coords.get(1)));
    }

}
