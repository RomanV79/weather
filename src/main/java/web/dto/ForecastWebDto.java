package web.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
    private String timezone;
    private String city;

    public void fromUnixTimeToLocalTime() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        long zoneValue= Long.parseLong(this.timezone) / 3600;

        String zone;
        if (zoneValue > 0 ) {
            zone = "GMT+" + zoneValue;
        } else {
            zone = "GMT" + zoneValue;
        }

        this.sysSunrise = Instant.ofEpochSecond(Long.parseLong(this.getSysSunrise()))
                .atZone(ZoneId.of(zone))
                .format(formatter);

        this.sysSunset = Instant.ofEpochSecond(Long.parseLong(this.getSysSunset()))
                .atZone(ZoneId.of(zone))
                .format(formatter);
    }
}
