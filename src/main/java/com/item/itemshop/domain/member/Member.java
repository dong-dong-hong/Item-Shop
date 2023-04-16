package com.item.itemshop.domain.member;

import com.item.itemshop.domain.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Setter @Getter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; // pk
    private String name;
    private String idname;
    private String pw;
    private String address;
    private String addressDetail;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();



}
