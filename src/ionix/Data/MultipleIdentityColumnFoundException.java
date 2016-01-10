package ionix.Data;

public class MultipleIdentityColumnFoundException extends RuntimeException {

    public MultipleIdentityColumnFoundException(String message){
        super(message);
    }

    public MultipleIdentityColumnFoundException(Object entity){
        super("Multiple Identiy Column Found in " + entity.getClass().getTypeName());

    }
}