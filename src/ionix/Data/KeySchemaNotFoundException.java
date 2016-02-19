package ionix.Data;


public class KeySchemaNotFoundException extends RuntimeException {
    public KeySchemaNotFoundException() {
        super("Key Schema Not Found");
    }
}
