package com.eprocurement.admin.masters.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MasterUnitRequestDto {
    @NotNull
    @NotBlank
    private String masterUnitCode;
    @NotNull
    @NotBlank
    private String masterUnitName;


}
