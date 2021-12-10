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
import uni.fmi.masters.project.insurenceproject.entities.ClientEntity;
import uni.fmi.masters.project.insurenceproject.entities.UserEntity;
import uni.fmi.masters.project.insurenceproject.repositories.ClientRepository;

@RestController
public class ClientController {

	private ClientRepository clientRepository;
	private WebSecurityConfig webSecurityConfig;
	
	public ClientController(ClientRepository clientRepo,WebSecurityConfig webSecurityConfig) {
		clientRepository = clientRepo;
		this.webSecurityConfig = webSecurityConfig;
	}
	
	@PostMapping(path="/create-client")
	public ResponseEntity<String> registerClient( @RequestParam( value = "name") String name,
								@RequestParam( value = "pin") long pin,
								@RequestParam( value = "phone") long phone,
								HttpSession session) {
		
		UserEntity employee = (UserEntity) session.getAttribute("user");
		
		if(employee == null) {
			return new ResponseEntity<String>("session: employee missing", HttpStatus.OK);
		}
		
		ClientEntity client = clientRepository.findByPin(pin);
		
		if(client != null) {
			return new ResponseEntity<String>("sql: pin exist", HttpStatus.OK);
		}
		
		client = new ClientEntity(pin, name, phone, employee.getUsername());
		clientRepository.saveAndFlush(client);
		
		return new ResponseEntity<String>("done", HttpStatus.OK);
	}
	
	@GetMapping(path="customer-table/all")
	public List<ClientEntity> getAllClients(){
		return clientRepository.findAll();
	}
	
	@DeleteMapping(path="customer-table/delete")
	public ResponseEntity<String> deleteClient( @RequestParam(value = "pin") long pin, HttpSession session){
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		
		if(user == null) {
			return new ResponseEntity<String>("session: employee missing", HttpStatus.NOT_FOUND);
		}
		
		ClientEntity client = clientRepository.findByPin(pin);
		
		if(client == null) {
			return new ResponseEntity<String>("sql: client missing", HttpStatus.NOT_FOUND);
		}
		
		clientRepository.delete(client);
		return new ResponseEntity<String>("done", HttpStatus.OK);
		
	}
}
