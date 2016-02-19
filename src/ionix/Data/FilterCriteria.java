package ionix.Data;

import ionix.Utils.Ext;

import java.util.Arrays;
import java.util.List;


public class FilterCriteria implements SqlQueryProvider {
    private final String columnName;
    private final ConditionOperator op;
    private final List<Object> values;

    public FilterCriteria(String columnName, ConditionOperator op, Object... values){

        if (Ext.isNullOrEmpty(columnName))
            throw new IllegalArgumentException("columnName is null or empty");
        if (null == op)
            throw new IllegalArgumentException("ConditionOperator is null");
        if (null == values)
            throw new IllegalArgumentException("value is null");
        if (values.length == 0)
            throw new IllegalArgumentException("'values.Length' must be greater than zero");
        for (int j = 0; j < values.length; ++j)
        {
            if (values[j] == null)
                throw new NullPointerException("'values[" + j + "]'");
        }
        if (op == ConditionOperator.Between)
        {
            if (values.length < 2)
                throw new IllegalArgumentException("'Between' Operatörü İçin Eksik Sayıda Parametre --> 'values.legth = " + values.length);
            if (values.length > 2)
                throw new IllegalArgumentException( "'Between' Operatörü İçin Fazla Sayıda Parametre --> 'values.legth = " + values.length);
        }
        else if (op != ConditionOperator.In && values.length> 1)
        {
            throw new IllegalArgumentException("'In' Operatörü İçin sadece Bir Adet Parametre Girilebilir.");
        }

        this.columnName = columnName;
        this.op = op;
        this.values = Arrays.asList(values);
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
        SqlQuery query = this.createQuery();

        StringBuilder text = query.getText();
        SqlQueryParameterList parameters = query.getParameters();

        text.append(this.columnName);
        String op = this.getOpString();
        if (null != op){
            text.append(op);
            text.append('?');
            parameters.add(1, this.values.get(0));
        }
        else if (this.op == ConditionOperator.StartsWith || this.op == ConditionOperator.Contains || this.op == ConditionOperator.EndsWith)
        {
            text.append(" LIKE ?");

            String firstPrefix = "";
            String lastPrefix = "";

            if (this.op == ConditionOperator.EndsWith || this.op == ConditionOperator.Contains)
                firstPrefix = "%";
            if (this.op == ConditionOperator.StartsWith || this.op == ConditionOperator.Contains)
                lastPrefix = "%";


            parameters.add(1, firstPrefix + this.values.get(0) + lastPrefix);
        }
        else
        {
            switch (this.op)
            {
                case In:
                    text.append(" IN (");
                    for (int j = 0; j < this.values.size(); ++j)
                    {
                        text.append("?, ");

                        parameters.add(j + 1, values.get(j));
                    }
                    text.delete(text.length() - 2, text.length());
                    text.append(")");
                    break;
                case Between:
                    text.append(" BETWEEN ? AND ?");

                    parameters.add(1, values.get(0));
                    parameters.add(2, values.get(1));
                    break;
                default:
                    throw new UnsupportedOperationException(this.op.toString());
            }
        }
        return query;
    }

    private String getOpString()
    {
        switch (this.op)
        {
            case Equals:
                return "=";
            case NotEquals:
                return " <> ";
            case GreaterThan:
                return " > ";
            case LessThan:
                return " < ";
            case GreaterThanOrEqualsTo:
                return ">=";
            case LessThanOrEqualsTo:
                return "<=";
            default:
                return null;
        }
    }
}
