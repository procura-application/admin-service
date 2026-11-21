package com.eprocurement.admin.masters.dto;

import com.eprocurement.admin.common.utils.CommonStatusEnum;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class DeliveryLocationUpdateRequestDto {

    private String locationName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String businessUnitId;
    private String contactPersonName;

    @Email(message = "Please enter a valid contact email")
    private String contactPersonEmail;

    private String contactPersonNumber;
    private CommonStatusEnum status;
}