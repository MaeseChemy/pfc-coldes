package es.uc3m.coldes.model;

public class Room {
	private int id;
	private String name;
	private String description;
	private String owner;
	
	public Room() {
	}

	public Room(int id, String name, String description, String owner) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
}
