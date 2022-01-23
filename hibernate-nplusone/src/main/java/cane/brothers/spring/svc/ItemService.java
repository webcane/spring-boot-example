package cane.brothers.spring.svc;

import org.springframework.stereotype.Service;

public interface ItemService {
    void getCharacteristicsByItemName(String name);

    void findCharacteristics();

    void findItems();
}
