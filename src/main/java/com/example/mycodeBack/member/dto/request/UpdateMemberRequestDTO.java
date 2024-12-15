package com.example.mycodeBack.member.dto.request;

import lombok.Data;

@Data
public class UpdateMemberRequestDTO {
    private String name;
    private String gender;
    private String birthDate;

    // Getter와 Setter 추가
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
