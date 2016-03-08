package ionix.Data;


import java.util.List;

//a kind of proxy
public class Repository<TEntity> implements AutoCloseable {

    private final Class<TEntity> cls;
    private final CommandAdapter cmd;

    public Repository(Class<TEntity> cls, CommandAdapter cmd) {
        if (null == cmd)
            throw new IllegalArgumentException("cmd is null");

        this.cls = cls;
        this.cmd = cmd;
    }

    public Repository(Class<TEntity> cls, CommandFactory factory, EntityMetaDataProvider provider) {
        this(cls, new CommandAdapter(factory, provider));
    }

    public CommandAdapter getCmd() {
        return this.cmd;
    }

    public DbAccess getDataAccess() {
        return this.cmd.getFactory().getDataAccess();
    }

    @Override
    public void close() {
        if (null != this.cmd)
            this.cmd.getFactory().getDataAccess().close();
    }


    public TEntity selectById(Object... keys){
        return this.cmd.selectById(this.cls, keys);
    }
    public TEntity selectSingle(SqlQuery whereQuery){
        return this.cmd.selectSingle(this.cls, whereQuery);
    }
    public List<TEntity> select(SqlQuery wherQuery){
        return this.cmd.select(this.cls, wherQuery);
    }

    public TEntity querySingle(SqlQuery query){
        return this.cmd.querySingle(this.cls, query);
    }
    public TEntity querySingle(String sql){
        return this.cmd.querySingle(this.cls, sql);
    }
    public TEntity querySingle(String sql, Object... parameters){
        return this.cmd.querySingle(this.cls, sql, parameters);
    }

    public List<TEntity> query(SqlQuery query){
        return this.cmd.query(this.cls, query);
    }
    public List<TEntity> query(String sql){
        return this.cmd.query(this.cls, sql);
    }
    public List<TEntity> query(String sql, Object... parameters){
        return this.cmd.query(this.cls, sql, parameters);
    }


    public int update(TEntity entity, String... updatedFields){
        return this.cmd.update(this.cls, entity, updatedFields);
    }
    public int update(TEntity entity){
        return this.cmd.update(this.cls, entity);
    }


    public int insert(TEntity entity, String... insertFields){
        return this.cmd.insert(this.cls, entity, insertFields);
    }
    public int insert(TEntity entity){
        return this.cmd.insert(this.cls, entity);
    }

    public int delete(TEntity entity){
        return this.cmd.delete(this.cls, entity);
    }


    public int[] batchUpdate(Iterable<TEntity> entityList, String... updatedFields){
        return this.cmd.batchUpdate(this.cls, entityList, updatedFields);
    }
    public int[] batchUpdate(Iterable<TEntity> entityList){
        return this.cmd.batchUpdate(this.cls, entityList);
    }

    public int[] batchInsert(Iterable<TEntity> entityList, String... insertFields){
        return this.cmd.batchInsert(this.cls, entityList, insertFields);
    }
    public int[] batchInsert(Iterable<TEntity> entityList){
        return this.cmd.batchInsert(this.cls, entityList);
    }


    public int[] batchDelete(Iterable<TEntity> entityList){
        return this.cmd.batchDelete(this.cls, entityList);
    }
}
