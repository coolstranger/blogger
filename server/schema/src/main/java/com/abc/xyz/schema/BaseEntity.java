package com.abc.xyz.schema;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by aaamir on 7/30/16.
 */
@MappedSuperclass
public class BaseEntity {

    /**
     * Primary key
     */
    protected String id;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "GuidGenerator")
    @GenericGenerator(name = "GuidGenerator", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
