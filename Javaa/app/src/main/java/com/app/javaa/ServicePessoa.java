package com.app.javaa;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class ServicePessoa implements Serializable {
    private static List<CadastroVO> listaPessoa;
    private static ServicePessoa servicePessoa = null;

    private ServicePessoa() {
        listaPessoa = new ArrayList<>();
    }

    public static ServicePessoa getInstance() {
        if (servicePessoa == null)
            servicePessoa = new ServicePessoa();

        return servicePessoa;
    }

    public void salvar(CadastroVO cadastroVO) {
        listaPessoa.add(cadastroVO);
    }

    public List<CadastroVO> getListaPessoa() {
        return listaPessoa;
    }

    // Método para verificar se as credenciais são válidas
    public boolean verificarUsuarioCadastrado(String email, String password) {
        for (CadastroVO usuario : listaPessoa) {
            if (usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
                return true; // Credenciais válidas
            }
        }
        return false; // Credenciais inválidas
    }
}