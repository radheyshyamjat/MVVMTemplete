package com.task.demo.utils;

import com.task.demo.data.model.ErrorModelDTO;

public class ErrorParseMessageUtils {
    public static String getMessageFromErrorBody(String errorBody) {
        ErrorModelDTO obj;
        obj = (ErrorModelDTO) JsonUtils.convertStringToPojo(errorBody, ErrorModelDTO.class);
        if (obj.getMessage() != null && obj.getMessage().length() > 0)
            return obj.getMessage();
        return "";
    }
}
