package org.validation.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.*;
public class Validator {
    private String username;
    private String email;
    private String password;
    private String dob;

    public Validator(String username, String email, String password, String dob) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }
    public Validator(){

    }

    public String getUsername(){
        return username;
    }

    public String validate(JwtGenerator jwtGenerator) {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder("Validation failed for:");

        // Username validation
        if (username == null || username.length() < 4) {
            isValid = false;
            errorMessage.append("\nUsername: min 4 characters required");
        }

        // Email validation
        if (email == null || !isValidEmail(email)) {
            isValid = false;
            errorMessage.append("\nEmail: valid email address required");
        }

        // Password validation
        if (password == null || !isValidPassword(password)) {
            isValid = false;
            errorMessage.append("\nPassword: strong password required");
        }

        // Date of Birth validation
        if (dob == null || !isValidDOB(dob)) {
            isValid = false;
            errorMessage.append("\nDate of Birth: must be 16 years or greater");
        }

        if (!isValid) {
            //return errorMessage.toString();
            return "verification fails";
        }

        Validator user = new Validator();
        return jwtGenerator.verifyJwt(jwtGenerator.generateJwt(user.getUsername(), jwtGenerator.getExpirationMillis(), jwtGenerator.getSecretKey()), jwtGenerator.getSecretKey());
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9]+\\.)+[A-Za-z]{2,4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[!@#$%^&*(),.?\":{}|<>])(?=.*[A-Z])(?=.*[0-9]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean isValidDOB(String dob) {
        // Define the regex pattern for "DD-MON-YY" format
        String regex = "^(0[1-9]|[12][0-9]|3[01])-(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)-\\d{2}$";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Match the input date against the pattern
        Matcher matcher = pattern.matcher(dob);

        if (!matcher.matches()) {
            return false; // Invalid format
        }

        // Parse the input date to a Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
        Date date;
        try {
            date = dateFormat.parse(dob);
        } catch (ParseException e) {
            return false; // Parsing error
        }

        // Calculate age based on the parsed date
        Calendar dobCal = Calendar.getInstance();
        dobCal.setTime(date);
        Calendar today = Calendar.getInstance();
        today.add(Calendar.YEAR, -16); // Subtract 16 years from today

        return dobCal.before(today); // Return true if date of birth is before today - 16 years
    }

}
