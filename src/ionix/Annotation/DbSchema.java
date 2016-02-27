package ionix.Annotation;

import ionix.Data.SqlValueType;
import ionix.Data.StoreGeneratedPattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DbSchema {
    String columnName() default "";    //Proprty ismi kolon ismiyle farklılık gösteriyor mu diye.

    boolean isKey() default false;
    StoreGeneratedPattern databaseGeneratedOption() default StoreGeneratedPattern.None;

    boolean isNullable() default true;
    int maxLength() default 0;//UI Binding için.
    String defaultValue() default "";

    boolean readOnly() default false;

    int order() default 0;

    SqlValueType sqlValueType() default SqlValueType.Parameterized;
}
