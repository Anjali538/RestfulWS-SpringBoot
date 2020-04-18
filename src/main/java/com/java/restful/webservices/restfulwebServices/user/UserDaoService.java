package com.java.restful.webservices.restfulwebServices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
//All business logic in service layer , used to connect between the controller and and Database
@Component
public class UserDaoService {
	private static List<User> users = new ArrayList<>();
	private static int   Usercount = 3;
	
static {
	users.add(new User(1,"Anjali", new Date()));
	users.add(new User(2,"Ankur", new Date()));
	users.add(new User(3,"Ankita", new Date()));
	users.add(new User(4,"Atul", new Date()));
}

public List<User> findAll(){
	return users;
	
}
	
public User save(User user) {
	if(user.getId()==null) {
		user.setId(++Usercount);
	}
	users.add(user);
	return user;
	
	
}

public User findOne(int id) {
	User usr = users.stream().filter(user->user.getId()==id).findFirst().get();
	return usr;
	
}

public User deleteById(int id) {
	Iterator<User> itr = users.iterator();
	while(itr.hasNext()) {
		User user = itr.next();
		if(user.getId()==id) {
			itr.remove();
			return user;
		}
	}
	return null;
	
}




}
