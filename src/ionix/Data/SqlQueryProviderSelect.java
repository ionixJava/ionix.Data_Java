package ionix.Data;

public class SqlQueryProviderSelect implements SqlQueryProvider {

    public SqlQueryProviderSelect(EntityMetaData metaData) {
        SqlQueryHelper.ensureEntityMetaData(metaData);

        this.metaData = metaData;
    }

    private final EntityMetaData metaData;

    public EntityMetaData getMetaData() {
        return this.metaData;
    }

    private SqlQuery seed;
    @Override
    public void setRoot(SqlQuery seed){
        this.seed = seed;
    }
    private SqlQuery createQuery(){
        return this.seed == null ? new SqlQuery() : seed;
    }

    @Override
    public SqlQuery toQuery() {
        String tableName = this.metaData.getTableName();

        SqlQuery query = this.createQuery();
        StringBuilder text = query.getText();
        text.append("SELECT ");

        for (FieldMetaData field : this.metaData.getFields()) {
            String columnName = field.getSchema().getColumnName();

            text.append(columnName);
            text.append(", ");
        }
        text.delete(text.length() - 2, text.length());

        text.append(" FROM ");
        text.append(tableName);

        return query;
    }

    public SqlQuery toQuery(String tableAlias) {
        String tableName = this.metaData.getTableName();

        SqlQuery query = this.createQuery();
        StringBuilder text = query.getText();
        text.append("SELECT ");

        String tableNameOp = tableAlias + ".";

        for (FieldMetaData field : this.metaData.getFields()) {
            String columnName = field.getSchema().getColumnName();

            text.append(tableNameOp);
            text.append(columnName);
            text.append(" AS ");
            text.append(columnName);
            text.append(", ");
        }
        text.delete(text.length() - 2, text.length());

        text.append(" FROM ");
        text.append(tableName);
        text.append(' ');
        text.append(tableAlias);

        return query;
    }
}
