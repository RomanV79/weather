package web.dto;

import api.dto.ForecastDto;
import api.dto.Sys;
import api.dto.Weather;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Slf4j
public class ConverterDto {

    public static ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<ForecastDto, ForecastWebDto> typeMap = modelMapper.createTypeMap(ForecastDto.class, ForecastWebDto.class);
        typeMap.addMappings(mapper -> mapper.skip(ForecastWebDto::setLocationId));

        Converter<Sys, String> toLocalTimeSunrise = new AbstractConverter<Sys, String>() {
            @Override
            protected String convert(Sys sys) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
                Date date = Date.from(Instant.ofEpochSecond(sys.getSunrise()));
                LocalDateTime localDateTime = Instant.ofEpochMilli(date.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                return localDateTime.format(formatter);
            }
        };
        typeMap.addMappings(mapper -> mapper.using(toLocalTimeSunrise).map(ForecastDto::getSys, ForecastWebDto::setSysSunrise));

        Converter<Sys, String> toLocalTimeSunset = new AbstractConverter<Sys, String>() {
            @Override
            protected String convert(Sys sys) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
                Date date = Date.from(Instant.ofEpochSecond(sys.getSunset()));
                LocalDateTime localDateTime = Instant.ofEpochMilli(date.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                return localDateTime.format(formatter);
            }
        };
        typeMap.addMappings(mapper -> mapper.using(toLocalTimeSunset).map(ForecastDto::getSys, ForecastWebDto::setSysSunset));

        Converter<List<Weather>, String> fromWeatherToDescription = new AbstractConverter<List<Weather>, String>() {
            @Override
            protected String convert(List<Weather> weathers) {
                return weathers.get(0).getDescription();
            }
        };

        typeMap.addMappings(mapper -> mapper.using(fromWeatherToDescription).map(ForecastDto::getWeatherList, ForecastWebDto::setDescription));

        return modelMapper;
    }

    public ForecastWebDto fromForecastDtoToForecastWeb(ForecastDto forecastDto) {
        ModelMapper modelMapper = getModelMapper();
        ForecastWebDto webDto = modelMapper.map(forecastDto, ForecastWebDto.class);
        log.info("Convert ApiDTo to WebDto -> {}", webDto);

        return webDto;
    }


}
