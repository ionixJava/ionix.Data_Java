package ionix.Data;


import ionix.Utils.*;

import java.util.*;

public class BatchCommandUpdate<TEntity> extends BatchCommandExecuteBase<TEntity> {

    public BatchCommandUpdate(Class<TEntity> entityClass, TransactionalDbAccess dataAccess){
        super(entityClass, dataAccess);
    }

    @Override
    public int[] execute(Iterable<TEntity> entityList, EntityMetaDataProvider provider) {
        if (Ext.isEmptyList(entityList))
            return  null;

        List<TEntity> list = Ext.toList(entityList);

        final EntityMetaData metaData = provider.createEntityMetaData(this.getEntityClass());
        EntitySqlQueryBuilderUpdate builder = new EntitySqlQueryBuilderUpdate();
        builder.setUpdatedFields(this.updatedFields);
        EntitySqlQueryBuilderResult result = builder.createQuery(list.get(0), metaData);
        SqlQuery templateQuery = SqlQuery.toQuery(result.Query.getText().toString());

        for(TEntity entity : entityList){
            result = builder.createQuery(entity, provider.createEntityMetaData(this.getEntityClass()));
            for(SqlQueryParameter par : result.Query.getParameters()){
                templateQuery.parameter(par.getValue());
            }
        }

        return this.getDataAccess().executeBatch(templateQuery, result.DivideFactor);
    }

    private HashSet<String> updatedFields;
    public void setUpdatedFields(HashSet<String> updatedFields) {
        this.updatedFields = updatedFields;
    }

    public int[] update(Iterable<TEntity> entityList, EntityMetaDataProvider provider) {
        return this.execute(entityList, provider);
    }
}
