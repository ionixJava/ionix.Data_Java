package ionix.Data;


import java.util.HashSet;

public interface BatchCommandInsert<TEntity> {

    void setInsertFields(HashSet<String> fields);
    int[] insert(Iterable<TEntity> entityList, EntityMetaDataProvider provider);
}
