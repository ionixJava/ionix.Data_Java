package ionix.Data;


import ionix.Utils.CachedTypes;
import ionix.Utils.Ext;
import ionix.Utils.Ref;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;

public abstract class DbValueSetter {
    private static final HashSet<Class> withQuotes;

    static {
        HashSet<Class> temp = new HashSet<>();
        temp.add(CachedTypes.String);
        temp.add(CachedTypes.Date);
        temp.add(CachedTypes.UUID);
        withQuotes = temp;
    }

    public abstract char getPrefix();

    public void setColumnValue(SqlQuery query, FieldMetaData metaData, Object entity) {
        SchemaInfo schema = metaData.getSchema();
        Field field = metaData.getField();
        Object parValue;
        try {
            parValue = field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        StringBuilder text = query.getText();
        if (!Ext.isNullOrEmpty(schema.getDefaultValue())){
            Object defaultValue = Ref.getDefault(field.getDeclaringClass());
            if (Ext.equals(defaultValue, parValue)){//Eğer Property Değeri Default Haldeyse yazdır Bunu
                text.append(schema.getDefaultValue());
                return;
            }
        }

        switch (schema.getSqlValueType()){
            case Parameterized:
                text.append(this.getPrefix());
                text.append(metaData.)
                break;
        }
    }
}
