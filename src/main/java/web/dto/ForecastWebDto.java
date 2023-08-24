package web.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ForecastWebDto {
    private long locationId;
    private String description;
    private float mainTemp;
    private float mainPressure;
    private int mainHumidity;
    private int visibility;
    private float windSpeed;
    private float windGust;
    private String sysSunrise;
    private String sysSunset;
    private String sysCountry;
    private String city;
}
