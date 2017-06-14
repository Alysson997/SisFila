package alysson.com.br.aplicativo.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import alysson.com.br.aplicativo.database.Database;
import alysson.com.br.aplicativo.model.Usuario;

/**
 * Created by Alysson on 26/01/2016.
 * Esta classe contém as regras de negocio referentes aos ususarios
 */
public class UsuarioRepository extends Database<Usuario>{

    private SQLiteDatabase database;

    public UsuarioRepository(Context context){
        super(context);
        database = getWritableDatabase();
    }

    @Override
    public void salvar(Usuario usuario){
        if(usuario.getId() == null){
            inserir(usuario);

        }else{
            atualizar(usuario);
        }
    }

    @Override
    public void inserir(Usuario usuario){
        ContentValues values = preencheContentValues(usuario);
        database.insertOrThrow("USUARIO", null, values);
    }

    @Override
    public void atualizar(Usuario usuario){
        ContentValues values = preencheContentValues(usuario);
        database.update("USUARIO", values, "_id = ?", new String[]{String.valueOf(usuario.getId())});
    }

    @Override
    public ContentValues preencheContentValues(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put("NOME", usuario.getNome());
        values.put("EMAIL", usuario.getEmail());
        values.put("SENHA", usuario.getSenha());
        values.put("EMPRESA_ID", usuario.getEmpresa().getId());

        return values;
    }

    @Override
    public void excluir(Long id){
        database.delete("USUARIO", "_id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public ArrayAdapter<Usuario> listar(Context context){

        ArrayAdapter<Usuario> adpLista = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = database.query("USUARIO", null, null, null, null, null, null);

        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();

            do{
                Usuario usuario = new Usuario();
                usuario.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                usuario.setNome(cursor.getString(cursor.getColumnIndex("NOME")));

                adpLista.add(usuario);
            }while(cursor.moveToNext());

        }
        cursor.close();
        return adpLista;
    }

    public Usuario login(Usuario usuario){
        String query = "SELECT * FROM USUARIO WHERE SENHA = " + usuario.getSenha();
        Cursor cursor = database.rawQuery(query, null);

        Usuario userAutenticado = new Usuario();
        cursor.moveToFirst();

        userAutenticado.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        userAutenticado.setNome(cursor.getString(cursor.getColumnIndex("NOME")));

        return userAutenticado;
    }
}
