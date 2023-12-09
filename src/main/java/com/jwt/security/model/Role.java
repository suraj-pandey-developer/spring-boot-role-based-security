package com.jwt.security.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Role {
   @Id
   private String rolename;
    private String roleDescription;

}
