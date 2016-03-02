package ionix.Data;


import ionix.Utils.Ext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Date;

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
    public void setsolationLevel(IsolationLevel level){
        if (null == level)
            throw new IllegalArgumentException("level");
        try {
            this.getConnection().setTransactionIsolation(level.getValue());
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


    //Template Query de Bütün parametreler var. Yani barch ile eklenen tüm parametreler
    public int[] executeBatch(SqlQuery templateQuery, EntityMetaData metaData){
        if (null == templateQuery || templateQuery.isEmpty())
            throw new IllegalArgumentException("templateQuery");
        if (null == metaData)
            throw new IllegalArgumentException("metaData");

        int[] ret = null;
        PreparedStatement ps = null;
        Exception ex = null;
        Date now = new Date();
        try {
            ps = this.prepareStatement(SqlQuery.toQuery(templateQuery.getText().toString()), null);
            final int size = templateQuery.getParameters().size() / metaData.size();// Her zaman aklansız bölünüyor.
            SqlQueryParameterList parameters = templateQuery.getParameters();
            for(int j = 0; j < size; ++j){
                for(int k = 0; k < parameters.size(); ++k){
                    ps.setObject(k + 1, parameters.get(j * size + k));
                }
                ps.addBatch();
            }

            ret = ps.executeBatch();
        } catch (Exception e) {
            ex = e;
            this.onCatch(e);
        }
        finally {
            this.onFinally(ps,templateQuery,now,ex);
        }


        return ret;
    }
}
