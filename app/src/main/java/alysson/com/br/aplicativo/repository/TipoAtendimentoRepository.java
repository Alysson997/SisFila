package alysson.com.br.aplicativo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import alysson.com.br.aplicativo.database.Database;
import alysson.com.br.aplicativo.model.Empresa;
import alysson.com.br.aplicativo.model.TipoAtendimento;

/**
 * Created by Alysson on 26/01/2016.
 * Esta classe contém as regras de acesso aos dados referentes aos tipos de atendimento
 */
public class TipoAtendimentoRepository extends Database<TipoAtendimento>{

    private SQLiteDatabase database;

    public TipoAtendimentoRepository(Context context){
        super(context);
        database = getWritableDatabase();
    }

    @Override
    public void salvar(TipoAtendimento tipoAtendimento){

        if(tipoAtendimento.getId() == null){
            inserir(tipoAtendimento);

        }else{
            atualizar(tipoAtendimento);
        }

    }

    @Override
    public void inserir(TipoAtendimento tipoAtendimento){
        ContentValues values = preencheContentValues(tipoAtendimento);
        database.insertOrThrow("TIPO_ATENDIMENTO", null, values);
    }

    @Override
    public void atualizar(TipoAtendimento tipoAtendimento){
        ContentValues values = preencheContentValues(tipoAtendimento);
        database.update("TIPO_ATENDIMENTO", values, "_id = ?", new String[]{String.valueOf(tipoAtendimento.getId())});
    }

    @Override
    public ContentValues preencheContentValues(TipoAtendimento tipoAtendimento){
        ContentValues values = new ContentValues();
        values.put("DESCRICAO", tipoAtendimento.getDescricao());
        values.put("EMPRESA_ID", tipoAtendimento.getEmpresa().getId());

        return values;
    }

    @Override
    public void excluir(Long id){
        database.delete("TIPO_ATENDIMENTO", "_id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public ArrayAdapter<TipoAtendimento> listar(Context context){

        ArrayAdapter<TipoAtendimento> adpLista = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = database.query("TIPO_ATENDIMENTO", null, null, null, null, null, null);

        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();

            do{
                TipoAtendimento tipoAtendimento = new TipoAtendimento();
                tipoAtendimento.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                tipoAtendimento.setDescricao(cursor.getString(cursor.getColumnIndex("DESCRICAO")));

                adpLista.add(tipoAtendimento);

            }while(cursor.moveToNext());

        }

        cursor.close();
        return adpLista;
    }

    public ArrayAdapter<TipoAtendimento> listarTeste(Context context, Long id){
        ArrayAdapter<TipoAtendimento> adpLista = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);

        String query = "SELECT * FROM TIPO_ATENDIMENTO WHERE EMPRESA_ID = '" + id+ "';";
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();

            do{
                TipoAtendimento tipoAtendimento = new TipoAtendimento();
                tipoAtendimento.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                tipoAtendimento.setDescricao(cursor.getString(cursor.getColumnIndex("DESCRICAO")));

                Empresa empresa = new Empresa();
                empresa.setId(cursor.getLong(cursor.getColumnIndex("EMPRESA_ID")));

                tipoAtendimento.setEmpresa(empresa);

                adpLista.add(tipoAtendimento);

            }while(cursor.moveToNext());

        }

        cursor.close();
        return adpLista;
    }

}
