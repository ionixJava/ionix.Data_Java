package ionix.Data;


public abstract class CommandFactoryBase implements CommandFactory {

    private final DbAccess dataAccess;

    protected CommandFactoryBase(DbAccess dataAccess) {
        if (null == dataAccess)
            throw new IllegalArgumentException("dataAccess is  null");
        this.dataAccess = dataAccess;
    }

    protected abstract <TEntity> EntityCommandInsert<TEntity> createEntityCommandInsert(Class<TEntity> cls, DbAccess dataAccess);
    protected abstract <TEntity> BatchCommandInsert<TEntity> createBatchCommandInsert(Class<TEntity> cls, TransactionalDbAccess dataAccess);

    @Override
    public DbAccess getDataAccess() {
        return this.dataAccess;
    }

    @Override
    public <TEntity> EntityCommandSelect<TEntity> createSelectCommand(Class<TEntity> cls) {
        if (null == cls)
            throw new IllegalArgumentException("cls is null");

        return new EntityCommandSelect<>(cls, this.getDataAccess());
    }

    @Override
    public <TEntity> EntityCommandExecute<TEntity> createEntityCommand(Class<TEntity> cls, EntityCommandType cmdType) {
        if (null == cls)
            throw new IllegalArgumentException("cls is null");
        if (null == cmdType)
            throw new IllegalArgumentException("cmdType is null");

        switch (cmdType) {
            case Update:
                return new EntityCommandUpdate<>(cls, this.dataAccess);
            case Insert:
                return this.createEntityCommandInsert(cls, this.dataAccess);
            case Delete:
                return new EntityCommandDelete<>(cls, this.dataAccess);
            default:
                throw new UnsupportedOperationException(cmdType.toString());
        }
    }

    @Override
    public <TEntity> BatchCommandExecute<TEntity> createBatchCommand(Class<TEntity> cls, EntityCommandType cmdType){
        if (null == cls)
            throw new IllegalArgumentException("cls is null");
        if (null == cmdType)
            throw new IllegalArgumentException("cmdType is null");

        TransactionalDbAccess tdb = (TransactionalDbAccess) this.dataAccess;// DB işler Çok sonrakarı karışacağından bu görevi client kodlara bırakmak en iyisi.
        //Değilse runtime da hata.

        switch (cmdType) {
            case Update:
                return new BatchCommandUpdate<>(cls, tdb);
            case Insert:
                return this.createBatchCommandInsert(cls, tdb);
            case Delete:
                return new BatchCommandDelete<>(cls, tdb);
            default:
                throw new UnsupportedOperationException(cmdType.toString());
        }
    }
}
