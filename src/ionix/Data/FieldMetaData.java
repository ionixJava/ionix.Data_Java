package ionix.Data;

import java.lang.reflect.Field;


public class FieldMetaData {

    public FieldMetaData(SchemaInfo schema, Field field){
        if (null == schema)
            throw new IllegalArgumentException("schema is null");
        if (null == field)
            throw new IllegalArgumentException("field is null");

        this.schema = schema;
        this.field = field;

    }

    private final SchemaInfo schema;
    public SchemaInfo getSchema(){
        return this.schema;
    }

    private final Field field;
    public Field getField(){
        return this.field;
    }


    @Override
    public FieldMetaData clone(){
        FieldMetaData copy = new FieldMetaData(this.schema.clone(), this.field);//field' in java da singleton olması gerek. ???
        return copy;
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof FieldMetaData){
            return this.schema.equals(((FieldMetaData)other).getSchema());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.schema.hashCode();
    }

    @Override
    public String toString(){
        return this.schema.getColumnName();
    }

}
