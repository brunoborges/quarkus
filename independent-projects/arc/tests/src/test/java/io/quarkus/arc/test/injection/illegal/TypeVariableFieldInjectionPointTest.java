package io.quarkus.arc.test.injection.illegal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.spi.DefinitionException;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.arc.test.ArcTestContainer;

public class TypeVariableFieldInjectionPointTest {

    @RegisterExtension
    public ArcTestContainer container = ArcTestContainer.builder().beanClasses(Head.class).shouldFail().build();;

    @Test
    public void testError() {
        Throwable failure = container.getFailure();
        assertNotNull(failure);
        assertTrue(failure instanceof DefinitionException);
        assertEquals(
                "Type variable is not a legal injection point type: io.quarkus.arc.test.injection.illegal.TypeVariableFieldInjectionPointTest$Head#it",
                failure.getMessage());
    }

    @Dependent
    static class Head<T> {

        @Inject
        T it;

    }

}
