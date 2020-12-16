package com.estudo.pontointeligente.dto;

import com.estudo.pontointeligente.entities.Empresa;
import com.estudo.pontointeligente.entities.Lancamento;
import com.estudo.pontointeligente.enums.PerfilEnum;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FuncionarioDTO {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private BigDecimal valorHora;
    private Float qtdHoraTrabalhoDia;
    private Float qtdHoraAlmoco;
    private PerfilEnum perfil;
    private String senha;
    private Date dataCriacao;
    private Date dataAtualizacao;
    private Empresa empresa;
    private List<Lancamento> lancamentos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    public Float getQtdHoraTrabalhoDia() {
        return qtdHoraTrabalhoDia;
    }

    public void setQtdHoraTrabalhoDia(Float qtdHoraTrabalhoDia) {
        this.qtdHoraTrabalhoDia = qtdHoraTrabalhoDia;
    }

    public Float getQtdHoraAlmoco() {
        return qtdHoraAlmoco;
    }

    public void setQtdHoraAlmoco(Float qtdHoraAlmoco) {
        this.qtdHoraAlmoco = qtdHoraAlmoco;
    }

    public PerfilEnum getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilEnum perfil) {
        this.perfil = perfil;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }
}
