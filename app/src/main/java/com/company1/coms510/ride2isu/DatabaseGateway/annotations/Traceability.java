package com.company1.coms510.ride2isu.DatabaseGateway.annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface Traceability {
	String testName();
	int storyId();
	String userId();
	String createdDate() default "MM/DD/YYYY";
}
