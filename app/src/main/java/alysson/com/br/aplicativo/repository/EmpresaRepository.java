package alysson.com.br.aplicativo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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
        Log.e("DEBUG", empresa.getCnpj());
        Log.e("DEBUG", empresa.getSenha());
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
        values.put("FANTASIA", empresa.getFantasia());
        values.put("CNPJ", empresa.getCnpj());
        values.put("EMAIL", empresa.getEmail());
        values.put("TELEFONE", empresa.getTelefone());
        values.put("CEP", empresa.getCep());
        values.put("LOGRADOURO", empresa.getLogradouro());
        values.put("SENHA", empresa.getSenha());

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

    public Empresa retorneUltimoRegistro(){
        String query = "SELECT * FROM EMPRESA WHERE _id = (SELECT MAX(_id) FROM EMPRESA)";
        Cursor cursor = database.rawQuery(query, null);

        Empresa empresa = new Empresa();
        cursor.moveToFirst();

        empresa.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        empresa.setRazaoSocial(cursor.getString(cursor.getColumnIndex("RAZAO_SOCIAL")));
        empresa.setFantasia(cursor.getString(cursor.getColumnIndex("FANTASIA")));
        empresa.setCnpj(cursor.getString(cursor.getColumnIndex("CNPJ")));
        empresa.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
        empresa.setTelefone(cursor.getString(cursor.getColumnIndex("TELEFONE")));
        empresa.setCep(cursor.getString(cursor.getColumnIndex("CEP")));
        empresa.setLogradouro(cursor.getString(cursor.getColumnIndex("LOGRADOURO")));

        return empresa;
    }

    public Empresa login(String cnpj, String senha){
        String query = "SELECT * FROM EMPRESA WHERE CNPJ = '" + cnpj+"' AND SENHA = '" + senha + "';";

        try {
            Cursor cursor = database.rawQuery(query, null);

            Empresa empresaAutenticado = new Empresa();
            cursor.moveToFirst();

            empresaAutenticado.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            empresaAutenticado.setRazaoSocial(cursor.getString(cursor.getColumnIndex("RAZAO_SOCIAL")));
            empresaAutenticado.setFantasia(cursor.getString(cursor.getColumnIndex("FANTASIA")));
            empresaAutenticado.setCnpj(cursor.getString(cursor.getColumnIndex("CNPJ")));
            empresaAutenticado.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
            empresaAutenticado.setTelefone(cursor.getString(cursor.getColumnIndex("TELEFONE")));
            empresaAutenticado.setCep(cursor.getString(cursor.getColumnIndex("CEP")));
            empresaAutenticado.setLogradouro(cursor.getString(cursor.getColumnIndex("LOGRADOURO")));
            empresaAutenticado.setSenha(cursor.getString(cursor.getColumnIndex("SENHA")));

            return empresaAutenticado;

        } catch (Exception e){
            Log.e("DEBUG", "USUARIO INEXISTENTE: " + e.getMessage());
            return null;
        }


    }

}
