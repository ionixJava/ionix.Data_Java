package ionix.Data;


import ionix.Utils.Ext;
import ionix.Utils.Triplet;

import java.util.HashSet;
import java.util.List;

public class BatchCommandDelete<TEntity> extends BatchCommandExecuteBase<TEntity> {

    public BatchCommandDelete(Class<TEntity> entityClass, TransactionalDbAccess dataAccess){
        super(entityClass, dataAccess);
    }

    @Override
    public int[] execute(Iterable<TEntity> entityList, EntityMetaDataProvider provider) {
        if (Ext.isEmptyList(entityList))
            return  null;

        List<TEntity> list = Ext.toList(entityList);

        final EntityMetaData metaData = provider.createEntityMetaData(this.getEntityClass());

        SqlQuery q = new SqlQuery("DELETE FROM ").sql(metaData.getTableName());

        SqlQuery keyTempQuery = SqlQueryHelper.createWhereSqlByKeys(metaData, list.get(0));
        final int keyMetaDataSize = keyTempQuery.getParameters().size();
        q.combine(keyTempQuery);

        SqlQuery templateQuery = SqlQuery.toQuery(q.getText().toString());

        for(TEntity entity : list) {
            SqlQuery justForParameters = SqlQueryHelper.createWhereSqlByKeys(metaData, entity);
            for (SqlQueryParameter par : justForParameters.getParameters()) {
                templateQuery.parameter(par.getValue());
            }
        }

        return this.getDataAccess().executeBatch(templateQuery, keyMetaDataSize);
    }



    public int[] delete(Iterable<TEntity> entityList, EntityMetaDataProvider provider) {
        return this.execute(entityList, provider);
    }
}
