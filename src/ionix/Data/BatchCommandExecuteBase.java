package ionix.Data;


public abstract class BatchCommandExecuteBase<TEntity> extends EntityCommandBase<TEntity> implements BatchCommandExecute<TEntity> {

    protected BatchCommandExecuteBase(Class<TEntity> entityClass, TransactionalDbAccess dataAccess){
        super(entityClass, dataAccess);
    }

    @Override
    public abstract int[] execute(Iterable<TEntity> entityList, EntityMetaDataProvider provider);

    @Override
    public TransactionalDbAccess getDataAccess(){
        return (TransactionalDbAccess) super.getDataAccess();
    }
}
