package ionix.Data;

public interface EntitySqlQueryBuilder {
    EntitySqlQueryBuilderResult createQuery(Object entity, EntityMetaData metaData);
}
