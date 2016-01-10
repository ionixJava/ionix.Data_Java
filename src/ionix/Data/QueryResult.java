package ionix.Data;

import java.sql.ResultSet;
import java.sql.Statement;

public final class QueryResult implements AutoCloseable{
    private final Statement statement;
    public Statement getStatement(){
        return this.statement;
    }

    private final ResultSet resultSet;
    public ResultSet getResultSet(){
        return this.resultSet;
    }

    public QueryResult(Statement statement,ResultSet resultSet){
        this.statement = statement;
        this.resultSet = resultSet;
    }

    @Override
    public void close()  {
        try {
            if (null != this.resultSet)
                this.resultSet.close();
            if (null != this.statement)
                this.statement.close();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
