package ionix.Data;

import ionix.Annotation.*;
import ionix.Utils.Ref;
import java.lang.reflect.Field;
import java.util.*;

//template method pattern.
public abstract class EntityMetaDataProviderBase implements EntityMetaDataProvider {
    private static final Map<Class, HashMap<Class, EntityMetaData>> _cache = new HashMap<>();

    protected boolean isMapped(Field field) {
        if (Ref.getAnnotation(field, NotMapped.class) != null)
            return false;

        if (Ref.isFieldIterable(field))
            return false;

        return true;
    }

    protected abstract void setExtendedSchema(SchemaInfo schema, Field field);

    protected void setExtendedMetaData(EntityMetaData metaData) {
    }

    private SchemaInfo fromField(Field field) {
        if (!this.isMapped(field))
            return null;

        Class<?> fieldCls = field.getType();

        SchemaInfo schema = new SchemaInfo(field.getName());
        schema.setDataClass(fieldCls);

        //NotMapped gibi bir standart
        Key keyAnn = Ref.getAnnotation(field, Key.class);
        if (null != keyAnn)
            schema.setIsKey(true);

        boolean isPrimitiveTypeDetected = fieldCls.isPrimitive();
        schema.setIsNullable(isPrimitiveTypeDetected);

        try {
            field.getDeclaringClass().getDeclaredMethod("set" + field.getName());//Burayı Test Et.
        } catch (NoSuchMethodException e) {
            schema.setReadOnly(true);
        }

        this.setExtendedSchema(schema, field);

        return schema;
    }


    public synchronized EntityMetaData createEntityMetaData(Class entityClass) {
        Class derivedClass = this.getClass();
        HashMap<Class, EntityMetaData> tempCache = _cache.get(derivedClass);
        if (null == tempCache) {
            tempCache = new HashMap<>();
            _cache.put(derivedClass, tempCache);
        }

        EntityMetaData metaData = tempCache.get(entityClass);
        if (null == metaData) {
            EntityMetaData temp = new EntityMetaData(entityClass);
            for (Field field : entityClass.getDeclaredFields()) {
                SchemaInfo schema = this.fromField(field);
                if (null == schema) //NotMapped.
                    continue;
                schema.lock();
                temp.add(schema, field);
                field.setAccessible(true);//ki verilere metadata üzerinden erişilsin.
            }

            this.setExtendedMetaData(temp);

            tempCache.put(entityClass, temp);
            metaData = temp;
        }

        return metaData;
    }
}