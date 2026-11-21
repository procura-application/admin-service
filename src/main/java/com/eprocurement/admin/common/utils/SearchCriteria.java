package com.eprocurement.admin.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {

    private String firstValue;

    private String operator;

    private String lastValue;
}
