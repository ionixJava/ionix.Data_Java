package ionix.Data;

import java.sql.Connection;

public enum IsolationLevel {
    TRANSACTION_NONE(Connection.TRANSACTION_NONE),
    TRANSACTION_READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
    TRANSACTION_READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
    TRANSACTION_REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
    TRANSACTION_SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

    public static IsolationLevel fromValue(int value) {
        switch (value) {
            case Connection.TRANSACTION_NONE:
                return TRANSACTION_NONE;
            case Connection.TRANSACTION_READ_COMMITTED:
                return TRANSACTION_READ_COMMITTED;
            case Connection.TRANSACTION_READ_UNCOMMITTED:
                return TRANSACTION_READ_UNCOMMITTED;
            case Connection.TRANSACTION_REPEATABLE_READ:
                return TRANSACTION_REPEATABLE_READ;
            case Connection.TRANSACTION_SERIALIZABLE:
                return TRANSACTION_SERIALIZABLE;
            default:
                throw new UnsupportedOperationException("Can not convert " + value + " to IsolationLevel");
        }
    }



    private final int value;
    IsolationLevel(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
