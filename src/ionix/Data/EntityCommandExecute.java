package ionix.Data;


public interface EntityCommandExecute<TEntity> {
    int execute(TEntity entity, EntityMetaDataProvider provider);
}
