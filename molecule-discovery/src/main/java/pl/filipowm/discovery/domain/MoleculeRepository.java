package pl.filipowm.discovery.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MoleculeRepository extends CrudRepository<Molecule, Long> {

    @Query("SELECT m FROM Molecule m LEFT JOIN FETCH m.ingredients WHERE m.id = ?1")
    Optional<Molecule> findOneById(Long id);

    Optional<Molecule> findOneByDiscoveryId(String discoveryId);
}