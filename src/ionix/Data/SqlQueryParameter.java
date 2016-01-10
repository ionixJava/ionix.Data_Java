package ionix.Data;

//import ionix.Utils.CachedTypes;
import ionix.Utils.Ext;
//import java.sql.JDBCType;
//import java.util.HashMap;

public final class SqlQueryParameter {
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

//    private JDBCType dataType;
//    public JDBCType getDataType(){
//        if (null != this.dataType)
//            return this.dataType;
//        if (null == this.value)
//            return JDBCType.NULL;
//        JDBCType ret = getTypeMap().get(this.value.getClass());
//        return ret == null ? JDBCType.OTHER : ret;
//    }
//    public SqlQueryParameter setDataType(JDBCType value){
//        this.dataType = value;
//        return this;
//    }
//
//    private static final HashMap<Class, JDBCType> typeMap = new HashMap<>();
//    private synchronized static HashMap<Class, JDBCType> getTypeMap(){
//        if (typeMap.isEmpty()){
//            typeMap.put(CachedTypes.String, JDBCType.VARCHAR);
//            typeMap.put(CachedTypes.Nullable_Boolean, JDBCType.BIT);
//            typeMap.put(CachedTypes.BigDecimal, JDBCType.NUMERIC);
//            typeMap.put(CachedTypes.Nullable_Byte, JDBCType.TINYINT);
//            typeMap.put(CachedTypes.Nullable_Short, JDBCType.SMALLINT);
//            typeMap.put(CachedTypes.Nullable_Int, JDBCType.INTEGER);
//            typeMap.put(CachedTypes.Nullable_Long, JDBCType.BIGINT);
//            typeMap.put(CachedTypes.Nullable_Float,JDBCType.FLOAT);
//            typeMap.put(CachedTypes.Nullable_Double, JDBCType.DOUBLE);
//            typeMap.put(CachedTypes.ByteArray, JDBCType.VARBINARY);
//            typeMap.put(CachedTypes.Date, JDBCType.DATE);
//            typeMap.put(CachedTypes.Timestamp, JDBCType.TIMESTAMP);
//            typeMap.put(CachedTypes.Clob, JDBCType.CLOB);
//            typeMap.put(CachedTypes.Blob, JDBCType.BLOB);
//            typeMap.put(CachedTypes.Array, JDBCType.ARRAY);
//            typeMap.put(CachedTypes.Ref, JDBCType.REF);
//            typeMap.put(CachedTypes.Struct, JDBCType.STRUCT);
//        }
//        return typeMap;
//    }


    public boolean isNamed(){
        return this.index < 1;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof SqlQueryParameter){
            SqlQueryParameter other = (SqlQueryParameter)obj;
            if (!Ext.isNullOrEmpty(this.name))
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
        return this.name;
    }
}
