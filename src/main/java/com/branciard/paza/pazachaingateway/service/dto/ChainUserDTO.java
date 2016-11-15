package com.branciard.paza.pazachaingateway.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the ChainUser entity.
 */
public class ChainUserDTO implements Serializable {

    private Long id;

    private Long uAaUserId;

    private String eCert;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getuAaUserId() {
        return uAaUserId;
    }

    public void setuAaUserId(Long uAaUserId) {
        this.uAaUserId = uAaUserId;
    }
    public String geteCert() {
        return eCert;
    }

    public void seteCert(String eCert) {
        this.eCert = eCert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChainUserDTO chainUserDTO = (ChainUserDTO) o;

        if ( ! Objects.equals(id, chainUserDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChainUserDTO{" +
            "id=" + id +
            ", uAaUserId='" + uAaUserId + "'" +
            ", eCert='" + eCert + "'" +
            '}';
    }
}
