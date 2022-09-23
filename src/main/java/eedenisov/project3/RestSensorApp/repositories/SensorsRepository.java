package eedenisov.project3.RestSensorApp.repositories;

import eedenisov.project3.RestSensorApp.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * @author eedenisov
 */
@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(String name);
}
