package ionix.Data;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class TransactionalDbAccess extends DbAccess implements DbTransaction {
    public TransactionalDbAccess(Connection conn) {
        super(conn);
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IsolationLevel getIsolationLevel() {
        try {
            return IsolationLevel.fromValue(this.getConnection().getTransactionIsolation());
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
    }

    @Override
    public void commit() {
        try {
            this.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rollBack() {
        try {
            this.getConnection().rollback();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void rollBack(Savepoint savepoint){
        try {
            this.getConnection().rollback(savepoint);
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
    }
}
