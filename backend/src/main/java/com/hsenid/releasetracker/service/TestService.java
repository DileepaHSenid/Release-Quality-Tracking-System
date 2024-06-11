package com.hsenid.releasetracker.service;

import com.hsenid.releasetracker.exception.TestNotFoundException;
import com.hsenid.releasetracker.exception.ReleaseNotFoundException;
import com.hsenid.releasetracker.exception.UserNotFoundException;
import com.hsenid.releasetracker.model.Test;
import com.hsenid.releasetracker.model.Release;
import com.hsenid.releasetracker.model.User;
import com.hsenid.releasetracker.repository.TestRepository;
import com.hsenid.releasetracker.repository.ReleaseRepository;
import com.hsenid.releasetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private UserRepository userRepository;

    // Fetch all tests
    public List<Test> fetchAllTests() {
        List<Test> tests = testRepository.findAll();
        if (tests.isEmpty()) {
            throw new RuntimeException("No tests found");
        }
        return tests;
    }

    // Add a new test
    public Test addTestDetails(Test test, String releaseId, String userId) {
        Release release = releaseRepository.findById(releaseId)
                .orElseThrow(() -> new ReleaseNotFoundException("There is no release with id: " + releaseId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("There is no user with id: " + userId));

        test.setRelease(release);
        test.setTestedBy(user);
        return testRepository.save(test);
    }

    // Fetch a test by its ID
    public Test fetchTestById(String id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new TestNotFoundException("There is no test with id: " + id));
    }

    // Delete a test by its ID
    public void deleteById(String id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new TestNotFoundException("There is no test with id: " + id));
        testRepository.deleteById(id);
    }
}
