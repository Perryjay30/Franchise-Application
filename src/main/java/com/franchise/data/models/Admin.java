package com.franchise.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Admin extends User {
    private String uniqueAdminId;
    private Role userRole;
}
