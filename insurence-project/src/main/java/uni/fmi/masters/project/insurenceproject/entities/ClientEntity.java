package uni.fmi.masters.project.insurenceproject.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class ClientEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="pin", length = 255, nullable = false, unique = true)
	private long pin;
	
	@Column(name="name", length = 255, nullable = false)
	private String name;
	
	@Column(name="phone", length = 255, nullable = false)
	private long phone;

	@Column(name="employee", length = 255, nullable = false)
	private String employee;
	
	public ClientEntity() { }
	
	public ClientEntity(long pin, String name, long phone, String employee) {
		this.pin = pin;
		this.name = name;
		this.phone = phone;
		this.employee = employee;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getPin() {
		return pin;
	}

	public void setPin(long pin) {
		this.pin = pin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}
	
	
}
