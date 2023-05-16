public class SQLTerm {
    private String strTableName;
    private String strColumnName;
    private String strOperator;
    private Object objValue;

    public SQLTerm(String tableName, String columnName, String operator, Object value) {
        strTableName = tableName;
        strColumnName = columnName;
        strOperator = operator;
        objValue = value;
    }

    public String getStrTableName() {
        return strTableName;
    }

    public String getStrColumnName() {
        return strColumnName;
    }

    public String getStrOperator() {
        return strOperator;
    }

    public Object getObjValue() {
        return objValue;
    }

    @Override
    public String toString() {
        return "SQLTerm [strTableName=" + strTableName + ", strColumnName=" + strColumnName + ", strOperator="
                + strOperator + ", objValue=" + objValue + "]";
    }


    
}

