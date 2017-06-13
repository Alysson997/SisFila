package alysson.com.br.aplicativo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import alysson.com.br.aplicativo.database.Database;
import alysson.com.br.aplicativo.model.Empresa;
import alysson.com.br.aplicativo.model.Usuario;

/**
 * Created by Alysson on 26/01/2016.
 * Esta classe contém as regras de acesso aos dados referentes a empresas
 */
public class EmpresaRepository extends Database<Empresa>{

    private SQLiteDatabase database;

    public EmpresaRepository(Context context){
        super(context);
        database = getWritableDatabase();
    }

    @Override
    public void salvar(Empresa empresa){

        if(empresa.getId() == null){
            inserir(empresa);

        }else{
            atualizar(empresa);
        }

    }

    @Override
    public void inserir(Empresa empresa){
        ContentValues values = preencheContentValues(empresa);
        database.insertOrThrow("EMPRESA", null, values);
    }

    @Override
    public void atualizar(Empresa empresa){
        ContentValues values = preencheContentValues(empresa);
        database.update("EMPRESA", values, "_id = ?", new String[]{String.valueOf(empresa.getId())});
    }

    @Override
    public ContentValues preencheContentValues(Empresa empresa){
        ContentValues values = new ContentValues();
        values.put("RAZAO_SOCIAL", empresa.getRazaoSocial());
        values.put("FANTASIA", empresa.getRazaoSocial());
        values.put("CNPJ", empresa.getRazaoSocial());
        values.put("EMAIL", empresa.getRazaoSocial());
        values.put("TELEFONE", empresa.getRazaoSocial());
        values.put("CEP", empresa.getRazaoSocial());
        values.put("LOGRADOURO", empresa.getRazaoSocial());

        return values;
    }

    @Override
    public void excluir(Long id){
        database.delete("EMPRESA", "_id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public ArrayAdapter<Empresa> listar(Context context){

        ArrayAdapter<Empresa> adpLista = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = database.query("EMPRESA", null, null, null, null, null, null);

        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();

            do{
                Empresa empresa = new Empresa();
                empresa.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                empresa.setRazaoSocial(cursor.getString(cursor.getColumnIndex("RAZAO_SOCIAL")));

                adpLista.add(empresa);
            }while(cursor.moveToNext());

        }

        cursor.close();
        return adpLista;
    }

}
