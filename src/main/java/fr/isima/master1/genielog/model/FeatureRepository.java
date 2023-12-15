package fr.isima.master1.genielog.model;

import org.springframework.data.repository.CrudRepository;

public interface FeatureRepository extends CrudRepository<FeatureEntity, String> {

    Iterable<FeatureEntity> findByNameIgnoringCaseContaining(String string);
}
