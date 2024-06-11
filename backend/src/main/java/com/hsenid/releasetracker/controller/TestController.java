package com.hsenid.releasetracker.controller;

import com.hsenid.releasetracker.model.Test;
import com.hsenid.releasetracker.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/add")
    public ResponseEntity<Test> addTest(@RequestBody Test test, @RequestParam String releaseId, @RequestParam String userId) {
        Test createdTest = testService.addTestDetails(test, releaseId, userId);
        return new ResponseEntity<>(createdTest, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Test>> getAllTests() {
        List<Test> tests = testService.fetchAllTests();
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable String id) {
        Test test = testService.fetchTestById(id);
        return ResponseEntity.ok(test);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable String id) {
        testService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
