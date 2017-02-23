package com.abc.xyz.schema;

import javax.persistence.*;


@Entity
@Table(name="USER")
public class UserEntity extends BaseEntity{
    protected String firstName;
    protected String lastName;
    protected String email;
    private String loginUid;
    private long lastLoginTime;
    private CredentialEntity credential;

    @Basic
    @Column(name = "loginUid")
    public String getLoginUid() {
        return loginUid;
    }

    public void setLoginUid(String loginUid) {
        this.loginUid = loginUid;
    }

    @OneToOne(mappedBy = "user")
    public CredentialEntity getCredential() {
        return credential;
    }

    public void setCredential(CredentialEntity credential) {
        this.credential = credential;
    }

    @Basic
    @Column(name="lastLoginTime")
    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }


    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
