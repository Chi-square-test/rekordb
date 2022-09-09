package com.rekordb.rekordb.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RenameDTO {

    private String id;

    @NotBlank(message = "이름")
    private String name;
}
