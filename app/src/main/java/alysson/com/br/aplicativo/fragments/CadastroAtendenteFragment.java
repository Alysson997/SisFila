package alysson.com.br.aplicativo.fragments;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import alysson.com.br.aplicativo.R;
import alysson.com.br.aplicativo.model.Atendente;
import alysson.com.br.aplicativo.model.Empresa;
import alysson.com.br.aplicativo.model.TipoAtendimento;
import alysson.com.br.aplicativo.repository.AtendenteRepository;
import alysson.com.br.aplicativo.repository.TipoAtendimentoRepository;

import static android.content.Context.MODE_PRIVATE;


public class CadastroAtendenteFragment extends Fragment implements View.OnClickListener {

    private EditText edtNome;
    private EditText edtEmail;

    private Spinner spnSexo;
    private ArrayAdapter adpSexo;

    private Atendente atendente;

    private AtendenteRepository atendenteRepository;

    public CadastroAtendenteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cadastro_atendente, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_salvar_atendente);
        fab.setOnClickListener(this);

        adpSexo = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item);
        adpSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        edtNome = (EditText) rootView.findViewById(R.id.edt_nome_atendente);
        edtEmail = (EditText) rootView.findViewById(R.id.edt_email_atendente);
        spnSexo = (Spinner) rootView.findViewById(R.id.spn_sexo);

        adpSexo.add("Selecione");
        adpSexo.add("Masculino");
        adpSexo.add("Feminino");

        spnSexo.setAdapter(adpSexo);

        edtNome.requestFocus();

        Bundle bundle = getArguments();

        if((bundle != null) && (bundle.containsKey("ATENDENTE"))){
            atendente = (Atendente) bundle.getSerializable("ATENDENTE");
            edtNome.setText(atendente.getNome());
            edtEmail.setText(atendente.getEmail());

        } else {
            atendente = new Atendente();

            SharedPreferences preferences =  getActivity().getSharedPreferences("SISFILA_PREFERENCES", MODE_PRIVATE);
            Long id = preferences.getLong("id_empresa", 1L);

            Empresa empresa = new Empresa();
            empresa.setId(id);

            atendente.setEmpresa(empresa);
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {
        try {

            String sexo = (String) spnSexo.getSelectedItem();

            if(TextUtils.isEmpty(edtEmail.getText().toString()) || TextUtils.isEmpty(edtNome.getText().toString()) || sexo.equals("Selecione")){
                Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();

            } else {
                atendente.setNome(edtNome.getText().toString());
                atendente.setEmail(edtEmail.getText().toString());
                atendente.setSexo((String) spnSexo.getSelectedItem());

                atendenteRepository = new AtendenteRepository(getContext());
                atendenteRepository.salvar(atendente);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new PesquisaAtendenteFragment());
                fragmentTransaction.commit();

                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Atendentes");

                Toast.makeText(getContext(), "Atendente salvo com sucesso!", Toast.LENGTH_LONG).show();
            }


            if(! edtNome.getText().toString().trim().isEmpty() || edtEmail.getText().toString().trim().isEmpty()){






            } else {

            }


        }catch(Exception ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
            dlg.setMessage("Erro ao inserir os dados: "+ ex);
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }
    }

}
