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

public class CadastroUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCadastrar;
    private EditText edtNome;
    private EditText edtSenha;
    private EditText edtConfirmaSenha;
    private AutoCompleteTextView actEmail;


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

    }

    @Override
    public void onClick(View view) {

        if (view == btnCadastrar) {
            Log.d("DEBUG", "Acionou botão cadastrar");

            if (TextUtils.isEmpty(edtNome.getText().toString()) || TextUtils.isEmpty(edtSenha.getText().toString()) ||
                    TextUtils.isEmpty(edtConfirmaSenha.getText().toString()) || TextUtils.isEmpty(actEmail.getText().toString())) {

                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();

            } else if (! isEmailValid(actEmail.getText().toString())) {
                actEmail.setError("Informe um email válido!");

            } else if (! isPasswordValid(edtSenha.getText().toString(), edtConfirmaSenha.getText().toString())) {
                Toast.makeText(this, "Senha invalida!", Toast.LENGTH_SHORT).show();

            } else{

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
