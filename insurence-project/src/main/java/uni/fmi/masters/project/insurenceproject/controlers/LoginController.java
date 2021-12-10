package uni.fmi.masters.project.insurenceproject.controlers;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import uni.fmi.masters.project.insurenceproject.WebSecurityConfig;
import uni.fmi.masters.project.insurenceproject.entities.UserEntity;
import uni.fmi.masters.project.insurenceproject.repositories.UserRepository;

@RestController
public class LoginController {
	private UserRepository userRepository;
	private WebSecurityConfig webSecurityConfig;
	
	public LoginController(UserRepository userRepo, WebSecurityConfig webSecurityConfig) {
		userRepository = userRepo;
		this.webSecurityConfig = webSecurityConfig;
	}
	
	@PostMapping(path="/register")
	public String register( @RequestParam( value = "username") String username,
								@RequestParam( value="password") String password,
								@RequestParam( value="repeatPassword") String repeatPassword,
								HttpSession session) {
		
		if(password.equals(repeatPassword)) {
			
			UserEntity user = userRepository.findByUsername(username);
			
			if(user == null) {
				user = new UserEntity(username, password);
				userRepository.saveAndFlush(user);
							
				session.setAttribute("user", user);
				
				return "/pages/home.html";
			}
			return "sql: email exist";
		}
		return "/pages/error.html";
	}
	
	@PostMapping(path="/login")
	public String login( @RequestParam( value = "username") String username,
						@RequestParam( value = "password") String password,
						HttpSession session) throws UsernameNotFoundException, Exception {
		
		UserEntity user = userRepository.findUserByUsernameAndPassword(username, password);
		
		if(user != null) {
			session.setAttribute("user", user);
			
			UserDetails userDetails = webSecurityConfig.userDetailsServiceBean().loadUserByUsername(username);
			
			if(userDetails != null) {
				Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
				
				session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
				
				return "/pages/home.html";
			}
		}
		
		return "/pages/error.html";
	}
	
	@PostMapping(path = "/logout")
	public ResponseEntity<Boolean> logout(HttpSession session){
		UserEntity user = (UserEntity) session.getAttribute("user");
		
		if(user != null) {
			session.invalidate();
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		
		return new ResponseEntity<Boolean>(false, HttpStatus.REQUEST_TIMEOUT);
	}
	
	@GetMapping(path = "/loggedUserId")
	public ResponseEntity<Integer> loggedUserId(HttpSession session) {
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		
		if(user != null) {
			return new ResponseEntity<Integer>(user.getId(), HttpStatus.OK);
		}
		
		return new ResponseEntity<Integer>(-1, HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping(path = "/credentials")
	public ResponseEntity<String> credential(HttpSession session){
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		
		if(user.getRole() != null) {
			return new ResponseEntity<String>(user.getRole(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
	}
}
