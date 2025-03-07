package org.example.ecommerceproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.ecommerceproject.model.Role;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    @NotBlank(message = "Tên người dùng không được để trống! ")
    @Size(min = 5,max = 30,message = "Tên người dùng phải hơn 2 ký tự và nhỏ hơn 30 ký tự!")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "Mật khẩu không được để trống!")
    private String password;

    @Column(name = "fullname")
    @NotBlank(message = "Họ và tên không được để trống!")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Họ và tên chỉ được chứa chữ cái và khoảng trắng!")
    private String fullname;

    @Column(name = "gender")
    @NotNull(message = "Giới tính không được để trống!")
    private Boolean gender;

    @Column(name = "email")
    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Email không hợp lệ!")
    private String email;

    @Column(name = "phone")
    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại phải có đúng 10 chữ số và chỉ chứa số!")
    private String phone;

    @Column(name = "address")
    @NotBlank(message = "Địa chỉ không được để trống!")
    private String address;

    @Column(name = "birth")
    @NotNull(message = "Ngày sinh không được để trống!")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    @Column(name = "avatar")
    private String avatar;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbacks;


    @OneToOne(mappedBy = "user")
    private Cart cart;


}
