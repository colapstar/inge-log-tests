package fr.isima.master1.genielog.service;

import fr.isima.master1.genielog.domain.Feature;
import fr.isima.master1.genielog.model.FeatureEntity;
import fr.isima.master1.genielog.model.FeatureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeatureServiceTest {

    @Mock
    private FeatureRepository repository;

    @InjectMocks
    private FeatureService service;

    private RoleExpressionParser parser = new InfixParser();

    @Test
    void cannotGetFeatureWithoutName() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getFeature(null) );
        assertThrows(IllegalArgumentException.class,
                () -> service.getFeature("") );
        assertThrows(IllegalArgumentException.class,
                () -> service.getFeature("   ") );
        Mockito.verify(repository, Mockito.times(0))
                .findById(Mockito.any());
    }

    @Test
    void cannotSaveNullFeature() {
        assertThrows(IllegalArgumentException.class,
                () -> service.saveFeature(null) );
    }

    @Test
    void cannotDeleteNullFeature() {
        assertThrows(IllegalArgumentException.class,
                () -> service.deleteFeature(null) );
        verify(repository, times(0)).delete(any());
        verify(repository, times(0)).deleteById(any());
    }

    @Test
    void cannotFindFeaturesWithLessThan3Characters() {
        assertThrows(IllegalArgumentException.class,
                () -> service.findFeatures(null) );
        assertThrows(IllegalArgumentException.class,
                () -> service.findFeatures("") );
        assertThrows(IllegalArgumentException.class,
                () -> service.findFeatures("a") );
        assertThrows(IllegalArgumentException.class,
                () -> service.findFeatures("ab") );
        service.findFeatures("abc");
        service.findFeatures("abcd");
        service.findFeatures("   ");
    }

    @Test
    void mustRetrieveExistingFeatureFromRepository() {

        var mockFeatureEntity = newEntity("test","(dev & admin)");
        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(Optional.of(mockFeatureEntity));

        var feature = service.getFeature("toto");

        assertThat(feature).isNotNull();
        assertThat(feature.name).isEqualTo("test");
        assertThat(feature.expression).isNotNull();
        assertThat(feature.expression.stringify()).isEqualTo("(dev & admin)");
        Mockito.verify(repository, Mockito.times(1))
                .findById(Mockito.eq("toto"));
    }

    @Test
    void mustReturnNullForNonExistingFeatureInRepository() {

        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(Optional.empty());

        var feature = service.getFeature("toto");

        assertThat(feature).isNull();
    }


    @Test
    void mustReturnAllFeaturesFromRepository() {
        var mockedEntities = Arrays.asList(
            newEntity("feature1", "dev"),
                newEntity("feature2", "(dev | admin)"),
                newEntity("feature3", "(dev & admin)")
        );
        var expectedFeatures = mockedEntities.stream()
                .map(entity -> new Feature(entity.name, parser.parse(entity.expression)))
                        .collect(Collectors.toList());

        when(repository.findAll()).thenReturn(mockedEntities);

        var features = service.getAllFeatures();

        assertThat(features).isNotNull();
        assertThat(features)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedFeatures);
        verify(repository).findAll();
    }




    @Test
    void mustFindFeaturesFromRepository() {
        var mockedEntities = Arrays.asList(
                newEntity("feature1", "dev"),
                newEntity("feature2", "(dev | admin)"),
                newEntity("feature3", "(dev & admin)")
        );
        var expectedFeatures = mockedEntities.stream()
                .map(entity -> new Feature(entity.name, parser.parse(entity.expression)))
                .collect(Collectors.toList());

        when(repository.findAll()).thenReturn(mockedEntities);

        var features = service.findFeatures("toto");

        assertThat(features).isNotNull();
        assertThat(features)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedFeatures);
        verify(repository).findByNameIgnoringCaseContaining("toto");
    }


    @Test
    void mustDeleteFromRepository() {
        service.deleteFeature(new Feature("test"));
        verify(repository).deleteById(eq("test"));
    }












    private FeatureEntity newEntity(String name, String expression) {
        var entity = new FeatureEntity();
        entity.name = "test";
        entity.expression = "(dev & admin)";
        return entity;
    }


















}