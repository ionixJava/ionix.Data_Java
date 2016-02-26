package ionix.Data;


public abstract class EntityCommandExecute {

    protected EntityCommandExecute(DbAccess dataAccess, Class<?> entityClass){
        if (null == dataAccess)
            throw new IllegalArgumentException("dataAccess is  null");

        this.dataAccess = dataAccess;
        this.entityClass = entityClass;
    }

    private final DbAccess dataAccess;
    public DbAccess getDataAccess(){
        return this.dataAccess;
    }

    private final Class<?> entityClass;
    public Class<?> getEntityClass(){
        return this.entityClass;
    }

    public abstract <TEntity>  int execute(TEntity entity, EntityMetaDataProvider provider);
}
