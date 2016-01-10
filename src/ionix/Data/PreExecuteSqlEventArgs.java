package ionix.Data;

/**
 * Created by mehme on 31.12.2015.
 */
public class PreExecuteSqlEventArgs {
    PreExecuteSqlEventArgs(DbAccess dataAccess, SqlQuery query)
    {
        this.dataAccess = dataAccess;
        this.query = query;
    }

    private final DbAccess dataAccess;
    public DbAccess getDataAccess() { return this.dataAccess; }

    private final SqlQuery query;
    public SqlQuery getQuery() { return this.query; }
}

