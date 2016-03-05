package ionix.Data;


public interface CommandFactory {
    DbAccess getDataAccess();

    <TEntity> EntityCommandSelect<TEntity> createSelectCommand(Class<TEntity> cls);

    <TEntity> EntityCommandExecute<TEntity> createEntityCommand(Class<TEntity> cls, EntityCommandType cmdType);

    <TEntity> BatchCommandExecute<TEntity> createBatchCommand(Class<TEntity> cls, EntityCommandType cmdType);
}
