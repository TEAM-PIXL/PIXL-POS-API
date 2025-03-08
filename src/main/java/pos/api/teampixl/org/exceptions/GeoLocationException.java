package pos.api.teampixl.org.exceptions;

public class GeoLocationException extends Exception {
    public GeoLocationException(String message) {
        super(message);
    }

    public GeoLocationException(String message, Throwable cause) {
        super(message, cause);
    }
}