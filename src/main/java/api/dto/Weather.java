package api.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    @JsonProperty("id")
    private int id;
    @JsonProperty("main")
    private String main;
    @JsonProperty("description")
    private String description;
    @JsonProperty("icon")
    private String icon;
//    @JsonProperty("temp")
//    private float temp;
//    @JsonProperty("visibility")
//    private int visibility;
//    @JsonProperty("speed")
//    private float windSpeed;
//    @JsonProperty("gust")
//    private float windGust;
//    @JsonProperty("clouds")
//    private int clouds;
//    @JsonProperty("dt")
//    private long dateTime;
//    @JsonProperty("country")
//    private String country;
//    @JsonProperty("sunrise")
//    private long sunrise;
//    @JsonProperty("sunset")
//    private long sunset;


}
