//package ru.fckng0d.boot.loan.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//import ru.fckng0d.boot.loan.controllers.ClientController;
//import ru.fckng0d.boot.loan.entities.Client;
//import ru.fckng0d.boot.loan.entities.User;
//import ru.fckng0d.boot.loan.services.UserService;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Configuration
//public class LoginValidator implements Validator {
//    private UserService userService;
//
//    @Autowired
//    public LoginValidator(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return User.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
////        validateLastName((Client) target, errors);
////        validateFirstName((Client) target, errors);
////        validatePatronymic((Client) target, errors);
////        validateBirthDate((Client) target, errors);
//        validatePassport((Client) target, errors, ClientController.getTempId(), ClientController.getMode());
//    }
//
//    private void validateUsername(User user, Errors errors) {
//        if (client.getLastName() == null || client.getLastName().isBlank()) {
//            errors.rejectValue("lastName", "", "Поле не заполнено");
//            return;
//        }
//
//        Pattern pattern = Pattern.compile("^[A-Za-zА-Яа-я]+$");
//        Matcher matcher = pattern.matcher(client.getLastName());
//
//        if (!matcher.matches()) {
//            errors.rejectValue("lastName", "", "Фамилия должна содержать только буквы");
//        } else if (client.getFirstName().length() < 2 || client.getFirstName().length() > 50) {
//            errors.rejectValue("lastName", "", "Фамилия должна содержать от 2 до 50 букв");
//        }
//    }
