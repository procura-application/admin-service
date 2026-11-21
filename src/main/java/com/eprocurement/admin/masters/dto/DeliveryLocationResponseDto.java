package com.eprocurement.admin.masters.dto;

import com.eprocurement.admin.common.utils.CommonStatusEnum;
import lombok.Data;

@Data
public class DeliveryLocationResponseDto {
    private String id;
    private String deliveryLocationCode;
    private String locationName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String businessUnitId;
    private String businessUnitCode;
    private String businessUnitName;
    private String contactPersonName;
    private String contactPersonEmail;
    private String contactPersonNumber;
    private CommonStatusEnum status;
}