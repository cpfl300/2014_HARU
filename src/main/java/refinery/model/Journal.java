package refinery.model;

public class Journal {
	
	private int id;
	private String name;
	private String section;
	
	public Journal() {
	}

	public Journal(String name) {
		this(0, name, null);
	}
	
	public Journal(int id) {
		this(id, null, null);
	}

	public Journal(String name, String section) {
		this(0, name, section);
	}
	
	public Journal(int id, String name, String section) {
		this.id = id;
		this.name = name;
		this.section = section;
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

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Journal other = (Journal) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Journal [id=" + id + ", name=" + name + ", section=" + section + "]";
	}
}
