package ionix.Data;


public abstract class BatchCommandExecute<TEntity> extends EntityCommandBase<TEntity> {

    protected BatchCommandExecute(Class<TEntity> entityClass, TransactionalDbAccess dataAccess){
        super(entityClass, dataAccess);
    }

    public abstract int[] execute(Iterable<TEntity> entityList, EntityMetaDataProvider provider);

    @Override
    public TransactionalDbAccess getDataAccess(){
        return (TransactionalDbAccess) super.getDataAccess();
    }
}
