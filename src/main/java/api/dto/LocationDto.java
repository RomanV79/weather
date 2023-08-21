package api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;


@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("local_names")
    private Map<String, String> localNames;

    @JsonProperty("lat")
    private Double latitude;

    @JsonProperty("lon")
    private Double longitude;

    @JsonProperty("country")
    String country;

    @JsonProperty("state")
    private String state;
}
