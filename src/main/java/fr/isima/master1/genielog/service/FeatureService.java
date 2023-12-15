package fr.isima.master1.genielog.service;

import fr.isima.master1.genielog.domain.Feature;
import fr.isima.master1.genielog.model.FeatureRepository;
import org.springframework.util.Assert;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FeatureService {

    private RoleExpressionParser parser = new InfixParser();

    private final FeatureRepository repository;

    public FeatureService(FeatureRepository repository) {
        this.repository = repository;
    }

    public Feature getFeature(String name) {
        Assert.hasText(name, "name cannot be null/empty/blank");
        var optionalEntity = repository.findById(name);
        if ( optionalEntity.isEmpty() ) {
            return null;
        }
        var entity = optionalEntity.get();
        var feature = new Feature(entity.name, parser.parse(entity.expression));
        return feature;
    }

    public Feature saveFeature(Feature feature) {
        Assert.notNull(feature, "feature cannot be null");
        return null;
    }

    public void deleteFeature(Feature feature) {
        Assert.notNull(feature, "feature cannot be null");
        repository.deleteById(feature.name);
    }

    public Iterable<Feature> getAllFeatures() {
        var entities = repository.findAll();

        var features = StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> new Feature(entity.name, parser.parse(entity.expression)))
                .collect(Collectors.toList());

        return features;
    }

    public Iterable<Feature> findFeatures(String template) {
        Assert.hasLength(template, "template cannot be null/empty");
        Assert.isTrue(template.length() >= 3, "template must be at 3 characters");
        var entities = repository.findByNameIgnoringCaseContaining(template);
        var features = StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> new Feature(entity.name, parser.parse(entity.expression)))
                .collect(Collectors.toList());

        return features;
    }
}
