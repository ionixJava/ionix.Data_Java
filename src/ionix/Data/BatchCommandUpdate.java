package ionix.Data;


import java.util.*;

public interface BatchCommandUpdate<TEntity> {

    void setUpdatedFields(HashSet<String> fields);
    int[] update(Iterable<TEntity> entityList, EntityMetaDataProvider provider);
}
