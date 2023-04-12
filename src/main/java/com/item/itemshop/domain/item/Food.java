package com.item.itemshop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Food")
@Getter @Setter
public class Food extends Item{

    private String cook; // 요리사
    private String ingredient; // 재료
}
