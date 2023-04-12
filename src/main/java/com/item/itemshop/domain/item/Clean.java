package com.item.itemshop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Clean")
@Setter @Getter
public class Clean extends Item{

    private String vacuum; // 청소기
    private String rag; // 청소도구

}
