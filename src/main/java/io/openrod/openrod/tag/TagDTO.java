package io.openrod.openrod.tag;

import io.openrod.openrod.common.dto.BaseDTO;

public class TagDTO extends BaseDTO {

    private String name;

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
