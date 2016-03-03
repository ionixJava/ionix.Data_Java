package ionix.Data;


import ionix.Utils.*;

import java.util.HashSet;

public abstract class EntityCommandInsert<TEntity> extends EntityCommandExecute<TEntity> {

    protected EntityCommandInsert(Class<TEntity> entityClass, DbAccess dataAccess) {
        super(entityClass, dataAccess);
    }


    @Override
    public int execute(TEntity entity, EntityMetaDataProvider provider) {
        EntitySqlQueryBuilderInsert builder = this.createInsertBuilder();
        builder.setInsertFields(this.insertFields);

        Triplet<SqlQuery, AutoGeneratedKey, FieldMetaData> result = builder.createQuery(entity, provider.createEntityMetaData(this.getEntityClass()));
        AutoGeneratedKey autoGeneratedKey = result.Item2;
        ExecuteResult er = this.getDataAccess().executeUpdate(result.Item1, autoGeneratedKey);
        if (autoGeneratedKey == AutoGeneratedKey.RETURN_GENERATED_KEYS) {
            FieldMetaData identity = result.Item3;
            Ref.setValueSafely(identity.getField(), entity, er.getGeneratedKey());
        }

        return er.getValue();
    }

    private HashSet<String> insertFields;
    protected HashSet<String> getInsertFields(){
        return this.insertFields;
    }
    public void setInsertFields(HashSet<String> insertFields) {
        this.insertFields = insertFields;
    }

    public int insert(TEntity entity, EntityMetaDataProvider provider) {
        return this.execute(entity, provider);
    }

    protected abstract EntitySqlQueryBuilderInsert createInsertBuilder();
}

