package alysson.com.br.aplicativo.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import alysson.com.br.aplicativo.R;
import alysson.com.br.aplicativo.model.Empresa;
import alysson.com.br.aplicativo.repository.EmpresaRepository;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginEmpresaActivity extends AppCompatActivity{
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "teste@examplo.com:12345", "bar@examplo.com:12345"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText edtCNPJ;
    private EditText edtSenha;
    private ProgressBar mProgressView;
    private View mLoginFormView;
    private TextView txtCadastreSe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_empresa);

        // Set up the login form.
        edtCNPJ = (EditText) findViewById(R.id.edt_cnpj_login);
        txtCadastreSe = (TextView) findViewById(R.id.txt_cadastre_se);

        edtSenha = (EditText) findViewById(R.id.password);
        edtSenha.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = (ProgressBar) findViewById(R.id.login_progress);

        txtCadastreSe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CadastroEmpresaActivity.class));
            }
        });

        MaskEditTextChangedListener cnpjMask = new MaskEditTextChangedListener("##.###.###/####-##", edtCNPJ);
        edtCNPJ.addTextChangedListener(cnpjMask);


    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual lgino attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        edtCNPJ.setError(null);
        edtSenha.setError(null);

        // Store values at the time of the login attempt.
        String cnpj = edtCNPJ.getText().toString();
        String password = edtSenha.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)){
            edtSenha.setError(getString(R.string.error_invalid_password));
            focusView = edtSenha;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(cnpj)) {
            edtCNPJ.setError(getString(R.string.error_field_required));
            focusView = edtCNPJ;
            cancel = true;

        } else if (!isCNPJValid(cnpj)) {
            edtCNPJ.setError("Esse CNPJ não é válido!");
            focusView = edtCNPJ;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(cnpj, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isCNPJValid(String cnpj) {
        return cnpj.length() == 18;
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mCNPJ;
        private final String mPassword;

        UserLoginTask(String cnpj, String password) {
            mCNPJ = cnpj;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            EmpresaRepository empresaRepository = new EmpresaRepository(getApplicationContext());
            Empresa autenticada = empresaRepository.login(mCNPJ, mPassword);

            return autenticada != null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success){
                startActivity(new Intent(getApplicationContext(), MainActivicty.class));
                finish();

            } else {
                edtSenha.setError(getString(R.string.error_incorrect_password));
                edtSenha.requestFocus();
            }

            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

