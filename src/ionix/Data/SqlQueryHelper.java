package ionix.Data;


import ionix.Utils.Ext;

import java.util.*;

public final class SqlQueryHelper {

    public static void ensureEntityMetaData(EntityMetaData metaData)
    {
        if (null == metaData)
            throw new NullPointerException("metaData");
        if (Ext.isEmptyList(metaData.getFields()))
            throw new NullPointerException("metaData.getFields()");
        if (Ext.isNullOrEmpty(metaData.getTableName()))
            throw new NullPointerException("IEntityMetaData.TableName");
    }

    public static <TEntity> EntityMetaData ensureCreateEntityMetaData(Class<TEntity> cls, EntityMetaDataProvider provider)
    {
        if (null == provider)
            throw new IllegalArgumentException("provider is null");

        EntityMetaData ret = provider.createEntityMetaData(cls);
        ensureEntityMetaData(ret);

        return ret;
    }


    //SelectById ile Kullanılıyor.
    public static List<FieldMetaData> ofKeys(EntityMetaData metaData, boolean throwExcIfNoKeys)
    {
        ArrayList<FieldMetaData> keys = new ArrayList<>();
        metaData.getFields().forEach((field)-> {
            if (field.getSchema().getIsKey())
                keys.add(field);
        });
        if (keys.size() > 0)
        {
            Comparator<FieldMetaData> c = (f1, f2)-> {
                Integer f1Order = f1.getSchema().getOrder();
                return f1Order.compareTo(f2.getSchema().getOrder());
            };
            keys.sort(c);

            return keys;
        }
        else
        {
            if (throwExcIfNoKeys)
                throw new KeySchemaNotFoundException();
            return null;
        }
    }

    public static void setColumnValue(DbValueSetter setter, SqlQuery query, FieldMetaData metaData, Object entity)
    {
        setter.setColumnValue(query, metaData, entity);
    }

    public static SqlQuery createWhereSqlByKeys(EntityMetaData metaData, Object entity)
    {
        if (null == entity)
            throw new IllegalArgumentException("entity is null");

        List<FieldMetaData> keySchemas = ofKeys(metaData, true);

        Object[] keyValues = new Object[keySchemas.size()];
        for (int j = 0; j < keySchemas.size(); ++j)
        {
            try {
                keyValues[j] = keySchemas.get(j).getField().get(entity);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        FilterCriteriaList list = new FilterCriteriaList();
        for (int j = 0; j < keySchemas.size(); ++j)
        {
            FieldMetaData keySchema = keySchemas.get(j);

            list.add(keySchema.getSchema().getColumnName(), ConditionOperator.Equals, keyValues[j]);
        }
        return list.toQuery();
    }

    public static String toParameterlessQuery(SqlQuery query){
        if (null != query){
          Object[] arr = new Object[query.getParameters().size()];
            for(int j  =0; j < arr.length;++ j){
                Object parValue = query.getParameters().get(j).getValue();
                if (null != parValue){
                    if (DbValueSetter.withQuotes.contains(parValue.getClass()))
                        parValue = "'" + parValue.toString() + "'";
                    arr[j] = parValue;
                }
                else
                    arr[j] = "NULL";
            }

            String sql = query.getText().toString();
            sql = sql.replace("?", "%s");
            sql = String.format(sql, arr);

            return sql;
        }
        return "";
    }
}
