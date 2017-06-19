package alysson.com.br.aplicativo.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import alysson.com.br.aplicativo.R;
import alysson.com.br.aplicativo.database.Database;
import alysson.com.br.aplicativo.model.TipoAtendimento;
import alysson.com.br.aplicativo.repository.TipoAtendimentoRepository;

import static android.content.Context.MODE_PRIVATE;


public class PesquisaTipoAtendimentoFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    /* Widgets utilizados nessa activity */
    private ImageButton imgBuscar;
    private ListView lstTiposAtendimento;
    private EditText edtBusca;

    private ArrayAdapter<TipoAtendimento> adpTiposAtendimento;

    private TipoAtendimentoRepository tipoAtendimentoRepository;

    public PesquisaTipoAtendimentoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pesquisa_tipo_atendimento, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_add_tipo_atendimento);
        fab.setOnClickListener(this);

        imgBuscar = (ImageButton) rootView.findViewById(R.id.img_btn_buscar_tipo_atendimento);
        lstTiposAtendimento = (ListView) rootView.findViewById(R.id.lst_tipos_atendimento);
        edtBusca = (EditText) rootView.findViewById(R.id.edt_busca_tipo_atendimento);

        lstTiposAtendimento.setOnItemClickListener(this);

        tipoAtendimentoRepository = new TipoAtendimentoRepository(getContext());

        SharedPreferences preferences =  getActivity().getSharedPreferences("SISFILA_PREFERENCES", MODE_PRIVATE);
        Long id = preferences.getLong("id_empresa", 1L);

        adpTiposAtendimento = tipoAtendimentoRepository.listar(getContext(), id);
        lstTiposAtendimento.setAdapter(adpTiposAtendimento);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, new CadastroTipoAtendimentoFragment());
        fragmentTransaction.commit();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Editar Tipo de Atendimento");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        TipoAtendimento tipoAtendimento =  adpTiposAtendimento.getItem(position);

        Bundle bundle = new Bundle();
        bundle.putSerializable("TIPO_ATENDIMENTO", tipoAtendimento);

        CadastroTipoAtendimentoFragment cadastroTipoAtendimentoFragment = new CadastroTipoAtendimentoFragment();
        cadastroTipoAtendimentoFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, cadastroTipoAtendimentoFragment);
        fragmentTransaction.commit();
    }

}
