package alysson.com.br.aplicativo.fragments;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import alysson.com.br.aplicativo.R;
import alysson.com.br.aplicativo.database.Database;
import alysson.com.br.aplicativo.model.Empresa;
import alysson.com.br.aplicativo.model.TipoAtendimento;
import alysson.com.br.aplicativo.repository.TipoAtendimentoRepository;

import static android.content.Context.MODE_PRIVATE;


public class CadastroTipoAtendimentoFragment extends Fragment implements View.OnClickListener {

    private EditText edtDescricao;

    private TipoAtendimento tipoAtendimento;

    private TipoAtendimentoRepository tipoAtendimentoRepository;

    public CadastroTipoAtendimentoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cadastro_tipo_atendimento, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_salvar_tipo_atendimento);
        fab.setOnClickListener(this);

        edtDescricao = (EditText) rootView.findViewById(R.id.edt_descricao);
        edtDescricao.requestFocus();

        Bundle bundle = getArguments();

        if((bundle != null) && (bundle.containsKey("TIPO_ATENDIMENTO"))){
            tipoAtendimento = (TipoAtendimento) bundle.getSerializable("TIPO_ATENDIMENTO");
            edtDescricao.setText(tipoAtendimento.getDescricao());

        } else {
            tipoAtendimento = new TipoAtendimento();

            SharedPreferences preferences =  getActivity().getSharedPreferences("SISFILA_PREFERENCES", MODE_PRIVATE);
            Long id = preferences.getLong("id_empresa", 1L);

            Empresa empresa = new Empresa();
            empresa.setId(id);

            tipoAtendimento.setEmpresa(empresa);
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {
        try {
            if(!edtDescricao.getText().toString().trim().isEmpty()){

                tipoAtendimento.setDescricao(edtDescricao.getText().toString());
                tipoAtendimentoRepository = new TipoAtendimentoRepository(getContext());

                tipoAtendimentoRepository.salvar(tipoAtendimento);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new PesquisaTipoAtendimentoFragment());
                fragmentTransaction.commit();

                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tipos de Atendimento");

                Toast.makeText(getContext(), "Tipo de Atendimento salvo com sucesso!", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();
            }


        }catch(Exception ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
            dlg.setMessage("Erro ao inserir os dados: "+ ex);
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }
    }

}
