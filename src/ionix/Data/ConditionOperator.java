package ionix.Data;


public enum ConditionOperator {
    Equals(0),
    NotEquals(1),
    GreaterThan(2),
    LessThan(3),
    GreaterThanOrEqualsTo(4),
    LessThanOrEqualsTo(5),
    In(6),
    Between(7),
    Contains(8),
    StartsWith(9),
    EndsWith(10);

    private final int value;
    ConditionOperator(int value){
        this.value = value;
    }

    public final int getValue(){
        return this.value;
    }
}
