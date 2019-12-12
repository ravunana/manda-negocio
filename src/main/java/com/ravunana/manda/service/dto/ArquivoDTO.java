package com.ravunana.manda.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.ravunana.manda.domain.enumeration.EntidadeSistema;

/**
 * A DTO for the {@link com.ravunana.manda.domain.Arquivo} entity.
 */
public class ArquivoDTO implements Serializable {

    private Long id;

    private String descricao;

    private EntidadeSistema entidade;

    @Lob
    private byte[] anexo;

    private String anexoContentType;
    private String codigoEntidade;

    private ZonedDateTime data;


    private Long utilizadorId;

    private String utilizadorLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public EntidadeSistema getEntidade() {
        return entidade;
    }

    public void setEntidade(EntidadeSistema entidade) {
        this.entidade = entidade;
    }

    public byte[] getAnexo() {
        return anexo;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public String getAnexoContentType() {
        return anexoContentType;
    }

    public void setAnexoContentType(String anexoContentType) {
        this.anexoContentType = anexoContentType;
    }

    public String getCodigoEntidade() {
        return codigoEntidade;
    }

    public void setCodigoEntidade(String codigoEntidade) {
        this.codigoEntidade = codigoEntidade;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Long getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(Long userId) {
        this.utilizadorId = userId;
    }

    public String getUtilizadorLogin() {
        return utilizadorLogin;
    }

    public void setUtilizadorLogin(String userLogin) {
        this.utilizadorLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArquivoDTO arquivoDTO = (ArquivoDTO) o;
        if (arquivoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), arquivoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArquivoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", entidade='" + getEntidade() + "'" +
            ", anexo='" + getAnexo() + "'" +
            ", codigoEntidade='" + getCodigoEntidade() + "'" +
            ", data='" + getData() + "'" +
            ", utilizador=" + getUtilizadorId() +
            ", utilizador='" + getUtilizadorLogin() + "'" +
            "}";
    }
}
