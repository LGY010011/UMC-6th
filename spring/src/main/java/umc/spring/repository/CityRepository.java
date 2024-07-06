package umc.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.City;

public interface CityRepository extends JpaRepository<City, Long> {
}
