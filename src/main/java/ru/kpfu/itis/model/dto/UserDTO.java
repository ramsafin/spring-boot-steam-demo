package ru.kpfu.itis.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Objects;

public class UserDTO {

    @NotBlank
    private String fullName;

    @NotBlank
    private String telephone;

    private String aboutMe;

    public UserDTO() {
    }

    public UserDTO(String fullName, String sex, String telephone, String aboutMe) {
        this.fullName = fullName;
        this.telephone = telephone;
        this.aboutMe = aboutMe;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (fullName != null ? !fullName.equals(userDTO.fullName) : userDTO.fullName != null) return false;
        if (telephone != null ? !telephone.equals(userDTO.telephone) : userDTO.telephone != null) return false;
        return aboutMe != null ? aboutMe.equals(userDTO.aboutMe) : userDTO.aboutMe == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, telephone, aboutMe);
    }
}
