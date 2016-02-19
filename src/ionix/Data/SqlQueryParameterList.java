package ionix.Data;

import ionix.Utils.Ext;
import java.util.ArrayList;
import java.util.Iterator;


public final class SqlQueryParameterList implements Iterable<SqlQueryParameter> {

    private final ArrayList<SqlQueryParameter> list = new ArrayList<>();


    //Optimizasyon için eklendi
    private boolean hasNamed;
    final boolean getHasNamed(){
        return hasNamed;
    }
    private void addInternal(SqlQueryParameter parameter)
    {
        this.list.add(parameter);
        if (!hasNamed && parameter.isNamed()){
            hasNamed = true;
        }
    }

    public SqlQueryParameterList add(SqlQueryParameter parameter)
    {
        if (null == parameter)
            throw new NullPointerException("parameter");

        this.addInternal(parameter);
        return this;
    }

    public SqlQueryParameterList add(String name, Object value)
    {
        if (Ext.isNullOrEmpty(name))
            throw new IllegalArgumentException("name");

        this.addInternal(new SqlQueryParameter(name, value));
        return this;
    }

    public SqlQueryParameterList add(int index, Object value)
    {
        if (index < 1)
            throw new IllegalArgumentException("index");

        this.addInternal(new SqlQueryParameter(index, value));
        return this;
    }

    public SqlQueryParameterList clear(){
        this.list.clear();
        return this;
    }

    public int size(){
        return this.list.size();
    }

    @Override
    public Iterator<SqlQueryParameter> iterator() {
        return this.list.iterator();
    }
}

