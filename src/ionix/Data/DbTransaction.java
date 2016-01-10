package ionix.Data;


import java.sql.Connection;

public interface DbTransaction {
    java.sql.Connection getConnection();
    //IsolationLevel IsolationLevel { get; }

    void commit();
    void rollback();
}
