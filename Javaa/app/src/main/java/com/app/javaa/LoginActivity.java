package com.app.javaa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // Instância do ServicePessoa para realizar a verificação de credenciais
    private ServicePessoa servicePessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicialização do ServicePessoa
        servicePessoa = ServicePessoa.getInstance();

        // Referências aos elementos da interface do usuário
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);


        // Configuração do listener para o botão de login
        buttonLogin.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // Verifica se os campos não estão vazios
            if (username.isEmpty() || password.isEmpty()) {
                showToast("Preencha todos os campos.");
                return;
            }

            // Verifica se os dados de login são válidos usando o CadastroVO
            if (CadastroVO.getInstance().verificarCredenciais(username, password)) {
                // Autenticação bem-sucedida
                showToast("Login bem-sucedido!");

                // Navegar para a próxima atividade após o login
                Intent intent = new Intent(LoginActivity.this, UserPageActivity.class);
                startActivity(intent);
                finish(); // Encerra a LoginActivity para que o usuário não possa voltar usando o botão de voltar
            } else {
                // Se a autenticação falhar
                showToast("Falha no login. Verifique suas credenciais.");
            }
        });

        // Configuração do listener para o botão de registro
        buttonRegister.setOnClickListener(v -> {
            // Navegar para a RegisterActivity
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


    }

    // Função auxiliar para exibir mensagens curtas na tela (Toast)
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}