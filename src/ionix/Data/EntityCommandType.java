package ionix.Data;


public enum EntityCommandType {
    Update(0),
    Insert(1),
    Delete(2);

    private final int value;
    EntityCommandType(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
