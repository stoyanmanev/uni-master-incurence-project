package uni.fmi.masters.project.insurenceproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uni.fmi.masters.project.insurenceproject.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	UserEntity findByUsername(String username);
	
	UserEntity findUserByUsernameAndPassword(String username, String password);
}
