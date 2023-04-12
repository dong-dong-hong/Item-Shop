package com.item.itemshop.domain.delivery;

import com.item.itemshop.domain.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery" , fetch = FetchType.LAZY)
    private Order order;

    public String Address;
    public String AddressDetail;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // 준비(READY), 배송(COMP)
}
