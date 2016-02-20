package ionix.Data;

public interface EntitySqlQueryBuilder {
    SqlQuery createQuery(Object entity, EntityMetaData metaData);
}
