package com.ravunana.manda.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RetencaoFonte.
 */
@Entity
@Table(name = "fnc_retencao_fonte")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RetencaoFonte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "motivo_retencao")
    private String motivoRetencao;

    @Column(name = "valor")
    private String valor;

    @Column(name = "porcentagem")
    private Boolean porcentagem;

    @ManyToOne
    @JsonIgnoreProperties("retencaoFontes")
    private DetalheLancamento detalhe;

    @ManyToOne
    @JsonIgnoreProperties("retencaoFontes")
    private Imposto imposto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivoRetencao() {
        return motivoRetencao;
    }

    public RetencaoFonte motivoRetencao(String motivoRetencao) {
        this.motivoRetencao = motivoRetencao;
        return this;
    }

    public void setMotivoRetencao(String motivoRetencao) {
        this.motivoRetencao = motivoRetencao;
    }

    public String getValor() {
        return valor;
    }

    public RetencaoFonte valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Boolean isPorcentagem() {
        return porcentagem;
    }

    public RetencaoFonte porcentagem(Boolean porcentagem) {
        this.porcentagem = porcentagem;
        return this;
    }

    public void setPorcentagem(Boolean porcentagem) {
        this.porcentagem = porcentagem;
    }

    public DetalheLancamento getDetalhe() {
        return detalhe;
    }

    public RetencaoFonte detalhe(DetalheLancamento detalheLancamento) {
        this.detalhe = detalheLancamento;
        return this;
    }

    public void setDetalhe(DetalheLancamento detalheLancamento) {
        this.detalhe = detalheLancamento;
    }

    public Imposto getImposto() {
        return imposto;
    }

    public RetencaoFonte imposto(Imposto imposto) {
        this.imposto = imposto;
        return this;
    }

    public void setImposto(Imposto imposto) {
        this.imposto = imposto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RetencaoFonte)) {
            return false;
        }
        return id != null && id.equals(((RetencaoFonte) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RetencaoFonte{" +
            "id=" + getId() +
            ", motivoRetencao='" + getMotivoRetencao() + "'" +
            ", valor='" + getValor() + "'" +
            ", porcentagem='" + isPorcentagem() + "'" +
            "}";
    }
}
