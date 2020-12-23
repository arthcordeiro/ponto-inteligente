package com.estudo.pontointeligente.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class CadastroPJDTO {

    private Long id;
    @NotEmpty(message = "Nome não pode ser nulo")
    @Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres")
    private String nome;
    @NotEmpty(message = "E-mail não pode ser nulo")
    @Length(min = 5, max = 200, message = "E-mail deve conter entre 5 e 200 caracteres")
    @Email(message = "E-mail inválido")
    private String email;
    @NotEmpty(message = "A senha não pode ser nula")
    private String senha;
    @NotEmpty(message = "CPF não pode ser nulo")
    @CPF(message = "CPF inválido")
    private String cpf;
    @NotEmpty(message = "Razão Social não pode ser nula")
    @Length(min = 5, max = 200, message = "Razão social deve conter entre 5 e 200 caracteres")
    private String razaoSocial;
    @NotEmpty(message = "CNPJ não pode ser nulo")
    @CNPJ(message = "CNPJ inválido")
    private String cnpj;

    public CadastroPJDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
