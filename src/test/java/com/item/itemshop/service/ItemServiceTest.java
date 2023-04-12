package com.item.itemshop.service;

import com.item.itemshop.domain.item.Book;
import com.item.itemshop.domain.item.Clean;
import com.item.itemshop.domain.item.Item;
import com.item.itemshop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    void saveItem() {
        // given
        Item item = new Book();
        item.setName("Spring JPA");
        item.setPrice(10000);
        item.setStockQuantity(10);
        // when
        itemService.saveItem(item);
        // then
        assertEquals(item, itemRepository.findOne(item.getId()));
        // 새로운 상품을 저장하고, 저장된 상품을 검색하여 결과가 일치하는 지 확인(상품 저장)
    }

    @Test
    void findItems() {
        // given
        Item item1 = new Book();
        item1.setName("Spring JPA");
        item1.setPrice(10000);
        item1.setStockQuantity(10);

        Item item2 = new Clean();
        item2.setName("The Best Clean");
        item2.setPrice(20000);
        item2.setStockQuantity(20);

        itemService.saveItem(item1);
        itemService.saveItem(item2);

        // when
        List<Item> items = itemService.findItems();
        // then
        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
        // 두 개의 상품을 저장한 후, 모든 상품을 검색하여 결과가 일치하는지 확인합니다.(모든 상품 검색)
    }

    @Test
    void findOne() {
        // given
        Item item = new Book();
        item.setName("Spring JPA");
        item.setPrice(10000);
        item.setStockQuantity(10);

        itemService.saveItem(item);
        // when
        Item foundItem = itemService.findOne(item.getId());
        // then
        assertEquals(item, foundItem);
        // 새로운 상품을 저장한 후, 저장된 상품을 검색하여 결과가 일치하는지 확인합니다.(상품 검색)
    }
}