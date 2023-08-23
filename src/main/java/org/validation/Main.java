package org.validation;

import org.validation.validator.JwtGenerator;
import org.validation.validator.Validator;

public class Main {
    public static void main(String[] args) {

        JwtGenerator jwtGenerator = new JwtGenerator("b28c2a56d9e7f3157b4382a076d9e4fc82c15b6a",30000);
        Validator validator = new Validator("jonathan39", "userName@cc.com", "P@ssw0rd", "21-AUG-07");
        System.out.println(validator.validate(jwtGenerator));
    }
}