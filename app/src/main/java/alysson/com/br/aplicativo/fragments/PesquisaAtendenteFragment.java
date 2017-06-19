package alysson.com.br.aplicativo.fragments;

import android.content.SharedPreferences;
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
import alysson.com.br.aplicativo.model.Atendente;
import alysson.com.br.aplicativo.model.TipoAtendimento;
import alysson.com.br.aplicativo.repository.AtendenteRepository;
import alysson.com.br.aplicativo.repository.TipoAtendimentoRepository;

import static android.content.Context.MODE_PRIVATE;


public class PesquisaAtendenteFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    /* Widgets utilizados nessa activity */
    private ImageButton imgBuscar;
    private ListView lstAtendentes;
    private EditText edtBusca;

    private ArrayAdapter<Atendente> adpAtendentes;

    private AtendenteRepository atendenteRepository;

    public PesquisaAtendenteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pesquisa_atendente, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_add_atendente);
        fab.setOnClickListener(this);

        imgBuscar = (ImageButton) rootView.findViewById(R.id.img_btn_buscar_atendente);
        lstAtendentes = (ListView) rootView.findViewById(R.id.lst_atendentes);
        edtBusca = (EditText) rootView.findViewById(R.id.edt_busca_atendente);

        lstAtendentes.setOnItemClickListener(this);

        atendenteRepository = new AtendenteRepository(getContext());

        SharedPreferences preferences =  getActivity().getSharedPreferences("SISFILA_PREFERENCES", MODE_PRIVATE);
        Long id = preferences.getLong("id_empresa", 1L);

        adpAtendentes = atendenteRepository.listar(getContext(), id);

        lstAtendentes.setAdapter(adpAtendentes);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, new CadastroAtendenteFragment());
        fragmentTransaction.commit();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Editar Atendente");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Atendente atendente =  adpAtendentes.getItem(position);

        Bundle bundle = new Bundle();
        bundle.putSerializable("ATENDENTE", atendente);

        CadastroAtendenteFragment cadastroAtendenteFragment = new CadastroAtendenteFragment();
        cadastroAtendenteFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_container, cadastroAtendenteFragment);
        fragmentTransaction.commit();
    }

}
