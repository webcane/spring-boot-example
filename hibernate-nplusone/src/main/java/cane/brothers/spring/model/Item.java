package cane.brothers.spring.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple item class
 * 
 * @author Mikhail Niedre
 */
@Entity(name = "ITEMS")
@Table(name = "ITEMS")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 2)
	@Basic(optional = false)
	private String name;

	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "item", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private List<Characteristic> characteristics = new ArrayList<>();

	/**
	 * Default constructor
	 */
	public Item() {
		super();
	}

	public Item(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<Characteristic> getCharacteristics() {
		return characteristics;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", descr=" + description + "]";
	}

}
