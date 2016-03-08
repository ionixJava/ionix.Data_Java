package ionix.Data;


import java.util.HashSet;

public class EntityCommandUpdate<TEntity> extends EntityCommandExecuteBase<TEntity> {

    public EntityCommandUpdate(Class<TEntity> entityClass, DbAccess dataAccess) {
        super(entityClass, dataAccess);
    }


    @Override
    public int execute(TEntity entity, EntityMetaDataProvider provider) {
        EntitySqlQueryBuilderUpdate builder = new EntitySqlQueryBuilderUpdate();
        builder.setUpdatedFields(this.updatedFields);

        EntitySqlQueryBuilderResult result = builder.createQuery(entity, provider.createEntityMetaData(this.getEntityClass()));
        return this.getDataAccess().executeUpdate(result.Query, result.Key).getValue();
    }

    private HashSet<String> updatedFields;

    public void setUpdatedFields(HashSet<String> updatedFields) {
        this.updatedFields = updatedFields;
    }

    public int update(TEntity entity, EntityMetaDataProvider provider) {
        return this.execute(entity, provider);
    }
}