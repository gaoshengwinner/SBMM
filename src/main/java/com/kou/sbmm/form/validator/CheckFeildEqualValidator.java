package com.kou.sbmm.form.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class CheckFeildEqualValidator implements ConstraintValidator<CheckFeildEqual, Object> {

	private String feild1;

	private String feild2;

	@Override
	public void initialize(CheckFeildEqual checkFeildEqual) {
		this.feild1 = checkFeildEqual.feild1();
		this.feild2 = checkFeildEqual.feild2();

	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		Object fv1 = beanWrapper.getPropertyValue(feild1);
		Object fv2 = beanWrapper.getPropertyValue(feild2);
		if (fv1 == null && fv2 == null) {
			return true;
		}
		return String.valueOf(fv1).equals(String.valueOf(fv2));
	}

}
