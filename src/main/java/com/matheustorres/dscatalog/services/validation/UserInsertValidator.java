package com.matheustorres.dscatalog.services.validation;

import java.util.ArrayList;
import java.util.List;

import com.matheustorres.dscatalog.controllers.exceptions.FieldMessage;
import com.matheustorres.dscatalog.dto.UserDTO;
import com.matheustorres.dscatalog.entities.User;
import com.matheustorres.dscatalog.repositories.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserDTO> {
	

	private final UserRepository repository;

	public UserInsertValidator(UserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void initialize(UserInsertValid ann) {
	}

	@Override
	public boolean isValid(UserDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		User user = repository.findByEmail(dto.email());
		if(user != null) {
			list.add(new FieldMessage("email", "Email já existe!"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.message()).addPropertyNode(e.fieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}