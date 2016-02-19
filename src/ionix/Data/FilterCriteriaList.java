package ionix.Data;


import ionix.Utils.Ext;
import java.util.ArrayList;
import java.util.Iterator;

public class FilterCriteriaList implements Iterable<FilterCriteria>, SqlQueryProvider {

    private final ArrayList<FilterCriteria> list = new ArrayList<>();

    public FilterCriteriaList add(String columnName, ConditionOperator op, Object... values) {
        FilterCriteria item = new FilterCriteria(columnName, op, values);
        this.list.add(item);
        return this;
    }


    private SqlQuery seed;

    @Override
    public void setRoot(SqlQuery seed) {
        this.seed = seed;
    }

    private SqlQuery createQuery() {
        return this.seed == null ? new SqlQuery() : seed;
    }

    public int size() {
        return this.list.size();
    }

    @Override
    public Iterator<FilterCriteria> iterator() {
        return this.list.iterator();
    }

    public SqlQuery toQuery(String tableNameOp) {
        if (this.size() > 0) {
            final boolean hasTableNameOp = !Ext.isNullOrEmpty(tableNameOp);

            SqlQuery query = this.createQuery();
            StringBuilder text = query.getText();
            text.append("\n");
            text.append("WHERE ");

            this.forEach((filter) -> {
                if (null != filter) {
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

    @Override
    public SqlQuery toQuery() {
        return this.toQuery(null);
    }
}
