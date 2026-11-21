package com.eprocurement.admin.masters.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MasterCurrencyAddRequestDto {

    @NotNull
    @NotEmpty
    private String masterCurrencyCode;

    @NotNull
    @NotEmpty
    private String masterCurrencyName;

    @NotNull
    @NotEmpty
    private String mainUnit;

    @NotNull
    @NotEmpty
    private String subUnit;
}
