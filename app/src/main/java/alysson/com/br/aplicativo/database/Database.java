package alysson.com.br.aplicativo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.List;

import alysson.com.br.aplicativo.database.ScriptSQL;

public abstract class Database<T> extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "SISFILA";
    private static final int VERSAO_BANCO = 1;

    public Database(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateEmpresa());
        db.execSQL(ScriptSQL.getCreateuUsuario());
        db.execSQL(ScriptSQL.getCreateTipoAtendimento());
        db.execSQL(ScriptSQL.getCreateAtendente());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public abstract void salvar(T t);
    public abstract void inserir(T t);
    public abstract void atualizar(T t);
    public abstract void excluir(Long id);
    public abstract ContentValues preencheContentValues(T t);
    public abstract ArrayAdapter<T> listar(Context context);
    public abstract ArrayAdapter<T> listar(Context context, Long id);

}




























