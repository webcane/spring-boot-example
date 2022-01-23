package cane.brothers.spring.svc;

import cane.brothers.spring.model.Characteristic;
import cane.brothers.spring.model.Item;
import cane.brothers.spring.repo.CharacteristicRepository;
import cane.brothers.spring.repo.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CharacteristicRepository characteristicRepository;

    @Override
    public void findItems() {
        LOGGER.info("Find items info");
        List<Item> items = itemRepository.findAllItems();
        for (Item item : items) {
            String message = item.getName();

            for (Characteristic characteristic : item.getCharacteristics()) {
                message += ": " + characteristic.getType() + " " + characteristic.getItem().getName();
            }
            LOGGER.info(message);
        }
    }

    @Override
    public void getCharacteristicsByItemName(String name) {
        LOGGER.info("Find characteristics by item name");
        List<Characteristic> chars = characteristicRepository.findCharacteristicsByItemName(name);
        for (Characteristic characteristic : chars) {
            LOGGER.info(characteristic.getType());
        }
    }


    @Override
    public void findCharacteristics() {
        LOGGER.info("Find characteristics info");
        List<Characteristic> chars = characteristicRepository.findAll();
        for (Characteristic characteristic : chars) {
            LOGGER.info(characteristic.getType());
        }
    }

}
