package uni.fmi.masters.project.insurenceproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import uni.fmi.masters.project.insurenceproject.entities.ContractEntity;

public interface ContractRepository extends JpaRepository<ContractEntity, Integer>{

	ContractEntity findContractByPinAndInsurenceNameAndDuration(long pin, String insurenceName, String duration);


}
