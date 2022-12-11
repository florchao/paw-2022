package ar.edu.itba.paw.webapp.dto;

public class UserDto {
    private String role;
    private String uid;

    public static UserDto fromForm(String role, String uid){
        UserDto dto = new UserDto();
        dto.role = role;
        dto.uid = uid;
        return dto;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
