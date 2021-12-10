package uni.fmi.masters.project.insurenceproject.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="insurence")
public class InsurenceEntity implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name", length = 255, nullable = false)
	private String name;
	
	@Column(name="price", length = 32, nullable = false)
	private double price;
	
	@Column(name="type", length = 32, nullable = false)
	private String type;
	
	@Column(name="image", length = 255)
	private String image;
	
	@Column(name="employee", length = 255, nullable = false)
	private String employee;
	
	public InsurenceEntity() {}
	
	public InsurenceEntity(String name, double price, String type, String image, String employee) { 
		this.name = name;
		this.price = price;
		this.type= type;
		this.image = image;
		this.employee = employee;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}
	
}
