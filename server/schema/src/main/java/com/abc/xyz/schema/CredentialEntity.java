package com.abc.xyz.schema;

import javax.persistence.*;

@Entity
@Table(name="CREDENTIAL")
public class CredentialEntity extends BaseEntity {

    private String hash;
    private String salt;
    private String refUser;
    private UserEntity user;

    @Basic
    @Column(name="hash")
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Basic
    @Column(name="salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @OneToOne
    @JoinColumn(name="refUser", referencedColumnName = "id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Basic
    @Column(name="refUser", insertable = false, updatable = false)
    public String getRefUser() {
        return refUser;
    }

    public void setRefUser(String refUser) {
        this.refUser = refUser;
    }
}
