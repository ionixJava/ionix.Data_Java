package ionix.Data;

import ionix.Annotation.Table;
import ionix.Utils.Ext;
import ionix.Utils.Ref;


public final class ExtAnnotation {

    public static String getTableName(Class entityCls)
    {
        if (null == entityCls)
            throw new IllegalArgumentException("entityClass is null");

        Table ta = Ref.getAnnotation(entityCls, Table.class);
        if (null != ta)
            return Ext.isNullOrEmpty(ta.schema()) ? ta.name() : ta.schema() + '.' + ta.name();

        return entityCls.getTypeName();
    }

}
