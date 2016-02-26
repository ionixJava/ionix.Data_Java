package ionix.Data;

import ionix.Utils.CachedTypes;
import ionix.Utils.Ext;
import java.sql.JDBCType;
import java.sql.SQLDataException;
import java.util.HashMap;

//DbAccess de yapılan setObject Buraya Taşınacak
public final class SqlQueryParameter {
    SqlQueryParameter(){

    }

    private SqlQueryParameter(String name, int index, Object value){
        this.name = name;
        this.index = index;
        this.value = value;
    }
    public SqlQueryParameter(String name, Object value){
        this(name, 0,value);
    }
    public SqlQueryParameter(int index, Object value){
        this(null, index,value);
    }

    private String name;
    public String getName(){return this.name;}
    public SqlQueryParameter setName(String value){
        this.name = value;
        return this;
    }

    private int index;
    public int getIndex(){return this.index;}
    public SqlQueryParameter setIndex(int value){
        this.index = value;
        return this;
    }

    private Object value;
    public Object getValue() {
        return this.value;
    }
    public SqlQueryParameter setValue(Object value) {
        this.value = value;
        return this;
    }

    private JDBCType dataType;
    public JDBCType getDataType(){
        return this.dataType;
    }
    public SqlQueryParameter setDataType(JDBCType value){
        this.dataType = value;
        return this;
    }


    public boolean isNamed(){
        return this.index < 1;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof SqlQueryParameter){
            SqlQueryParameter other = (SqlQueryParameter)obj;
            if (this.isNamed())
                return this.name == other.name;
            return this.index == other.index;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return !Ext.isNullOrEmpty(this.name) ? this.name.hashCode() : this.index;
    }

    @Override
    public String toString(){
        return this.isNamed() ? this.name : Integer.toString(this.index);
    }
}
