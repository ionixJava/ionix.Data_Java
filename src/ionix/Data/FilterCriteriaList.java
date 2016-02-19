package ionix.Data;


import ionix.Utils.Ext;
import java.util.ArrayList;

public class FilterCriteriaList extends ArrayList<FilterCriteria> implements SqlQueryProvider {

    public FilterCriteriaList add(String columnName, ConditionOperator op, Object... values)
    {
        FilterCriteria item = new FilterCriteria(columnName, op, values);
        item.setRoot(this.createQuery());
        super.add(item);
        return  this;
    }

    public FilterCriteriaList add(FieldMetaData field, ConditionOperator op, Object... values)
    {
        if (null == field)
            throw new IllegalArgumentException("field is null");

        return this.add(field.getSchema().getColumnName(), op, values);
    }

    private SqlQuery seed;
    @Override
    public void setRoot(SqlQuery seed){
        this.seed = seed;
    }
    private SqlQuery createQuery(){
        return this.seed == null ? new SqlQuery() : seed;
    }

    public SqlQuery toQuery(String tableNameOp)
    {
        if (super.size() > 0)
        {
            final boolean hasTableNameOp = !Ext.isNullOrEmpty(tableNameOp);

            SqlQuery query = this.createQuery();
            StringBuilder text = query.getText();
            text.append("\n");
            text.append("WHERE ");

            this.forEach((filter)->{
                if (null != filter)
                {
                    if (hasTableNameOp)
                        text.append(tableNameOp);

                    SqlQuery temp = filter.toQuery();

                    query.combine(temp);

                    text.append(" AND ");
                }
            });
            text.delete(text.length() - 5, text.length());
            return query;
        }

        return null;
    }

    public SqlQuery toQuery()
    {
        return this.toQuery(null);
    }
}
