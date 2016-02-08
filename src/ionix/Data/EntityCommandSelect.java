package ionix.Data;


import ionix.Utils.Ref;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class EntityCommandSelect {

    public EntityCommandSelect(DbAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    private final DbAccess dataAccess;
    public DbAccess getDataAccess(){
        return this.dataAccess;
    }

    private boolean convertType;
    public boolean getConvertType(){return this.convertType;}
    public EntityCommandSelect setConvertType(boolean convertType){
        this.convertType = convertType;
        return this;
    }

    private enum MapType{
        Select,
        Query
    }

    private <TEntity> void map(TEntity entity, EntityMetaData metaData, QueryResult qr, MapType mapType){
        switch (mapType){
            case Select:
                for(FieldMetaData fd : metaData.getFields()){
                    String columnName = fd.getSchema().getColumnName();
                    Field field = fd.getField();
                    try {
                        Object dbValue = qr.getResultSet().getObject(columnName);
                        if (this.convertType)
                            Ref.setValueSafely(field, entity, dbValue);
                        else
                            field.set(entity, dbValue);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case Query:
                ResultSet rs = qr.getResultSet();
                try {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int fieldCount = rsmd.getColumnCount();
                    for (int j = 0; j < fieldCount; ++j) {
                        String columnName = rsmd.getColumnName(j + 1);
                        FieldMetaData fd = metaData.get(columnName);
                        if (null != fd) {
                            Field field = fd.getField();
                            Object dbValue = rs.getObject(j + 1);
                            if (this.convertType)
                                Ref.setValueSafely(field, entity, dbValue);
                            else
                                field.set(entity, dbValue);
                        }
                    }
                }
                catch (Exception e){
                    throw new RuntimeException(e);
                }
                break;
            default:
                throw new UnsupportedOperationException(mapType.toString());
        }
    }

    private <TEntity> TEntity readEntity(EntityMetaData metaData, SqlQuery query, MapType mapType){
        QueryResult result = null;
        try{
            result = this.getDataAccess().executeQuery(query);

            if (result.getResultSet().next()){

            }
        }
        finally{

        }
    }
}
