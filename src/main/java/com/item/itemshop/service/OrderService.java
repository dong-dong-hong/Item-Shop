package com.item.itemshop.service;

import com.item.itemshop.domain.delivery.Delivery;
import com.item.itemshop.domain.delivery.DeliveryStatus;
import com.item.itemshop.domain.item.Item;
import com.item.itemshop.domain.member.Member;
import com.item.itemshop.domain.order.Order;
import com.item.itemshop.domain.order.OrderItem;
import com.item.itemshop.exception.NotEnoughStockException;
import com.item.itemshop.repository.ItemRepository;
import com.item.itemshop.repository.MemberRepository;
import com.item.itemshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository  itemRepository;

    @Autowired
    public OrderService(MemberRepository memberRepository, OrderRepository orderRepository, ItemRepository itemRepository) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 재고 수량 확인
        if (item.getStockQuantity() < count) {
            throw new NotEnoughStockException("재고 수량 부족");
        }

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member,delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 취소
        order.cancel();
    }

    // 주문 검색
//    public List<Order> findOrders(Ordersearch ordersearch) {
//        return orderRepository.findAll(ordersearch);
//    }
}
