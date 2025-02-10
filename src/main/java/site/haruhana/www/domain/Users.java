package site.haruhana.www.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String userEmail;
    private String password;
    private String socialType;
    private Date joinDate;
    private String profileImage;
}
