package ionix.Data;


public interface BatchCommandExecute<TEntity> {
    int[] execute(Iterable<TEntity> entityList, EntityMetaDataProvider provider);
}
