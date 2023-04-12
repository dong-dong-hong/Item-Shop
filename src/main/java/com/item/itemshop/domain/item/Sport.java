package com.item.itemshop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Sport")
@Getter @Setter
public class Sport extends Item{

    private String sneakers; // 운동화
    private String shirt; // 운동 셔츠
    private String pants; // 운동 바지
}
