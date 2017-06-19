package alysson.com.br.aplicativo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.util.Date;

import alysson.com.br.aplicativo.database.Database;
import alysson.com.br.aplicativo.model.Atendente;
import alysson.com.br.aplicativo.model.Empresa;
import alysson.com.br.aplicativo.model.TipoAtendimento;

/**
 * Created by Alysson on 26/01/2016.
 * Esta classe contém as regras de acesso aos dados referentes aos Atendentes
 */
public class AtendenteRepository extends Database<Atendente>{

    private SQLiteDatabase database;

    public AtendenteRepository(Context context){
        super(context);
        database = getWritableDatabase();
    }

    @Override
    public void salvar(Atendente atendente){

        if(atendente.getId() == null){
            inserir(atendente);

        }else{
            atualizar(atendente);
        }

    }

    @Override
    public void inserir(Atendente atendente){
        ContentValues values = preencheContentValues(atendente);
        database.insertOrThrow("ATENDENTE", null, values);
    }

    @Override
    public void atualizar(Atendente atendente){
        ContentValues values = preencheContentValues(atendente);
        database.update("ATENDENTE", values, "_id = ?", new String[]{String.valueOf(atendente.getId())});
    }

    @Override
    public ContentValues preencheContentValues(Atendente atendente){
        ContentValues values = new ContentValues();
        values.put("NOME", atendente.getNome());
        values.put("EMAIL", atendente.getEmail());
        values.put("SEXO", atendente.getSexo());
        //values.put("DATA_NASCIMENTO", atendente.getDataNascimento().getTime());
        values.put("EMPRESA_ID", atendente.getEmpresa().getId());

        return values;
    }

    @Override
    public void excluir(Long id){
        database.delete("ATENDENTE", "_id = ?", new String[]{String.valueOf(id)});
    }

    public ArrayAdapter<Atendente> listar(Context context, Long id){

        ArrayAdapter<Atendente> adpLista = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);

        String query = "SELECT * FROM ATENDENTE WHERE EMPRESA_ID = '" + id+ "';";
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();

            do{
                Atendente atendente = new Atendente();

                atendente.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                atendente.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
                atendente.setDataNascimento(new Date(cursor.getLong(cursor.getColumnIndex("DATA_NASCIMENTO"))));

                Empresa empresa = new Empresa();
                empresa.setId(cursor.getLong(cursor.getColumnIndex("EMPRESA_ID")));

                atendente.setEmpresa(empresa);

                adpLista.add(atendente);

            }while(cursor.moveToNext());

        }

        cursor.close();
        return adpLista;
    }

    @Override
    public ArrayAdapter<Atendente> listar(Context context) {
        return null;
    }

}
