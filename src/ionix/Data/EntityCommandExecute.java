package ionix.Data;


public abstract class EntityCommandExecute<TEntity> extends EntityCommandBase<TEntity> {

    protected EntityCommandExecute(Class<TEntity> entityClass, DbAccess dataAccess){
        super(entityClass, dataAccess);
    }

    public abstract int execute(TEntity entity, EntityMetaDataProvider provider);
}
