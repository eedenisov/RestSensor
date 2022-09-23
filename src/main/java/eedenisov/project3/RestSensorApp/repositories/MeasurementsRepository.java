package eedenisov.project3.RestSensorApp.repositories;

import eedenisov.project3.RestSensorApp.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author eedenisov
 */
@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
}
