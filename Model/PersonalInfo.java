package Model;

import java.awt.*;

public class PersonalInfo {
    String name, title, email, phone, address, profile;
    Image photo;

    public PersonalInfo(String name, String title, String email, String phone, String address, String profile, Image photo) {
        this.name = name;
        this.title = title;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.profile = profile;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }
}
