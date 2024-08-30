package com.arkson.samples.transitivedependency;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TransitiveDependencyProblem {

    public Map<String, Set<String>> mapTransitiveDependencies(Map<String, Set<String>> transitiveDeps) {
        String firstElement = transitiveDeps.entrySet().stream().findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new IllegalArgumentException("Transitive deps is empty"));

        return mapDependencies(transitiveDeps, firstElement);
    }

    private Map<String, Set<String>> mapDependencies(Map<String, Set<String>> transitiveDeps, final String key) {
        var deps = transitiveDeps.get(key);

        if (!deps.isEmpty()) {
            deps.forEach(dep -> mapDependencies(transitiveDeps, dep));
            var transitive = deps
                    .stream()
                    .flatMap(d -> transitiveDeps.get(d).stream())
                    .collect(Collectors.toSet());
            deps.addAll(transitive);
        }

        return transitiveDeps;
    }

}
