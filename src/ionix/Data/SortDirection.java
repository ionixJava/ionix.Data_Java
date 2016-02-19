package ionix.Data;


public enum SortDirection {
    Asc(0),
    Desc(1);

    private final int value;
    SortDirection(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
