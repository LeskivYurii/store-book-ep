package com.epam.rd.autocode.spring.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetClientListResponse {

    private String email;
    private String name;
    private boolean isActive;

}
