package ionix.Data;


public abstract class EntityCommandExecute {

    public EntityCommandExecute(DbAccess dataAccess){
        if (null == dataAccess)
            throw new IllegalArgumentException("dataAccess is  null");

        this.dataAccess = dataAccess;
    }

    private final DbAccess dataAccess;
    public DbAccess getDataAccess(){
        return this.dataAccess;
    }

    public abstract <TEntity>  int execute(TEntity entity, EntityMetaDataProvider provider);
}
