package ionix.Data;


public interface EntityCommandDelete<TEntity> {
    int delete(TEntity entity, EntityMetaDataProvider provider);
}
