package com.vulnerable.api.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class Country {
    private String id;
    private String name;
    private String code;
}
