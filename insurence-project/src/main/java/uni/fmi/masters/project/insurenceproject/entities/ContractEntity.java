package uni.fmi.masters.project.insurenceproject.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="contract")
public class ContractEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="client_name", length = 255, nullable = false)
	private String clientName;
	
	@Column(name="pin", length = 255, nullable = false)
	private long pin;
	
	@Column(name="insurence_name", length = 255, nullable = false)
	private String insurenceName;
	
	@Column(name="price", length = 32, nullable = false)
	private double price;
	
	@Column(name="duration", length = 32, nullable = false)
	private String duration;
	
	@Column(name="details", length = 255)
	private String details;
	
	@Column(name="sing_up_date", length = 255, nullable = false)
	private String singUpDate;
	
	@Column(name="employee", length = 255, nullable = false)
	private String employee;
	
	public ContractEntity() {}
	
	public ContractEntity(String name, long pin, String insurenceName, 
			double price, String duration, String details, String singUpDate, String employee) {
		this.clientName = name;
		this.pin = pin;
		this.insurenceName = insurenceName;
		this.price = price;
		this.duration = duration;
		this.details = details;
		this.singUpDate = singUpDate;
		this.employee = employee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public long getPin() {
		return pin;
	}

	public void setPin(long pin) {
		this.pin = pin;
	}

	public String getInsuranceName() {
		return insurenceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insurenceName = insurenceName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getSingUpDate() {
		return singUpDate;
	}

	public void setSingUpDate(String singUpDate) {
		this.singUpDate = singUpDate;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}
	
	
}
