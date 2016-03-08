package ionix.Data;


import java.util.*;


public abstract class EntitySqlQueryBuilderInsert implements EntitySqlQueryBuilder {
    private HashSet<String> insertFields;
    protected HashSet<String > getInsertFields(){
        return this.insertFields;
    }
    public void setInsertFields(HashSet<String> insertFields){
        this.insertFields = insertFields;
    }

    @Override
    public abstract EntitySqlQueryBuilderResult createQuery(Object entity, EntityMetaData metaData);
}
