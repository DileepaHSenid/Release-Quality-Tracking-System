package com.hsenid.releasetracker.model;

import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tests")
public class Test {
    @Id
    @Generated
    private String id;
    @DBRef
    private Release release;
    private String title;
    private String description;
    private String status;
    private int totalTestCases;
    private int passedTestCases;
    @DBRef
    private User testedBy;
}
