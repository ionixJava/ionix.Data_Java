package ionix.Data;


import ionix.Utils.Ext;
import java.lang.reflect.Field;
import java.util.*;

public class EntityMetaData {

    private final Class entityClass;
    private List<FieldMetaData> list;//Set ve Map gibi listeler sıralı getirmiyor field leri o yüzden bu şekilde oldu
    private final String tableName;

    public EntityMetaData(Class entityClass, String tableName) {
        if (null == entityClass)
            throw new IllegalArgumentException("entityClass is null");
        if (Ext.isNullOrEmpty(tableName))
            throw new IllegalArgumentException("tableName is null or empty");

        this.entityClass = entityClass;
        this.list = new ArrayList<>();
        this.tableName = tableName;
    }

    public EntityMetaData(Class entityClass) {
        this(entityClass, ExtAnnotation.getTableName(entityClass));
    }

    void add(SchemaInfo schema, Field field) {
        this.list.add(new FieldMetaData(schema, field));
    }


    public String getTableName() {
        return this.tableName == null ? "" : this.tableName;
    }

    public Class getEntityClass() {
        return this.entityClass;
    }

    public Iterable<FieldMetaData> getFields() {
        return this.list;
    }

    public int size() {
        return this.list.size();
    }

    @Override
    public EntityMetaData clone() {
        EntityMetaData copy = new EntityMetaData(this.entityClass, this.tableName);//Type ReadOnly bir object dir. String de fixed char* kullanılmıyorsa immutable bir nesnedir.
        for (FieldMetaData orginal : this.list) {
            copy.list.add(orginal.clone());
        }

        return copy;
    }

    @Override
    public String toString() {
        return this.tableName;
    }

    private HashMap<String, FieldMetaData> dic;
    private synchronized HashMap<String, FieldMetaData> getDic() {
        if (null == this.dic) {
            this.dic = new HashMap<>(this.list.size());
            for (FieldMetaData item : this.list)
                this.dic.put(item.getSchema().getColumnName(), item);
            this.list = Collections.unmodifiableList(this.list);
        }

        return this.dic;
    }

    public FieldMetaData get(String columnName) {
        return this.getDic().get(columnName);
    }
}
