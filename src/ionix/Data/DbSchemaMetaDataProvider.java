package ionix.Data;

import ionix.Annotation.DbSchema;
import ionix.Utils.Ext;
import ionix.Utils.Ref;

import java.lang.reflect.Field;

/**
 * Created by mehme on 15.12.2015.
 */
public class DbSchemaMetaDataProvider extends EntityMetaDataProviderBase {
    @Override
    protected void setExtendedSchema(SchemaInfo schema, Field field) {
        DbSchema ann = Ref.getAnnotation(field, DbSchema.class);
        if (null != ann){
            if (!Ext.isNullOrEmpty(ann.columnName()))
                schema.setColumnName(ann.columnName());
            schema.setDatabaseGeneratedOption(ann.databaseGeneratedOption());
            schema.setDefaultValue(ann.defaultValue());
            schema.setIsKey(ann.isKey());
            schema.setMaxLength(ann.maxLength());
            schema.setOrder(ann.order());
            schema.setIsNullable(ann.isNullable());
            schema.setReadOnly(ann.readOnly());
            schema.setSqlValueType(ann.sqlValueType());
        }
    }
}
