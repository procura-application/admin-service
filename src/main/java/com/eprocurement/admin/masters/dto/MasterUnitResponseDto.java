package com.eprocurement.admin.masters.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MasterUnitResponseDto {

    private String masterUnitCode;

    private String masterUnitName;

    private String status;


}
