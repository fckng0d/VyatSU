package ru.fckng0d.boot.loan.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.fckng0d.boot.loan.controllers.ClientController;
import ru.fckng0d.boot.loan.entities.Client;
import ru.fckng0d.boot.loan.services.ClientService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class ClientValidator implements Validator {
    private ClientService clientService;

    @Autowired
    public ClientValidator(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        validateLastName((Client) target, errors);
        validateFirstName((Client) target, errors);
        validatePatronymic((Client) target, errors);
        validateBirthDate((Client) target, errors);
        validatePassport((Client) target, errors, ClientController.getTempId(), ClientController.getMode());
    }

    private void validateLastName(Client client, Errors errors) {
        if (client.getLastName() == null || client.getLastName().isBlank()) {
            errors.rejectValue("lastName", "", "Поле не заполнено");
            return;
        }

        Pattern pattern = Pattern.compile("^[A-Za-zА-Яа-я]+$");
        Matcher matcher = pattern.matcher(client.getLastName());

        if (!matcher.matches()) {
            errors.rejectValue("lastName", "", "Фамилия должна содержать только буквы");
        } else if (client.getFirstName().length() < 2 || client.getFirstName().length() > 50) {
            errors.rejectValue("lastName", "", "Фамилия должна содержать от 2 до 50 букв");
        }
    }

    private void validateFirstName(Client client, Errors errors) {
        if (client.getFirstName() == null || client.getFirstName().isBlank()) {
            errors.rejectValue("firstName", "", "Поле не заполнено");
            return;
        }

        Pattern pattern = Pattern.compile("^[A-Za-zА-Яа-я]+$");
        Matcher matcher = pattern.matcher(client.getFirstName());

        if (!matcher.matches()) {
            errors.rejectValue("firstName", "", "Имя должно содержать только буквы");
        } else if (client.getFirstName().length() < 2 || client.getFirstName().length() > 50) {
            errors.rejectValue("firstName", "", "Имя должно содержать от 2 до 50 букв");
        }
    }

    private void validatePatronymic(Client client, Errors errors) {
        if (!client.getPatronymic().isBlank()) {
            Pattern pattern = Pattern.compile("^[A-Za-zА-Яа-я]+$");
            Matcher matcher = pattern.matcher(client.getFirstName());

            if (!matcher.matches()) {
                errors.rejectValue("patronymic", "", "Отчество должно содержать только буквы");
            } else if (client.getFirstName().length() < 2 || client.getFirstName().length() > 30) {
                errors.rejectValue("patronymic", "", "Отчество должно содержать от 2 до 30 букв");
            }
        }
    }

    private void validateBirthDate(Client client, Errors errors) {
        if (client.getBirthDate().isBlank()) {
            errors.rejectValue("birthDate", "",
                    "Поле не заполнено");
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatter.parse(client.getBirthDate());
            String[] strDate = client.getBirthDate().split("-");
            if (Integer.parseInt(strDate[0]) > 2023) {
                errors.rejectValue("birthDate", "",
                        "Такой даты не существует");
            } else if (Integer.parseInt(strDate[0]) > 2005 || Integer.parseInt(strDate[0]) < 1900) {
                errors.rejectValue("birthDate", "",
                        "Возраст клиента не может быть меньше 18 и больше 100 лет");
            }
        } catch (ParseException e) {
            errors.rejectValue("birthDate", "",
                    "Дата рождения должна быть в формате yyyy-MM-dd");
        }
    }

    private void validatePassport(Client client, Errors errors, Long id, int mode) {
        if (clientService.isThereSuchPassport(client, id, mode) != null) {
            errors.rejectValue("passport", "", "Такой паспорт уже используется");
            return;
        } else if (client.getPassport().isBlank()) {
            errors.rejectValue("passport", "", "Поле не заполнено");
            return;
        }

        String regex = "^[1-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(client.getPassport());

        if (!matcher.matches()) {
            errors.rejectValue("passport", "", "Паспорт должен состоять только из цифр");
        } else if (client.getPassport().length() != 10) {
            errors.rejectValue("passport", "", "Паспорт должен состоять из 10 цифр");
        }
    }
}
