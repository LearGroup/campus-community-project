package bean;

public class collectionTag {

	
	private Integer id;
	private String collectionName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	@Override
	public String toString() {
		return "collectionTag [id=" + id + ", collectionName=" + collectionName + ", getId()=" + getId()
				+ ", getCollectionName()=" + getCollectionName() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
	
}
