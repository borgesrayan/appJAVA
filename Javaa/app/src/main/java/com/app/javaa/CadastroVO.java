package com.app.javaa;

import java.util.ArrayList;
import java.util.List;

public class CadastroVO {

    private static CadastroVO instance;
    private List<CadastroVO> listaPessoa;

    // Campos adicionados
    private String email;
    private String password;
    private String fullName;


    private CadastroVO() {
        listaPessoa = new ArrayList<>();
    }

    public static CadastroVO getInstance() {
        if (instance == null)
            instance = new CadastroVO();

        return instance;
    }

    public void salvar(String email, String password, String fullName) {

        // Verifica se o e-mail já está cadastrado
        if (verificarEmailCadastrado(email)) {
            System.out.println("Este e-mail já está cadastrado.");
            return;
        }

        // Se o e-mail não existe na lista, cria um novo usuário e o adiciona à lista
        CadastroVO novoUsuario = new CadastroVO();
        novoUsuario.setEmail(email);
        novoUsuario.setPassword(password);
        novoUsuario.setFullName(fullName);
        listaPessoa.add(novoUsuario);
    }

    public boolean verificarEmailCadastrado(String email) {
        for (CadastroVO usuario : listaPessoa) {
            if (usuario.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public List<CadastroVO> getListaPessoa() {
        return listaPessoa;
    }

    public boolean verificarCredenciais(String email, String password) {
        for (CadastroVO usuario : listaPessoa) {
            if (usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public CadastroVO getUser(String email) {
        for (CadastroVO usuario : listaPessoa) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


}