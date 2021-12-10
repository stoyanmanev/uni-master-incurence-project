package uni.fmi.masters.project.insurenceproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import uni.fmi.masters.project.insurenceproject.entities.InsurenceEntity;


public interface InsurenceRepository extends JpaRepository<InsurenceEntity, Integer>{

	InsurenceEntity findInsurenceByNameAndType(String name, String type);

}
