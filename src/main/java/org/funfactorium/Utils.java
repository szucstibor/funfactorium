package org.funfactorium;

import org.funfactorium.funfacts.FunFactDto;
import org.funfactorium.funfacts.FunFactNotFoundException;
import org.funfactorium.funfacts.topics.TopicNotFoundException;
import org.funfactorium.funfacts.FunFact;
import org.funfactorium.funfacts.topics.Topic;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    public static List<Map<String, String>> buildFilteredPage(List<FunFact> funFacts) {
        List<Map<String, String>> model = new ArrayList<>();
        for (FunFact funFact : funFacts) {
            Map<String, String> currentFunFacts = new HashMap<>();
            currentFunFacts.put("title", funFact.getTitle());
            currentFunFacts.put("author", funFact.getAuthor().getUserName());
            currentFunFacts.put("description", funFact.getDescription());
            model.add(currentFunFacts);
        }
        return model;
    }

    public static Map<String, Object> buildSingleFactJson(FunFact randomFact) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", randomFact.getId());
        result.put("title", randomFact.getTitle());
        result.put("author", randomFact.getAuthor().getUserName());
        result.put("topics", randomFact.getTopic().stream().map(Topic::getName).collect(Collectors.toList()));
        result.put("description", randomFact.getDescription());
        result.put("rating", randomFact.getRating());
        result.put("status", "FOUND");
        return result;
    }

    public static Map<String, String> buildApiErrorMessage(RuntimeException e) {
        Map<String, String> result = new HashMap<>();
        if(e instanceof FunFactNotFoundException) {
            result.put("status", "NOT FOUND");
            result.put("description", "No entry with this id was found in the database!");
        } else if(e instanceof TopicNotFoundException) {
            result.put("status", "NOT FOUND");
            result.put("description", "No such topic was found in the database!");
        } else if(e instanceof NullPointerException) {
            result.put("status", "NOT FOUND");
            result.put("description", "Database empty!");
        }
        return result;
    }

    public static List<Map<String,Object>> buildFactListJson(List<FunFact> allFactsForTopic) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (FunFact funFact:allFactsForTopic) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("id", funFact.getId());
            entry.put("title", funFact.getTitle());
            entry.put("author", funFact.getAuthor().getUserName());
            entry.put("topics", funFact.getTopic().stream().map(Topic::getName).collect(Collectors.toList()));
            entry.put("description", funFact.getDescription());
            entry.put("rating", funFact.getRating());
            entry.put("status", "FOUND");
            resultList.add(entry);
        }
        return resultList;
    }

    public static boolean checkForEmptyFields(FunFactDto funFactDto) {
        return Stream.of(funFactDto.getTitle(),
                            funFactDto.getDescription(),
                                funFactDto.getTopics())
                                    .anyMatch(Objects::isNull);
    }
}
