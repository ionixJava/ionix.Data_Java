package ionix.Data;

import ionix.Utils.CachedTypes;
import java.lang.reflect.Field;


public final class ExtEntityMetaData {
    public static <TEntity> boolean isEntityValid(TEntity entity, EntityMetaDataProvider provider) {
        if (null != entity && null != provider) {
            for (FieldMetaData metaData : provider.createEntityMetaData(entity.getClass()).getFields()) {
                Field field = metaData.getField();
                SchemaInfo schema = metaData.getSchema();
                if (!schema.getIsNullable()) {
                    if (!field.getType().isPrimitive()) {
                        Object value;
                        try {
                            value = field.get(entity);
                            if (null == value) {
                                return false;
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                int maxLength = schema.getMaxLength();
                if (maxLength > 0) {
                    if (field.getType() == CachedTypes.String) {
                        Object value;
                        try {
                            value = field.get(entity);
                            if (null != value && value.toString().length() > maxLength)
                                return false;
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
