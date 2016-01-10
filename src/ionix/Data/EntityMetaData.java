package ionix.Data;


import ionix.Utils.Ext;
import ionix.Utils.ThrowingHashSet;
import java.lang.reflect.Field;
import java.util.HashMap;

public class EntityMetaData {

    private final Class entityClass;
    private final ThrowingHashSet<FieldMetaData> hash;
    private String tableName;

    public EntityMetaData(Class entityClass, String tableName) {
        if (null == entityClass)
            throw new IllegalArgumentException("entityClass is null");
        if (Ext.isNullOrEmpty(tableName))
            throw new IllegalArgumentException("tableName is null or empty");

        this.entityClass = entityClass;
        this.hash = new ThrowingHashSet<>();
        this.tableName = tableName;
    }

    public EntityMetaData(Class entityClass) {
        this(entityClass, ExtAnnotation.getTableName(entityClass));
    }

    public void add(SchemaInfo schema, Field field) {
        this.hash.add(new FieldMetaData(schema, field));
    }

    public void add(FieldMetaData item) {
        if (null == item)
            throw new IllegalArgumentException("item is null");

        this.hash.add(item);
    }

    public String getTableName() {
        return this.tableName == null ? "" : this.tableName;
    }

    public EntityMetaData setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public Class getEntityClass() {
        return this.entityClass;
    }

    public Iterable<FieldMetaData> getFields() {
        return this.hash;
    }

    public int size() {
        return this.hash.size();
    }

    @Override
    public EntityMetaData clone() {
        EntityMetaData copy = new EntityMetaData(this.entityClass, this.tableName);//Type ReadOnly bir object dir. String de fixed char* kullanılmıyorsa immutable bir nesnedir.
        for (FieldMetaData orginal : this.hash) {
            copy.hash.add(orginal.clone());
        }

        return copy;
    }

    @Override
    public String toString() {
        return this.tableName;
    }

    private HashMap<String, FieldMetaData> dic;

    private HashMap<String, FieldMetaData> getDic() {
        if (null == this.dic) {
            this.dic = new HashMap<>(this.hash.size());
            for (FieldMetaData item : this.hash)
                this.dic.put(item.getSchema().getColumnName(), item);
        }

        return this.dic;
    }

    public FieldMetaData get(String columnName) {
        FieldMetaData p = null;
        this.getDic().getOrDefault(columnName, p);
        return p;
    }
}
