package com.packt.webstore.exception;

public class InvalidCartException extends RuntimeException {
	private static final long serialVersionUID = -3522041566033321491L;
	private String cartId;

	public InvalidCartException(String cartId) {
		this.cartId = cartId;
	}

	public String getCartId() {
		return cartId;
	}
}
