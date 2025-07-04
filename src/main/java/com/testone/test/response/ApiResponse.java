package com.testone.test.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private Object data;
    private String message;

}
