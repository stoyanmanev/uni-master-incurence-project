package uni.fmi.masters.project.insurenceproject.controlers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.masters.project.insurenceproject.WebSecurityConfig;
import uni.fmi.masters.project.insurenceproject.entities.ContractEntity;
import uni.fmi.masters.project.insurenceproject.entities.UserEntity;
import uni.fmi.masters.project.insurenceproject.repositories.ContractRepository;

@RestController
public class ContractController {

	private ContractRepository contractRepository;
	private WebSecurityConfig webSecurityConfig;
	
	public ContractController(ContractRepository contractRepo, WebSecurityConfig webSecurityConfig) {
		contractRepository = contractRepo;
		this.webSecurityConfig = webSecurityConfig;
	}
	
	
	@PostMapping(path="/create-contract")
	public ResponseEntity<String> registerContract(@RequestParam( value = "pin") long pin,
									@RequestParam(value = "cname") String clientName,
									@RequestParam(value = "iname") String insurenceName,
									@RequestParam(value = "price") double price,
									@RequestParam(value = "type") String duration,
									@RequestParam(value = "details") String details,
									@RequestParam(value = "singUpDate") String singUpDate,
									HttpSession session){
		
		UserEntity employee = (UserEntity) session.getAttribute("user");
		
		if(employee == null) {
			return new ResponseEntity<String>("session: employee missing", HttpStatus.NOT_FOUND);
		}
		
		ContractEntity contract = new ContractEntity(clientName, pin, insurenceName,
				price, duration, details, singUpDate, employee.getUsername());
		
		contractRepository.saveAndFlush(contract);
		
		return new ResponseEntity<String>("done", HttpStatus.OK);
	}
	
	@GetMapping(path="contract-table/all")
	public List<ContractEntity> getAllContract(){
		return contractRepository.findAll();
	}
	
	@DeleteMapping(path="contract-table/delete")
	public ResponseEntity<String> deleteContract( @RequestParam(value = "pin") long pin,
									@RequestParam(value = "insurenceName") String insurenceName,
									@RequestParam(value = "duration") String duration,
									HttpSession session){
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		
		if(user == null) {
			return new ResponseEntity<String>("session: employee missing", HttpStatus.NOT_FOUND);
		}
		
		ContractEntity contract = contractRepository.findContractByPinAndInsurenceNameAndDuration(pin, insurenceName, duration);
		// todo pin is unique
		if(contract == null) {
			return new ResponseEntity<String>("sql: contract missing", HttpStatus.NOT_FOUND);
		}
		
		contractRepository.delete(contract);
		return new ResponseEntity<String>("done", HttpStatus.OK);
	}
	
	
}
