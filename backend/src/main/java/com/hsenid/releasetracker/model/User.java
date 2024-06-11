package com.hsenid.releasetracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document (collection = "user")
public class User{

    @Id
    @Generated
    private String id;
    private String username;
    private String password;
    private String role;
}
