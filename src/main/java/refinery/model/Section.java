package refinery.model;

public class Section {
	
	private int id;
	private String major;
	private String minor;
	
	public Section() {
	}
	
	public Section(String minor) {
		this(0, null, minor);
	}

	public Section(int id) {
		this(id, null, null);
	}
	
	public Section(String major, String minor) {
		this(0, major, minor);
	}
	
	public Section (int id, String major, String minor) {
		this.id = id;
		this.major = major;
		this.minor = minor;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((minor == null) ? 0 : minor.hashCode());
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
		Section other = (Section) obj;
		if (minor == null) {
			if (other.minor != null)
				return false;
		} else if (!minor.equals(other.minor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Section [id=" + id + ", major=" + major + ", minor=" + minor + "]";
	}
	


}
