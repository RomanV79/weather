package CustomException;

public class UserExistException extends Exception {
    public UserExistException(String message) {
        super(message);
    }
}
