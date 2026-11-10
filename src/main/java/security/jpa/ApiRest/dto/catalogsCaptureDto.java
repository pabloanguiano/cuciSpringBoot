package security.jpa.ApiRest.dto;

import lombok.*;

@Getter
@Setter

public class catalogsCaptureDto {

    private int id;
    private String description;
    private String identifier;

    public catalogsCaptureDto(int id, String description, String identifier) {
        this.id = id;
        this.description = description;
        this.identifier = identifier;
    }
}
