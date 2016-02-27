package ionix.Data;


import java.util.HashSet;

public interface EntityCommandUpdate<TEntity>
{
    void setUpdatedFields(HashSet<String> fields);

    int  update(TEntity entity, EntityMetaDataProvider provider);
}