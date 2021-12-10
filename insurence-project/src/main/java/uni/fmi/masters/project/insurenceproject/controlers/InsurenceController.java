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
import uni.fmi.masters.project.insurenceproject.entities.InsurenceEntity;
import uni.fmi.masters.project.insurenceproject.entities.UserEntity;
import uni.fmi.masters.project.insurenceproject.repositories.InsurenceRepository;

@RestController
public class InsurenceController {

	private InsurenceRepository insurenceRepository;
	private WebSecurityConfig webSecurityConfig;
	
	public InsurenceController(InsurenceRepository insurenceRepo, WebSecurityConfig webSecurityConfig) {
		insurenceRepository = insurenceRepo;
		this.webSecurityConfig = webSecurityConfig;
	}
	
	@PostMapping(path="/create-insurence")
	public ResponseEntity<String> registerInsurence(@RequestParam(value = "name") String name,
													@RequestParam(value = "type") String type,
													@RequestParam(value = "price") double price,
													@RequestParam(value = "image") String image,
													HttpSession session){
		
		UserEntity employee = (UserEntity) session.getAttribute("user");
		
		if(employee == null) {
			return new ResponseEntity<String>("session: employee missing", HttpStatus.NOT_FOUND);
		}
		
		InsurenceEntity insurence = insurenceRepository.findInsurenceByNameAndType(name, type);
		
		if(insurence != null) {
			return new ResponseEntity<String>("sql: insurence exist", HttpStatus.OK);
		}
		
		insurence = new InsurenceEntity(name, price, type, image, employee.getUsername());
		insurenceRepository.saveAndFlush(insurence);
		
		return new ResponseEntity<String>("success record", HttpStatus.OK);
	}
	
	@GetMapping(path="insurence-table/all")
	public List<InsurenceEntity> getAllInsurence(){
		return insurenceRepository.findAll();
	}
	
	@DeleteMapping(path="insurence-table/delete")
	public ResponseEntity<String> deleteInsurence( @RequestParam(value = "name") String name,
													@RequestParam(value = "type") String type,
													HttpSession session){
		
		UserEntity employee = (UserEntity) session.getAttribute("user");
		
		if(employee == null) {
			return new ResponseEntity<String>("session: employee missing", HttpStatus.OK);
		}
		
		InsurenceEntity insurence = insurenceRepository.findInsurenceByNameAndType(name, type);
		
		if(insurence == null) {
			return new ResponseEntity<String>("sql: insurence missing", HttpStatus.NOT_FOUND);
		}
		
		insurenceRepository.delete(insurence);
		return new ResponseEntity<String>("done", HttpStatus.OK);
	}
}
