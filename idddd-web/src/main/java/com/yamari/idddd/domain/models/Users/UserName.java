package com.yamari.idddd.domain.models.Users;

class UserName {
	private String value;
	
	public UserName(String value) {
		if (value == null) {
			throw new IllegalArgumentException("ユーザ名は必ず入力してください。");
		} 
		if (value.length() < 3) {
			throw new IllegalArgumentException("ユーザ名は3文字以上です。");			
		}
		this.value = value;
	}
}
