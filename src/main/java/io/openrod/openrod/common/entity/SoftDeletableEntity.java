package io.openrod.openrod.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SoftDeletableEntity extends BaseEntity {

    @Column
    private boolean deleted;

    public void delete() {
        this.deleted = true;
    }
}
