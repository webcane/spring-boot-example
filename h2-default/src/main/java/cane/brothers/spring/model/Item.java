package cane.brothers.spring.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Simple item class
 * 
 * @author Mikhail Niedre
 */
@Entity
@Table(name = "ITEMS")
public class Item {

	@Id
	@GeneratedValue
	private Long id;
	
	@Size(min = 2)
	@Basic(optional = false)
	private String name;
	
	private String description;

	/**
	 * Default constructor
	 */
	public Item() {
		super();
	}
	
	public Item(Long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Item(String name, String description) {
		this(null, name, description);
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

	public void setId(Long id) {
		this.id = id;
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
