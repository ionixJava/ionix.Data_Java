package ionix.Data;


import java.sql.Connection;

public class TransactionalDbAccess extends DbAccess implements DbTransaction {
    public TransactionalDbAccess(Connection conn) {
        super(conn);
    }
}
