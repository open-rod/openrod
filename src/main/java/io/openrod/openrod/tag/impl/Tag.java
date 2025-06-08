package io.openrod.openrod.tag.impl;

import io.openrod.openrod.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tags")
public class Tag extends BaseEntity {

    @Column()
    private String name;

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
