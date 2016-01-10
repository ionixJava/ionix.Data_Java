package ionix.Data;

import java.util.*;


public final class ExecuteSqlCompleteEventArgs extends PreExecuteSqlEventArgs{
    ExecuteSqlCompleteEventArgs(DbAccess dataAccess, SqlQuery query, Date executionStart, Date executionFinish, Exception executionException){
        super(dataAccess, query);

        this.executionStart = executionStart;
        this.executionFinish = executionFinish;
        this.executionException = executionException;
    }

    private final Date executionStart;
    public Date getExecutionStart(){
        return this.executionStart;
    }

    private final Date executionFinish;
    public Date getExecutionFinish(){
        return this.executionFinish;
    }

    public long getElapsed(){
        return this.executionFinish.getTime() - this.executionStart.getTime();
    }

    private final Exception executionException;
    public Exception getExecutionException(){
        return this.executionException;
    }

    public boolean isSucceeded(){
        return this.executionException == null;
    }
}
