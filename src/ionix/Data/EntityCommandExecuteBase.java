package ionix.Data;


public abstract class EntityCommandExecuteBase<TEntity> extends EntityCommandBase<TEntity> implements EntityCommandExecute<TEntity> {

    protected EntityCommandExecuteBase(Class<TEntity> entityClass, DbAccess dataAccess){
        super(entityClass, dataAccess);
    }

    @Override
    public abstract int execute(TEntity entity, EntityMetaDataProvider provider);
}
