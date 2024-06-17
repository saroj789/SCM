package com.scm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class SocialLink {
    @Id
    private String id;
    private String title;
    private String link;

    @ManyToOne
    private Contact contact;

}
