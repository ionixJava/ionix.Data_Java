package ionix.Data;


public interface CommandFactory {
    DbAccess getDataAccess();

    EntityCommandSelect createSelectCommand();

    EntityCommandExecute createEntityCommand();
}
