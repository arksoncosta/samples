package com.arkson.samples.transitivedependency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class TransitiveDependencyProblemTest {

    @Test
    void testDependencies() {
        Map<String, Set<String>> dependencies = new HashMap<>();
        dependencies.put("core", new HashSet<>(List.of("apache-commons-collections", "apache-logging", "web")));
        dependencies.put("apache-commons-collections", new HashSet<>(List.of("commons-lib", "commons-stream")));
        dependencies.put("commons-lib", Collections.emptySet());
        dependencies.put("commons-stream", new HashSet<>(List.of("stream-utils")));
        dependencies.put("stream-utils", Collections.emptySet());
        dependencies.put("web", new HashSet<>(List.of("json")));
        dependencies.put("json", Collections.emptySet());
        dependencies.put("apache-logging", new HashSet<>(List.of("log4j")));
        dependencies.put("log4j", Collections.emptySet());

        var transitiveDependencyProblem = new TransitiveDependencyProblem();
        var stringSetMap = transitiveDependencyProblem.mapTransitiveDependencies(dependencies);

        Assertions.assertEquals(9, dependencies.size());

        // core assertions
        Set<String> core = dependencies.get("core");
        Assertions.assertTrue(core.contains("apache-commons-collections"));
        Assertions.assertTrue(core.contains("apache-logging"));
        Assertions.assertTrue(core.contains("web"));
        Assertions.assertTrue(core.contains("commons-lib"));
        Assertions.assertTrue(core.contains("commons-stream"));
        Assertions.assertTrue(core.contains("stream-utils"));

        // apache-commons-collections assertions
        Set<String> commonsCollections = dependencies.get("apache-commons-collections");
        Assertions.assertTrue(commonsCollections.contains("commons-lib"));
        Assertions.assertTrue(commonsCollections.contains("commons-stream"));
        Assertions.assertTrue(commonsCollections.contains("stream-utils"));
    }

}