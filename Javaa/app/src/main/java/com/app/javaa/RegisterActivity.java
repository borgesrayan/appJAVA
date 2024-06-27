package com.app.javaa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Referências aos elementos da interface do usuário

        EditText editTextFullName = findViewById(R.id.editTextFullName);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPasswordRegister);
        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        Button buttonAlreadyHaveAccount = findViewById(R.id.buttonAlreadyHaveAccount);

        // Limpa o campo de nome para evitar preenchimento automático
        editTextFullName.setText(null);

        // Configuração do listener para o botão de registro
        buttonRegister.setOnClickListener(v -> {
            String fullName = editTextFullName.getText().toString();
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();

            // Verifica se os campos não estão vazios
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showToast("Preencha todos os campos.");
                return;
            }

            // Verifica se as senhas coincidem
            if (!password.equals(confirmPassword)) {
                showToast("As senhas não coincidem.");
                return;
            }

            // Verifica se o e-mail já está cadastrado
            if (CadastroVO.getInstance().verificarEmailCadastrado(email)) {
                showToast("Este e-mail já está cadastrado.");
                return;
            }

            // Salva as credenciais usando a classe CadastroVO
            CadastroVO.getInstance().salvar(email, password, fullName);

            // Exibe a mensagem de registro bem-sucedido
            showToast("Registro bem-sucedido!");

            // Inicia a LoginActivity após o registro bem-sucedido
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Encerra a RegisterActivity para que o usuário não possa voltar usando o botão de voltar
        });

        // Configuração do listener para o botão "Já tem uma conta"
        buttonAlreadyHaveAccount.setOnClickListener(v -> {
            // Navegar para a LoginActivity ou para onde desejar
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Encerra a RegisterActivity para que o usuário não possa voltar usando o botão de voltar
        });
    }

    // Função auxiliar para exibir mensagens curtas na tela (Toast)
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}