package com.abc.xyz.schema;

import javax.persistence.*;


@Entity
@Table(name="SESSION")
public class SessionEntity extends BaseEntity {

    private String sessionId;
    private UserEntity user;
    private long expiryTime;
    private String refUser;

    @Basic
    @Column(name = "sessionId")
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @ManyToOne
    @JoinColumn(name = "refUser", referencedColumnName = "id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Basic
    @Column(name = "expiryTime")
    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
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
