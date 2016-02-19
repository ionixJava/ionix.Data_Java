package ionix.Data;


public interface SqlQueryProvider {
    void setRoot(SqlQuery seed);
    SqlQuery toQuery();
}
