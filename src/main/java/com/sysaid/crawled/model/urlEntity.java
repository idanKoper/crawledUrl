package com.sysaid.crawled.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
public class urlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String url;

    @NotEmpty
    @ElementCollection
    private List<String> innerLinks;

    public urlEntity() {
    }

    public urlEntity(String url, List<String> links ) {
        this.url = url;
        this.innerLinks = links;
    }
}
