package ionix.Data;


import java.util.HashSet;

public interface EntityCommandUpdate
{
    HashSet<String> getUpdatedFields();
    void setUpdatedFields(HashSet<String> field);

    <TEntity> int  update(TEntity entity, EntityMetaDataProvider provider);
}