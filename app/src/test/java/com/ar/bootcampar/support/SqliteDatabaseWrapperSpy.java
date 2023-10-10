package com.ar.bootcampar.support;

import com.ar.bootcampar.model.IContentValuesWrapper;
import com.ar.bootcampar.model.ICursorWrapper;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;

public class SqliteDatabaseWrapperSpy implements ISQLiteDatabaseWrapper {
    public static class Builder {
        private int resultadoDelete;
        private long resultadoInsert;
        private int resultadoUpdate;
        private ICursorWrapper resultadoQuery;

        public Builder conDeleteRetornando(int resultado) {
            resultadoDelete = resultado;
            return this;
        }

        public Builder conInsertRetornando(long resultado) {
            resultadoInsert = resultado;
            return this;
        }

        public Builder conQueryRetornando(ICursorWrapper resultado) {
            resultadoQuery = resultado;
            return this;
        }

        public Builder conUpdateRetornando(int resultado) {
            resultadoUpdate = resultado;
            return this;
        }

        public SqliteDatabaseWrapperSpy build() {
            return new SqliteDatabaseWrapperSpy(resultadoInsert, resultadoDelete, resultadoQuery, resultadoUpdate);
        }
    }

    private String tableName;
    private IContentValuesWrapper insertedValues;
    private boolean transactionSuccessfulCalled;
    private boolean closeCalled;
    private boolean beginTransactionCalled;
    private boolean endTransactionCalled;
    private String whereClause;
    private String[] whereArgs;
    private final int resultadoDelete;
    private final long resultadoInsert;
    private final int resultadoUpdate;
    private final ICursorWrapper resultadoQuery;
    private String[] columns;
    private String selection;
    private String[] selectionArgs;
    private String groupBy;
    private String having;
    private String orderBy;

    private SqliteDatabaseWrapperSpy(long resultadoInsert, int resultadoDelete, ICursorWrapper resultadoQuery, int resultadoUpdate) {
        this.resultadoInsert = resultadoInsert;
        this.resultadoDelete = resultadoDelete;
        this.resultadoQuery = resultadoQuery;
        this.resultadoUpdate = resultadoUpdate;
    }

    @Override
    public void close() {
        this.closeCalled = true;
    }

    @Override
    public ICursorWrapper query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        tableName = table;
        this.columns = columns;
        this.selection = selection;
        this.selectionArgs = selectionArgs;
        this.groupBy = groupBy;
        this.having = having;
        this.orderBy = orderBy;
        return resultadoQuery;
    }

    @Override
    public void endTransaction() {
        this.endTransactionCalled = true;
    }

    @Override
    public void setTransactionSuccessful() {
        this.transactionSuccessfulCalled = true;
    }

    @Override
    public void beginTransaction() {
        this.beginTransactionCalled = true;
    }

    @Override
    public long insert(String table, String nullColumnHack, IContentValuesWrapper values) {
        tableName = table;
        insertedValues = values;
        return resultadoInsert;
    }

    @Override
    public int delete(String table, String whereClause, String[] whereArgs) {
        tableName = table;
        this.whereClause = whereClause;
        this.whereArgs = whereArgs;
        return resultadoDelete;
    }

    @Override
    public int update(String table, IContentValuesWrapper values, String whereClause, String[] whereArgs) {
        tableName = table;
        insertedValues = values;
        this.whereClause = whereClause;
        this.whereArgs = whereArgs;
        return resultadoUpdate;
    }

    @Override
    public void execSQL(String sql) {
    }

    public String getTableName() { return tableName; }

    public IContentValuesWrapper getInsertedValues() { return insertedValues; }

    public boolean getBeginTransactionCalled() { return beginTransactionCalled; }
    public boolean getEndTransactionCalled() { return endTransactionCalled; }
    public boolean getTransactionSuccessfulCalled() { return transactionSuccessfulCalled; }
    public boolean getCloseCalled() { return closeCalled; }
    public String getWhereClause() { return whereClause; }
    public String[] getWhereArgs() { return whereArgs; }
    public String[] getColumns() { return columns; }
    public String[] getSelectionArgs() { return selectionArgs; }
}