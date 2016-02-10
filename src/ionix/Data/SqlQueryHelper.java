package ionix.Data;


import ionix.Utils.Ext;

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
}
