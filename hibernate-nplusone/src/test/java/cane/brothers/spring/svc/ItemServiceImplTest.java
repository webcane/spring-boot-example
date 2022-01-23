package cane.brothers.spring.svc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemServiceImplTest {

    @Autowired
    ItemService itemService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findItems() {
        itemService.findItems();
    }

    @Test
    void getCharacteristicsByItemName() {
        itemService.getCharacteristicsByItemName("item 1");
    }

    @Test
    void findCharacteristics() {
        itemService.findCharacteristics();
    }
}