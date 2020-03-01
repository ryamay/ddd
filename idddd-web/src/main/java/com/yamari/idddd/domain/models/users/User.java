package com.yamari.idddd.domain.models.users;

import java.util.Objects;
import java.util.UUID;

public class User {
	public UserId id;
	public UserName name;
		
	public User(UserName name) {
		this.id = new UserId(UUID.randomUUID().toString());		
		this.changeName(name);
	}
		
	public User(UserId id, UserName name) {
		if(id == null) {
			throw new IllegalArgumentException("idは必ず入力してください。");
		}
		
		this.id =id;
		this.changeName(name);
	}
	
	public void changeName(UserName name) {
		if (name == null) {
			throw new IllegalArgumentException("nameは必ず入力してください。");
		}
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true;	}
		if (obj == null) { return false; }
		if (!(obj instanceof User)) { return false;	}
		
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
}
