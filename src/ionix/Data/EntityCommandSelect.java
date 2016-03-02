package ionix.Data;


import ionix.Utils.Ref;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class EntityCommandSelect<TEntity> extends EntityCommandBase<TEntity> {

    public EntityCommandSelect(Class<TEntity> cls, DbAccess dataAccess){
        super(cls, dataAccess);
    }


    private boolean convertType;
    public boolean getConvertType(){return this.convertType;}
    public EntityCommandSelect setConvertType(boolean convertType){
        this.convertType = convertType;
        return this;
    }

    private enum MapType{
        Select,
        Query
    }

    private void map(TEntity entity, EntityMetaData metaData, QueryResult qr, MapType mapType){
        switch (mapType){
            case Select:
                for(FieldMetaData fd : metaData.getFields()){
                    String columnName = fd.getSchema().getColumnName();
                    Field field = fd.getField();
                    try {
                        Object dbValue = qr.getResultSet().getObject(columnName);
                        if (this.convertType)
                            Ref.setValueSafely(field, entity, dbValue);
                        else
                            field.set(entity, dbValue);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case Query:
                ResultSet rs = qr.getResultSet();
                try {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int fieldCount = rsmd.getColumnCount();
                    for (int j = 0; j < fieldCount; ++j) {
                        String columnName = rsmd.getColumnName(j + 1);
                        FieldMetaData fd = metaData.get(columnName);
                        if (null != fd) {
                            Field field = fd.getField();
                            Object dbValue = rs.getObject(j + 1);
                            if (this.convertType)
                                Ref.setValueSafely(field, entity, dbValue);
                            else
                                field.set(entity, dbValue);
                        }
                    }
                }
                catch (Exception e){
                    throw new RuntimeException(e);
                }
                break;
            default:
                throw new UnsupportedOperationException(mapType.toString());
        }
    }

    private TEntity readEntity(EntityMetaData metaData, SqlQuery query, MapType mapType) {
        TEntity entity = null;
        QueryResult result = null;
        try {
            result = this.getDataAccess().executeQuery(query);

            try {
                if (result.getResultSet().next()) {
                    entity = (TEntity)this.getEntityClass().newInstance();
                    this.map(entity, metaData, result, mapType);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            if (null != result)
                result.close();
        }
        return entity;
    }


    private List<TEntity> readEntityList(EntityMetaData metaData, SqlQuery query, MapType mapType)
    {
        ArrayList<TEntity> ret = new ArrayList<>();

        QueryResult qr = null;
        try
        {
            qr = this.getDataAccess().executeQuery(query);
            ResultSet rs = qr.getResultSet();

            try {
                while (rs.next()){
                    TEntity entity = (TEntity)this.getEntityClass().newInstance();
                    this.map(entity, metaData, qr, mapType);
                    ret.add(entity);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        finally
        {
            if (qr != null) qr.close();
        }

        return ret;
    }

    public TEntity selectSingle(EntityMetaDataProvider provider, SqlQuery extendedQuery)
    {
        EntityMetaData metaData = SqlQueryHelper.ensureCreateEntityMetaData(this.getEntityClass(), provider);

        SqlQueryProviderSelect builder = new SqlQueryProviderSelect(metaData);
        SqlQuery query = builder.toQuery();
        if (null != extendedQuery)
            query.combine(extendedQuery);

        return this.readEntity(metaData, query, MapType.Select);
    }

    public List<TEntity> select(EntityMetaDataProvider provider, SqlQuery extendedQuery){
        EntityMetaData metaData = SqlQueryHelper.ensureCreateEntityMetaData(this.getEntityClass(), provider);

        SqlQueryProviderSelect builder = new SqlQueryProviderSelect(metaData);
        SqlQuery query = builder.toQuery();
        if (null != extendedQuery)
            query.combine(extendedQuery);

        return this.readEntityList(metaData, query, MapType.Select);
    }

    public TEntity querySingle(EntityMetaDataProvider provider, SqlQuery query){
        if (null == query)
            throw new IllegalArgumentException ("query is null");

        EntityMetaData metaData = SqlQueryHelper.ensureCreateEntityMetaData(this.getEntityClass(), provider);

        return this.readEntity(metaData, query, MapType.Query);
    }

    public List<TEntity> query(EntityMetaDataProvider provider, SqlQuery query){
        if (null == query)
            throw new IllegalArgumentException ("query is null");

        EntityMetaData metaData = SqlQueryHelper.ensureCreateEntityMetaData(this.getEntityClass(), provider);

        return this.readEntityList(metaData, query, MapType.Query);
    }

    public TEntity selectById(EntityMetaDataProvider provider, Object... keys){
        if (null == keys || 0 == keys.length)
            throw new IllegalArgumentException("keys is null or empty");

        EntityMetaData metaData = SqlQueryHelper.ensureCreateEntityMetaData(this.getEntityClass(), provider);

        SqlQueryProviderSelect sp  = new SqlQueryProviderSelect(metaData);

        FilterCriteriaList filters = new FilterCriteriaList();
        filters.setRoot(sp.toQuery());

        List<FieldMetaData> keySchemas = SqlQueryHelper.ofKeys(metaData, true);//Order a göre geldiği için böyle.
        if (keySchemas.size() != keys.length)
            throw new RuntimeException("Keys and Valus count does not match");

        int index = -1;
        for(FieldMetaData keyField : keySchemas)
        {
            filters.add(keyField.getSchema().getColumnName(), ConditionOperator.Equals, keys[++index]);
        }

        SqlQuery query = filters.toQuery();

        return this.readEntity(metaData, query, MapType.Select);
    }
}
