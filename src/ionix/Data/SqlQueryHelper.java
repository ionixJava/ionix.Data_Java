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
}
