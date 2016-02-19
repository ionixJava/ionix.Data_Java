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

    public SqlQuery(SqlQuery query){
        this();
        if (null != query){
            this.combine(query);
        }
    }
    public SqlQuery combine(SqlQuery query){
        if (null != query){
            this.text.append(query.text);
            query.parameters.forEach((par)-> this.parameter(par.getValue()));
        }
        return this;
    }

    public SqlQuery sql(String sql, Object... parameters){
        if (!Ext.isNullOrEmpty(sql)){
            this.text.append(sql);
            if (null != parameters){
                for(int j = 0; j < parameters.length; ++j){
                    this.parameter(parameters[j]);
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


    public synchronized SqlQuery parameter(Object value){//combine için kullanılacak. Artan Index Veriyor
        this.parameters.add(this.parameters.size() + 1, value);
        return this;
    }

    public SqlQuery clear(){
        this.text.setLength(0);
        this.parameters.clear();
        return this;
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
}

