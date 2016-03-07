package ionix.Data;


import ionix.Utils.Ext;

import java.util.*;

public class CommandAdapter {
    private final CommandFactory factory;
    private final EntityMetaDataProvider provider;

    public CommandAdapter(CommandFactory factory,EntityMetaDataProvider provider){
        if (null == factory)
            throw new IllegalArgumentException("factory is null");
        if (null == provider)
            throw new IllegalArgumentException("provider is null");

        this.factory = factory;
        this.provider = provider;
    }

    public CommandFactory getFactory(){
        return this.factory;
    }

    private <TEntity> EntityCommandSelect<TEntity> createSelectCommand(Class<TEntity> cls){
        return this.factory.createSelectCommand(cls).setConvertType(true);
    }

    private static void checkSql(String sql){
        if (Ext.isNullOrEmpty(sql))
            throw new IllegalArgumentException("sql is null or empty");
    }


    public <TEntity> TEntity selectById(Class<TEntity> cls, Object... keys){
        EntityCommandSelect<TEntity> cmd = this.createSelectCommand(cls);
        return cmd.selectById(this.provider, keys);
    }

    public <TEntity> TEntity selectSingle(Class<TEntity> cls, SqlQuery whereQuery){
        EntityCommandSelect<TEntity> cmd = this.createSelectCommand(cls);
        return cmd.selectSingle(this.provider, whereQuery);
    }

    public <TEntity> List<TEntity> select(Class<TEntity> cls, SqlQuery whereQuery){
        EntityCommandSelect<TEntity> cmd = this.createSelectCommand(cls);
        return cmd.select(this.provider, whereQuery);
    }


    public <TEntity> TEntity querySingle(Class<TEntity> cls, SqlQuery query){
        EntityCommandSelect<TEntity> cmd = this.createSelectCommand(cls);
        return cmd.querySingle(this.provider, query);
    }
    public <TEntity> TEntity querySingle(Class<TEntity> cls, String sql){
        return this.querySingle(cls, SqlQuery.toQuery(sql));
    }
    public <TEntity> TEntity querySingle(Class<TEntity> cls, String sql, Object... parameters){
        return this.querySingle(cls, SqlQuery.toQuery(sql, parameters));
    }

    public <TEntity> List<TEntity> query(Class<TEntity> cls, SqlQuery query){
        EntityCommandSelect<TEntity> cmd = this.createSelectCommand(cls);
        return cmd.query(this.provider, query);
    }
    public <TEntity> List<TEntity> query(Class<TEntity> cls, String sql){
        return this.query(cls, SqlQuery.toQuery(sql));
    }
    public <TEntity> List<TEntity> query(Class<TEntity> cls, String sql, Object... parameters){
        return this.query(cls, SqlQuery.toQuery(sql, parameters));
    }



    public <TEntity> int update(Class<TEntity> cls, TEntity entity, String... updatedFields){
        EntityCommandUpdate<TEntity> cmd = (EntityCommandUpdate<TEntity>)this.factory.createEntityCommand(cls, EntityCommandType.Update);
        if (!Ext.isEmptyArray(updatedFields)){
            cmd.setUpdatedFields(new HashSet<>(Ext.toCollection(String.class, updatedFields)));
        }

        return cmd.update(entity, this.provider);
    }
    public <TEntity> int update(Class<TEntity> cls, TEntity entity){
        return this.update(cls, entity, (String[]) null);
    }

    public <TEntity> int insert(Class<TEntity> cls, TEntity entity, String... insertField){
        EntityCommandInsert<TEntity> cmd = (EntityCommandInsert<TEntity>)this.factory.createEntityCommand(cls, EntityCommandType.Insert);
        if (!Ext.isEmptyArray(insertField)){
            cmd.setInsertFields(new HashSet<>(Ext.toCollection(String.class, insertField)));
        }

        return cmd.insert(entity, provider);
    }
    public <TEntity> int insert(Class<TEntity> cls, TEntity entity){
        return this.insert(cls, entity, (String[])null);
    }

    public <TEntity> int delete(Class<TEntity> cls, TEntity entity){
        EntityCommandDelete<TEntity> cmd = (EntityCommandDelete<TEntity>)this.factory.createEntityCommand(cls, EntityCommandType.Delete);
        return cmd.delete(entity, provider);
    }



    public <TEntity> int[] batchUpdate(Class<TEntity> cls, Iterable<TEntity> entityList, String... updatedFields){
        BatchCommandUpdate<TEntity> cmd = (BatchCommandUpdate<TEntity>)this.factory.createBatchCommand(cls, EntityCommandType.Update);
        if (!Ext.isEmptyArray(updatedFields)){
            cmd.setUpdatedFields(new HashSet<>(Ext.toCollection(String.class, updatedFields)));
        }

        return cmd.update(entityList, this.provider);
    }
    public <TEntity> int[] batchUpdate(Class<TEntity> cls, Iterable<TEntity> entityList){
        return this.batchUpdate(cls, entityList, (String[])null);
    }

    public <TEntity> int[] batchInsert(Class<TEntity> cls, Iterable<TEntity> entityList, String... insertFields){
        BatchCommandInsert<TEntity> cmd = (BatchCommandInsert<TEntity>)this.factory.createBatchCommand(cls, EntityCommandType.Insert);
        if (!Ext.isEmptyArray(insertFields)){
            cmd.setInsertFields(new HashSet<>(Ext.toCollection(String.class, insertFields)));
        }

        return cmd.insert(entityList, this.provider);
    }
    public <TEntity> int[] batchInsert(Class<TEntity> cls, Iterable<TEntity> entityList){
        return this.batchInsert(cls, entityList, (String[])null);
    }
}
