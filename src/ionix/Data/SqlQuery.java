package ionix.Data;

import ionix.Utils.Ext;

public final class SqlQuery  {
    public static SqlQuery toQuery(String sql){
        return new SqlQuery(sql);
    }
    public static SqlQuery toQuery(String sql, Object... parameters){
        SqlQuery q = new SqlQuery();
        q.sql(sql, parameters);
        return q;
    }


    private final StringBuilder text;
    private final SqlQueryParameterList parameters;

    public SqlQuery(String sql)
    {
        this.text = new StringBuilder(sql);
        this.parameters = new SqlQueryParameterList();
    }
    public SqlQuery(){
        this("");
    }

    public StringBuilder getText(){
        return this.text;
    }

    public SqlQueryParameterList getParameters(){
        return this.parameters;
    }

    public boolean isEmpty(){
        return this.text.length() == 0;
    }

    @Override
    public String toString(){
        return this.text.toString();
    }

    public SqlQuery combine(SqlQuery query){
        if (null != query){
            this.text.append(query.text);
            this.parameters.addRange(query.parameters);
        }
        return this;
    }
    public SqlQuery clear(){
        this.text.setLength(0);
        this.parameters.clear();
        return this;
    }

    public SqlQuery sql(String sql, Object... parameters){
        if (!Ext.isNullOrEmpty(sql)){
            this.text.append(sql);
            if (null != parameters){
                for(int j = 0; j < parameters.length; ++j){
                    this.parameters.add(j + 1, parameters[j]);
                }
            }
        }
        return this;
    }
    public SqlQuery sql(String sql){
        if (!Ext.isNullOrEmpty(sql)){
            this.text.append(sql);
        }
        return this;
    }

    public SqlQuery paramater(String name, Object value) {
        if (!Ext.isNullOrEmpty(name)){
            this.parameters.add(name, value);
        }
        return this;
    }

    public SqlQuery paramater(int index, Object value) {
        if (index > 0){
            this.parameters.add(index, value);
        }
        return this;
    }
}

