package ionix.Annotation;

import ionix.Data.SqlValueType;
import ionix.Data.StoreGeneratedPattern;

public @interface DbSchema {
    String columnName();//Proprty ismi kolon ismiyle farklılık gösteriyor mu diye.

    boolean isKey();
    StoreGeneratedPattern databaseGeneratedOption();

    boolean isNullable();
    int maxLength();//UI Binding için.
    String defaultValue();

    boolean readOnly();

    int order();

    SqlValueType sqlValueType();
}
