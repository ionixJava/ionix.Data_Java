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

    private SchemaInfo schema;
    public SchemaInfo getSchema(){
        return this.schema;
    }
    public FieldMetaData setSchema(SchemaInfo value){
        this.schema = value;
        return this;
    }

    private Field field;
    public Field getField(){
        return this.field;
    }
    public FieldMetaData setField(Field field){
        this.field = field;
        return this;
    }

    private int parameterIndex;
    public int getParameterIndex(){
        return this.parameterIndex;
    }
    public FieldMetaData setParameterIndex(int value){
        this.parameterIndex = value;
        return this;
    }

    @Override
    public FieldMetaData clone(){
        FieldMetaData copy = new FieldMetaData(this.schema.clone(), this.field);//field' in java da singleton olması gerek. ???
        //parametrName kopyalanmamalı
        return copy;
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof FieldMetaData){
            return this.schema.equals(((SchemaInfo)other));
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
