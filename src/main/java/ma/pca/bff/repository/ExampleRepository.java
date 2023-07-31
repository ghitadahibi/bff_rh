package ma.pca.bff.repository;

import ma.pca.bff.model.entities.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleRepository extends JpaRepository<Example, String> {
}
