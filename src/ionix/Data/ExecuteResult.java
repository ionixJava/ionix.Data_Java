package ionix.Data;

public class ExecuteResult {
    private final int returnValue;
    public int getValue(){
        return this.returnValue;
    }

    private final Object generatedKey;
    public Object getGeneratedKey(){
        return this.generatedKey;
    }

    public ExecuteResult(int returnValue, Object generatedKey){
        this.returnValue = returnValue;
        this.generatedKey = generatedKey;
    }
}
