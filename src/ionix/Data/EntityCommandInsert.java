package ionix.Data;


import java.util.HashSet;

public interface EntityCommandInsert<TEntity> {
    void setInsertFields(HashSet<String> fields);

    int  insert(TEntity entity, EntityMetaDataProvider provider);
}
