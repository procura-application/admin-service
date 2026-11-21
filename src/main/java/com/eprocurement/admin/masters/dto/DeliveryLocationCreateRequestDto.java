package com.eprocurement.admin.masters.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeliveryLocationCreateRequestDto {

    @NotBlank(message = "Please enter location name")
    private String locationName;

    @NotBlank(message = "Please enter address line 1")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "Please enter city")
    private String city;

    @NotBlank(message = "Please enter state")
    private String state;

    @NotBlank(message = "Please enter country")
    private String country;

    @NotBlank(message = "Please enter postal code")
    private String postalCode;

    @NotBlank(message = "Please send the business unit ID")
    private String businessUnitId;

    private String contactPersonName;

    @Email(message = "Please enter a valid contact email")
    private String contactPersonEmail;

    private String contactPersonNumber;
}