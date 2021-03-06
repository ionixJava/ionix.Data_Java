package ionix.Data;

import ionix.Utils.*;

import java.util.*;


public class EntitySqlQueryBuilderUpdate implements EntitySqlQueryBuilder {

    private HashSet<String> updatedFields;

    public HashSet<String> getUpdatedFields() {
        return this.updatedFields;
    }

    public void setUpdatedFields(HashSet<String> updatedFields) {
        this.updatedFields = updatedFields;
    }

    @Override
    public EntitySqlQueryBuilderResult createQuery(Object entity, EntityMetaData metaData) {
        if (null == entity)
            throw new IllegalArgumentException("entity is null");

        boolean updatedFieldsEnabled = !Ext.isEmptyList(this.updatedFields);

        SqlQuery query = new SqlQuery();
        StringBuilder sb = query.getText();

        sb.append("UPDATE ")
                .append(metaData.getTableName())
                .append(" SET ");


        for (FieldMetaData field : metaData.getFields()) {
            SchemaInfo schema = field.getSchema();

            if (schema.getIsKey())
                continue;

            if (schema.getDatabaseGeneratedOption() != StoreGeneratedPattern.None)
                continue;

            if (schema.getReadOnly())
                continue;

            if (updatedFieldsEnabled && !this.updatedFields.contains(schema.getColumnName()))
                continue;

            sb.append(schema.getColumnName())
                    .append('=');

            SqlQueryHelper.setColumnValue(DbValueSetter.Instance, query, field, entity);

            sb.append(',');
        }

        sb.delete(sb.length() - 1, sb.length());

        query.combine(SqlQueryHelper.createWhereSqlByKeys(metaData, entity));

        return new EntitySqlQueryBuilderResult(query, null, null, query.getParameters().size());
    }
}
