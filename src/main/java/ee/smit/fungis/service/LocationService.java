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
import java.util.logging.Logger;

@Service
public class LocationService {

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(LocationService.class.getName());
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
        String message = String.format("Added new location at %1$s : %2$s, description: %3$s",
                dto.getGeometry().getCoordinates().get(0),
                dto.getGeometry().getCoordinates().get(1),
                location.getDescription());
        LOGGER.info(message);
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
            String message = String.format("Deleted location with id: %1$d", id);
            LOGGER.info(message);
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

        String description = getCleanString(dto.getProperties().get("description"));
        location.setDescription(description);

        try {
            repository.save(location);
            String message = String.format("Edited location at %1$s : %2$s, description: %3$s",
                    dto.getGeometry().getCoordinates().get(0),
                    dto.getGeometry().getCoordinates().get(1),
                    description);
            LOGGER.info(message);
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
            LOGGER.info("Validation failed because 'Type' is not 'point'");
            return false;
        }

        List<Double> coords = dto.getGeometry().getCoordinates();
        if (coords == null || coords.size() != 2) {
            LOGGER.info("Validation failed because coordinates are not valid");
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

        String description = getCleanString(dto.getProperties().get("description"));
        location.setDescription(description);
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

    /**
     * Remove all characters that aren't letters, numbers or punctuation from input string.
     * @param input input string.
     * @return clean string.
     */
    private String getCleanString(String input) {
        return input.replaceAll("[^\\p{L}\\p{N} .,!?()\\-]", "")
                .trim();
    }

}
