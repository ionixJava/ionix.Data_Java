package ionix.Data;

public interface DbTransaction extends AutoCloseable {
    java.sql.Connection getConnection();
    IsolationLevel getIsolationLevel();
    void commit();
    void rollBack();
}
