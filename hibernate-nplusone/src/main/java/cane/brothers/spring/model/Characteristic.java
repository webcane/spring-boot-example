package cane.brothers.spring.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "CHARACTERISTICS")
@Table(name = "CHARACTERISTICS")
public class Characteristic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;


    public Characteristic() {
    }

    public Characteristic(String type, Item item) {
        this.type = type;
        this.item = item;
    }

    public String getType() {
        return type;
    }

    public Item getItem() {
        return item;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Characteristic{" +
                "type='" + type + '\'' +
                ", item=" + item +
                '}';
    }
}
