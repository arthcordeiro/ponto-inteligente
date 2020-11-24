package com.estudo.pontointeligente.entities;

import com.estudo.pontointeligente.enums.PerfilEnum;

import javax.persistence.*;
import javax.swing.text.html.Option;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "cpf", nullable = false)
    private String cpf;
    @Column(name = "valor_hora", nullable = false)
    private BigDecimal valorHora;
    @Column(name = "qtd_hora_trabalho_dia", nullable = false)
    private Float qtdHoraTrabalhoDia;
    @Column(name = "qtd_hora_almoco", nullable = false)
    private Float qtdHoraAlmoco;
    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private PerfilEnum perfil;
    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;
    @Column(name = "data_atualizacao", nullable = false)
    private Date dataAtualizacao;
    @ManyToOne(fetch = FetchType.EAGER)
    private Empresa empresa;
    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lancamento> lancamentos;

    public Funcionario() {}

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

    @PreUpdate
    public void preUpdate(){
        this.dataAtualizacao = new Date();
    }

    @PrePersist
    public void prePersist(){
        final Date atual = new Date();
        this.dataCriacao = atual;
        this.dataAtualizacao = atual;
    }

    @Transient
    public Optional<BigDecimal> getValorHoraOpt() {
        return Optional.ofNullable(valorHora);
    }

    @Transient
    public Optional<BigDecimal> getQtdHoraTrabalhoDiaOpt() {
        return Optional.ofNullable(qtdHoraTrabalhoDia)
    }
}
