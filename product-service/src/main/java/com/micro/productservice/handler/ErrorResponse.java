package com.micro.productservice.handler;

import java.util.Map;

public record ErrorResponse(Map<String, String> errors) {
}
