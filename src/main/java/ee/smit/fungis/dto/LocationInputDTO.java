package ee.smit.fungis.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LocationInputDTO {
    private Geometry geometry;
    private Map<String, String> properties;
}

