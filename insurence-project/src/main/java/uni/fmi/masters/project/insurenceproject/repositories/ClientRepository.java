package uni.fmi.masters.project.insurenceproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uni.fmi.masters.project.insurenceproject.entities.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {

	ClientEntity findByPin(long pin);
	
	
}
