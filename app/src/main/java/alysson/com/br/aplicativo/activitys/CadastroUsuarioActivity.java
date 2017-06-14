package alysson.com.br.aplicativo.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import alysson.com.br.aplicativo.R;
import alysson.com.br.aplicativo.model.Empresa;
import alysson.com.br.aplicativo.model.Usuario;
import alysson.com.br.aplicativo.repository.EmpresaRepository;
import alysson.com.br.aplicativo.repository.UsuarioRepository;

public class CadastroUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCadastrar;
    private EditText edtNome;
    private EditText edtSenha;
    private EditText edtConfirmaSenha;
    private AutoCompleteTextView actEmail;
    private Empresa empresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        btnCadastrar = (Button) findViewById(R.id.btn_cadastrar);
        edtNome = (EditText) findViewById(R.id.edt_nome);
        edtSenha = (EditText) findViewById(R.id.edt_senha);
        edtConfirmaSenha = (EditText) findViewById(R.id.edt_confirma_senha);
        actEmail = (AutoCompleteTextView) findViewById(R.id.act_email);

        btnCadastrar.setOnClickListener(this);

        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("EMPRESA")){
            empresa = (Empresa) bundle.getSerializable("EMPRESA");
        }

    }

    @Override
    public void onClick(View view) {

        if (view == btnCadastrar) {

            String nome = edtNome.getText().toString();
            String email = actEmail.getText().toString();
            String senha = edtSenha.getText().toString();
            String confirmaSenha = edtConfirmaSenha.getText().toString();

            if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(senha) || TextUtils.isEmpty(confirmaSenha) || TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();

            } else if (! isEmailValid(email)) {
                actEmail.setError("Informe um email válido!");
                actEmail.requestFocus();

            } else if (! isPasswordValid(senha, confirmaSenha)) {
                Toast.makeText(this, "Senha invalida!", Toast.LENGTH_SHORT).show();

            } else{

                Usuario usuario = new Usuario();
                usuario.setNome(nome);
                usuario.setEmail(email);
                usuario.setSenha(senha);

                EmpresaRepository empresaRepository = new EmpresaRepository(getApplicationContext());
                empresaRepository.salvar(empresa);

                empresa = empresaRepository.retorneUltimoRegistro();

                usuario.setEmpresa(empresa);

                UsuarioRepository usuarioRepository = new UsuarioRepository(getApplicationContext());
                usuarioRepository.inserir(usuario);

                //Cadastrar
                startActivity(new Intent(getApplicationContext(), MainActivicty.class));
            }

        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String senha, String confirmaSenha) {
        return senha.length() > 4 && senha.equals(confirmaSenha);
    }

}
