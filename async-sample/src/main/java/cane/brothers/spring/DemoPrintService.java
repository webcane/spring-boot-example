package cane.brothers.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by cane on 29.03.17.
 */
@Service
public class DemoPrintService implements PrintService {

    private static final Logger log =
            LoggerFactory.getLogger(DemoPrintService.class);

    @Override
    public void printStage(int stage) {
        log.info(String.format("stage #%d", stage));
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            log.info("problems with thread", e);
        }
    }
}
