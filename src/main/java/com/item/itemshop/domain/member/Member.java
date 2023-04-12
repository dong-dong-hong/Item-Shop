package com.item.itemshop.domain.member;

import com.item.itemshop.domain.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "members")
@Setter @Getter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id; // pk
    private String name;
    private String address;
    private String addressDetail;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
