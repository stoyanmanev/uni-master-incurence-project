package uni.fmi.masters.project.insurenceproject;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uni.fmi.masters.project.insurenceproject.entities.RoleEntity;
import uni.fmi.masters.project.insurenceproject.entities.UserEntity;
import uni.fmi.masters.project.insurenceproject.repositories.UserRepository;

@Service
public class ApplicationUserDetailService implements UserDetailsService{

	private UserRepository userRepository;
	
	public ApplicationUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity user = userRepository.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException(username + " was not found....");
		}
		
		Set<RoleEntity> roles = user.getRoles();
		
		return new UserPrincipal(user, roles);
	}

}
