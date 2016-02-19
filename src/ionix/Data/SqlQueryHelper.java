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
}
