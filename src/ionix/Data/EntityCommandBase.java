package ionix.Data;


public abstract class EntityCommandBase<TEntity> {
    protected EntityCommandBase(Class<TEntity> entityClass, DbAccess dataAccess){
        if (null == dataAccess)
            throw new IllegalArgumentException("dataAccess is  null");

        this.entityClass = entityClass;
        this.dataAccess = dataAccess;
    }

    private final Class<?> entityClass;
    public Class<?> getEntityClass(){
        return this.entityClass;
    }

    private final DbAccess dataAccess;
    public DbAccess getDataAccess(){
        return this.dataAccess;
    }
}
