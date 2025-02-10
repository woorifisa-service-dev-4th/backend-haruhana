package site.haruhana.www.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Problem> problems = new ArrayList<>();
}
