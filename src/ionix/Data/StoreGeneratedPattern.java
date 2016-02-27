package ionix.Data;


public enum StoreGeneratedPattern {
    None,//Guid, Manuel Sequence Value. (insert list de olacak)
    Identity,//Identity Column. (insert list de olmayacak). Kısıt olarak IEntityMetaData.Properties de mutlaka tekil olmalı.
    Computed,//Column with Default Value(i.e getdate(), deleted 0,), Guid as DefaultValue, Next Sequence Value as Default Value.
    AutoGenerateSequence//Classical Oracle Sequence and returning next value like identity. Kısıt olarak IEntityMetaData.Properties de mutlaka tekil olmalı.
}

