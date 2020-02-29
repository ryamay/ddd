package com.yamari.idddd.domain.models.Users;

class UserId {
	private String value;
	
	public UserId(String value) {
		if (value == null) {
			throw new IllegalArgumentException("ユーザIDは必ず入力してください。");
		}
		this.value = value;
	}
}
