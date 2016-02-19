package ionix.Data;

import ionix.Utils.Lockable;
import ionix.Utils.Locked;


public class SchemaInfo implements Lockable, Cloneable {

    private boolean _isLocked;

    public SchemaInfo(String columnName) {
        this.columnName = new Locked<>(columnName);
        this.dataClass = new Locked<>(null);
        this.isNullable = new Locked<>(false);
        this.isKey = new Locked<>(false);
        this.readOnly = new Locked<>(false);
        this.databaseGeneratedOption = new Locked<>(StoreGeneratedPattern.None);
        this.defaultValue = new Locked<>(null);
        this.maxLength = new Locked<>(0);
        this.order = new Locked<>(0);
        this.sqlValueType = new Locked<>(SqlValueType.Parameterized);
    }

    public SchemaInfo(){
        this(null);
    }

    private Locked<String> columnName;
    public String getColumnName() {
        return this.columnName.getValue();
    }
    public SchemaInfo setColumnName(String value) {
        this.columnName.setValue(value);
        return this;
    }


    private Locked<Class> dataClass;
    public Class getDataClass() {
        return this.dataClass.getValue();
    }
    public SchemaInfo setDataClass(Class value){
        this.dataClass.setValue(value);
        return this;
    }

    private Locked<Boolean> isNullable;
    public boolean getIsNullable() {
        return this.isNullable.getValue();
    }
    public SchemaInfo setIsNullable(boolean value){
        this.isNullable.setValue(value);
        return this;
    }

    private Locked<Boolean> isKey;
    public boolean getIsKey() {
        return this.isKey.getValue();
    }
    public SchemaInfo setIsKey(boolean value){
        this.isKey.setValue(value);
        return this;
    }

    private Locked<Boolean> readOnly;
    public boolean getReadOnly() {
        return this.readOnly.getValue();
    }
    public SchemaInfo setReadOnly(boolean value){
        this.readOnly.setValue(value);
        return this;
    }

    private Locked<StoreGeneratedPattern> databaseGeneratedOption;
    public StoreGeneratedPattern getDatabaseGeneratedOption(){
        return this.databaseGeneratedOption.getValue();
    }
    public SchemaInfo setDatabaseGeneratedOption(StoreGeneratedPattern value){
        this.databaseGeneratedOption.setValue(value);
        return this;
    }

    private Locked<String> defaultValue;
    public String getDefaultValue(){
        return this.defaultValue.getValue();
    }
    public SchemaInfo setDefaultValue(String value){
        this.defaultValue.setValue(value);
        return this;
    }

    private Locked<Integer> maxLength;
    public int getMaxLength(){
        return this.maxLength.getValue();
    }
    public SchemaInfo setMaxLength(int value){
        this.maxLength.setValue(value);
        return this;
    }

    private Locked<Integer> order;
    public int getOrder(){
        return this.order.getValue();
    }
    public SchemaInfo setOrder(int value){
        this.order.setValue(value);
        return this;
    }

    private Locked<SqlValueType> sqlValueType;
    public SqlValueType getSqlValueType(){
        return this.sqlValueType.getValue();
    }
    public SchemaInfo setSqlValueType(SqlValueType value){
        this.sqlValueType.setValue(value);
        return this;
    }



    @Override
    public void lock() {
        this.columnName.lock();
        this.dataClass.lock();
        this.isNullable.lock();

        this.isKey.lock();
        this.readOnly.lock();

        this.databaseGeneratedOption.lock();
        this.defaultValue.lock();

        this.maxLength.lock();
        this.order.lock();

        this.sqlValueType.lock();

        this._isLocked = true;
    }

    @Override
    public void unLock() {
        this.columnName.unLock();
        this.dataClass.unLock();
        this.isNullable.unLock();

        this.isKey.unLock();
        this.readOnly.unLock();

        this.databaseGeneratedOption.unLock();
        this.defaultValue.unLock();

        this.maxLength.unLock();
        this.order.unLock();

        this.sqlValueType.unLock();

        this._isLocked = false;
    }

    @Override
    public boolean isLocked() {
        return this._isLocked;
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof SchemaInfo)
            this.getColumnName().equals(((SchemaInfo)other).getColumnName());
        return false;
    }

    @Override
    public int hashCode() {
        String columnName = this.getColumnName();
        return  columnName == null ? 0 : columnName.hashCode();
    }

    @Override
    public String toString(){
        return this.getColumnName();
    }

    @Override
    public SchemaInfo clone(){
        SchemaInfo ret = new SchemaInfo();
        ret.setColumnName(this.getColumnName());
        ret.setDataClass(this.getDataClass());
        ret.setIsNullable(this.getIsNullable());

        ret.setIsKey(this.getIsKey());
        ret.setReadOnly(this.getReadOnly());

        ret.setDatabaseGeneratedOption(this.getDatabaseGeneratedOption());
        ret.setDefaultValue(this.getDefaultValue());

        ret.setMaxLength(this.getMaxLength());
        ret.setOrder(this.getOrder());

        ret.setSqlValueType(this.getSqlValueType());

        return ret;
    }
}
