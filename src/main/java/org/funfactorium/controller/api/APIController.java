package org.funfactorium.controller.api;

import org.funfactorium.Utils;
import org.funfactorium.funfacts.FunFactNotFoundException;
import org.funfactorium.funfacts.FunFactService;
import org.funfactorium.funfacts.topics.TopicNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class APIController {

    @Autowired
    FunFactService funFactService;

    @PostMapping(value = "/api/filter", consumes = "application/json")
    public ResponseEntity renderFilteredIndex(@RequestBody String id) {
        long topicId = Long.parseLong(id.split("=")[1]);
        List<Map<String, String>> params = funFactService.searchByTopic(topicId);
        return ResponseEntity.ok(params);
    }

    @GetMapping(value = "/api/funfact")
    public ResponseEntity getRandomFunFact() {
        long randomId = funFactService.getRandomFunFactId();
        return ResponseEntity.ok(funFactService.getFunFact(randomId));
    }

    @GetMapping(value = "/api/funfact/{factId}")
    public ResponseEntity getFunFact(@PathVariable("factId") long id) {
        try {
            return ResponseEntity.ok(funFactService.getFunFact(id));
        } catch (FunFactNotFoundException e) {
            return new ResponseEntity(Utils.buildApiErrorMessage(e), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/api/funfact/topic/{topicName}")
    public ResponseEntity getFunFactByTopic(@PathVariable("topicName") String topicName) {
        String properTopicName = StringUtils.capitalize(topicName.toLowerCase());
        try {
            return ResponseEntity.ok(funFactService.getFunFactByTopicName(properTopicName));
        } catch (TopicNotFoundException e) {
            return new ResponseEntity(Utils.buildApiErrorMessage(e), HttpStatus.NOT_FOUND);
        }
    }
}
