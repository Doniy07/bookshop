package org.company.youtube.dto.auth;

import lombok.Getter;
import lombok.Setter;
import org.company.youtube.enums.ProfileRole;


@Getter
@Setter
public class JwtDTO {
    private String id;
    private String username;
    private ProfileRole role;

    public JwtDTO(String id) {
        this.id = id;
    }

    public JwtDTO(String id,String username, ProfileRole role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
