package ionix.Data;


import ionix.Utils.Ext;
import ionix.Utils.Triplet;

import java.util.HashSet;
import java.util.List;

public abstract class BatchCommandInsert<TEntity> extends BatchCommandExecuteBase<TEntity> {

    protected BatchCommandInsert(Class<TEntity> entityClass, TransactionalDbAccess dataAccess){
        super(entityClass, dataAccess);
    }

    @Override
    public int[] execute(Iterable<TEntity> entityList, EntityMetaDataProvider provider) {
        if (Ext.isEmptyList(entityList))
            return  null;

        List<TEntity> list = Ext.toList(entityList);

        final EntityMetaData metaData = provider.createEntityMetaData(this.getEntityClass());
        EntitySqlQueryBuilderInsert builder = this.createInsertBuilder();
        builder.setInsertFields(this.insertFields);
        EntitySqlQueryBuilderResult result = builder.createQuery(list.get(0), metaData);
        SqlQuery templateQuery = SqlQuery.toQuery(result.Query.getText().toString());

        for(TEntity entity : list){
            result = builder.createQuery(entity, provider.createEntityMetaData(this.getEntityClass()));
            for(SqlQueryParameter par : result.Query.getParameters()){
                templateQuery.parameter(par.getValue());
            }
        }

        return this.getDataAccess().executeBatch(templateQuery, result.DivideFactor);
    }

    private HashSet<String> insertFields;
    protected HashSet<String> getInsertFields(){
        return this.insertFields;
    }
    public void setInsertFields(HashSet<String> updatedFields) {
        this.insertFields = updatedFields;
    }


    public int[] insert(Iterable<TEntity> entityList, EntityMetaDataProvider provider) {
        return this.execute(entityList, provider);
    }

    protected abstract EntitySqlQueryBuilderInsert createInsertBuilder();
}
