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
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastroEmpresaActivity extends AppCompatActivity implements View.OnClickListener {

    // UI references.
    private EditText edtEmail;
    private EditText edtRazaoSocial;
    private EditText edtFantasia;
    private EditText edtCNPJ;
    private EditText edtTelefone;
    private EditText edtCEP;
    private EditText edtLogradouro;
    private Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_empresa);

        edtEmail = (EditText) findViewById(R.id.edt_email_empresa);
        edtRazaoSocial = (EditText) findViewById(R.id.edt_razao_social);
        edtFantasia = (EditText) findViewById(R.id.edt_fantasia);
        edtCNPJ = (EditText) findViewById(R.id.edt_cnpj);
        edtTelefone = (EditText) findViewById(R.id.edt_telefone);
        edtCEP = (EditText) findViewById(R.id.edt_cep);
        edtLogradouro = (EditText) findViewById(R.id.edt_logradouro);
        btnContinuar = (Button) findViewById(R.id.btn_continuar);

        MaskEditTextChangedListener telefoneMask = new MaskEditTextChangedListener("(##)#####-####", edtTelefone);
        edtTelefone.addTextChangedListener(telefoneMask);

        MaskEditTextChangedListener cnpjMask = new MaskEditTextChangedListener("##.###.###/####-##", edtCNPJ);
        edtCNPJ.addTextChangedListener(cnpjMask);

        MaskEditTextChangedListener CEPMask = new MaskEditTextChangedListener("#####-###", edtCEP);
        edtCEP.addTextChangedListener(CEPMask);

        btnContinuar.setOnClickListener(this);

        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


    @Override
    public void onClick(View view) {

        if (view == btnContinuar) {

            String cnpj = edtCNPJ.getText().toString();
            String email = edtEmail.getText().toString();
            String cep = edtCEP.getText().toString();

            if (TextUtils.isEmpty(edtRazaoSocial.getText().toString()) || TextUtils.isEmpty(edtFantasia.getText().toString()) || TextUtils.isEmpty(cnpj) || TextUtils.isEmpty(email) || TextUtils.isEmpty(edtTelefone.getText().toString()) || TextUtils.isEmpty(cep) || TextUtils.isEmpty(edtLogradouro.getText().toString())) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();

            } else if (!isEmailValid(email)) {
                edtEmail.setError("Informe um email válido");

            } else if(!isCepValid(cep)){
                edtCEP.setError("Informe um CEP válido");

            } else if(!isCNPJValid(cnpj)){
                edtCNPJ.setError("Informe um CNPJ válido");

            } else {

                Empresa empresa = new Empresa();

                empresa.setRazaoSocial(edtRazaoSocial.getText().toString());
                empresa.setFantasia(edtFantasia.getText().toString());
                empresa.setCnpj(cnpj);
                empresa.setLogradouro(edtLogradouro.getText().toString());
                empresa.setCep(cep);
                empresa.setEmail(email);
                empresa.setTelefone(edtTelefone.getText().toString());

                Bundle bundle = new Bundle();
                bundle.putSerializable("EMPRESA", empresa);

                Intent intent = new Intent(this, CadastroUsuarioActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        }


    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isCepValid(String cep){
        return cep.length() == 9;
    }

    private boolean isCNPJValid(String cnpj){
        return cnpj.length() == 18;
    }

}
