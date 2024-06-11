package com.hsenid.releasetracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "release")
public class Release {

    @Id
    @Generated
    private String releaseId;
    @DBRef
    private Product product;
    private double version;
    private Date releaseDate;

}
