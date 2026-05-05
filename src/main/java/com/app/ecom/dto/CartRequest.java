package com.app.ecom.dto;

import lombok.Data;

@Data
public class CartRequest {
	private Long productId;
	private Integer quantity;
}
