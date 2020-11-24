package exceptions;

public class ReadFromEmptyException extends RuntimeException {

    public ReadFromEmptyException(String message){
        super(message);
    }

}
