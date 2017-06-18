package alysson.com.br.aplicativo.activitys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.TextView;

import alysson.com.br.aplicativo.R;
import alysson.com.br.aplicativo.fragments.PesquisaTipoAtendimentoFragment;
import alysson.com.br.aplicativo.model.Empresa;
import alysson.com.br.aplicativo.repository.EmpresaRepository;

public class MainActivicty extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FragmentTransaction fragmentTransaction;
    private DrawerLayout drawer;

    private TextView txtNomeEmpresa;
    private TextView txtEmail;

    private Empresa empresa;
    private EmpresaRepository  empresaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Instância a toolbar */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView =  navigationView.getHeaderView(0);

        txtNomeEmpresa = (TextView) navHeaderView.findViewById(R.id.txt_nome_empresa);
        txtEmail = (TextView) navHeaderView.findViewById(R.id.txt_email);

        configurarUsuario();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.main_container, new PesquisaClienteFragment());
        fragmentTransaction.commit();

        getSupportActionBar().setTitle("Pesquisa de cliente");


    }

    private void configurarUsuario() {
        SharedPreferences preferences = getSharedPreferences("SISFILA_PREFERENCES", MODE_PRIVATE);
        Long id = preferences.getLong("id_empresa", 1L);

        empresaRepository = new EmpresaRepository(getApplicationContext());
        empresa = empresaRepository.buscarPorID(id);

        if(empresa != null){
            Log.e("DEBUG", empresa.toString());
            txtNomeEmpresa.setText(empresa.getRazaoSocial());
            txtEmail.setText(empresa.getEmail());
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.nav_atendentes:
                /*fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new PesquisaClienteFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Pesquisa de clientes");
                item.setChecked(true);
                drawer.closeDrawers();*/
                break;

            case R.id.nav_tipos_atendimento:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new PesquisaTipoAtendimentoFragment());
                fragmentTransaction.commit();

                getSupportActionBar().setTitle("Tipos de Atendimentos");
                item.setChecked(true);
                drawer.closeDrawers();

                break;

            case R.id.nav_filas:

                break;

            case R.id.nav_atendimentos:
                
                break;
        }

        return true;
    }


}
