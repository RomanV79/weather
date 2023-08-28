package CustomException;

public class OpenApiWeatherErrorException extends Exception {
    public OpenApiWeatherErrorException(String message) {
        super(message);
    }
}
