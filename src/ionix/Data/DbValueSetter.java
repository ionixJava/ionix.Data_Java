package ionix.Data;


import ionix.Utils.CachedTypes;
import ionix.Utils.Ext;
import ionix.Utils.Ref;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

//Bu Kısım DbCommand larda mutlaka elden geçmneli. Aynı şekilde parametre tip belirleme de.
//Ek olarak named parametreler kullanılmayacak.
public class DbValueSetter {
    static final HashSet<Class> withQuotes;

    static {
        HashSet<Class> temp = new HashSet<>();
        temp.add(CachedTypes.String);
        temp.add(CachedTypes.Date);
        temp.add(CachedTypes.UUID);
        withQuotes = temp;
    }

    public static final DbValueSetter Instance = new DbValueSetter();
    protected DbValueSetter(){
    }

    public void setColumnValue(SqlQuery query, FieldMetaData metaData, Object entity) {
        SchemaInfo schema = metaData.getSchema();
        Field field = metaData.getField();
        Object parValue;
        try {
            parValue = field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        StringBuilder text = query.getText();
        if (!Ext.isNullOrEmpty(schema.getDefaultValue())){
            Object defaultValue = Ref.getDefault(field.getDeclaringClass());
            if (Ext.equals(defaultValue, parValue)){//Eğer Property Değeri Default Haldeyse yazdır Bunu
                text.append(schema.getDefaultValue());
                return;
            }
        }

        switch (schema.getSqlValueType()){//Buralar DbCommand yazılarken test edilecek.
            case Parameterized:
                text.append('?');

                if (null == parValue && !schema.getIsNullable())
                    throw new RuntimeException(new SQLDataException(schema.getColumnName() + " can not be null"));

                SqlQueryParameterList pars = query.getParameters();
                SqlQueryParameter par = new SqlQueryParameter()
                        .setValue(parValue)
                        .setDataType(getTypeMap().get(field.getDeclaringClass()))
                        .setIndex(pars.size() + 1);

                pars.add(par);
                break;
            case Text:
                String textValue = null;
                if (null != parValue){
                    Class dataType = metaData.getSchema().getDataClass();
                    if (withQuotes.contains(dataType)){
                        textValue = "'" + parValue + "'";
                    }
                    else{
                        textValue = parValue.toString();
                    }
                }
                text.append(textValue);
                break;
            default:
                throw new UnsupportedOperationException(schema.getSqlValueType().toString());
        }
    }

    private static final HashMap<Class, JDBCType> typeMap = new HashMap<>();
    private synchronized static HashMap<Class, JDBCType> getTypeMap(){
        if (typeMap.isEmpty()){
            typeMap.put(CachedTypes.String, JDBCType.VARCHAR);
            typeMap.put(CachedTypes.Nullable_Boolean, JDBCType.BIT);
            typeMap.put(CachedTypes.BigDecimal, JDBCType.NUMERIC);
            typeMap.put(CachedTypes.Nullable_Byte, JDBCType.TINYINT);
            typeMap.put(CachedTypes.Nullable_Short, JDBCType.SMALLINT);
            typeMap.put(CachedTypes.Nullable_Int, JDBCType.INTEGER);
            typeMap.put(CachedTypes.Nullable_Long, JDBCType.BIGINT);
            typeMap.put(CachedTypes.Nullable_Float,JDBCType.FLOAT);
            typeMap.put(CachedTypes.Nullable_Double, JDBCType.DOUBLE);
            typeMap.put(CachedTypes.ByteArray, JDBCType.VARBINARY);
            typeMap.put(CachedTypes.Date, JDBCType.DATE);
            typeMap.put(CachedTypes.Timestamp, JDBCType.TIMESTAMP);
            typeMap.put(CachedTypes.Clob, JDBCType.CLOB);
            typeMap.put(CachedTypes.Blob, JDBCType.BLOB);
            typeMap.put(CachedTypes.Array, JDBCType.ARRAY);
            typeMap.put(CachedTypes.Ref, JDBCType.REF);
            typeMap.put(CachedTypes.Struct, JDBCType.STRUCT);
        }
        return typeMap;
    }
}
