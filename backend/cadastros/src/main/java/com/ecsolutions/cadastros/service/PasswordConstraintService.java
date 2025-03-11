package com.ecsolutions.cadastros.service;

import com.ecsolutions.cadastros.model.attributes.PasswordConstraintAttributes;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.passay.CharacterRule;
import org.passay.PasswordData;
import org.passay.PasswordGenerator;
import org.passay.PasswordValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PasswordConstraintService {

    private final PasswordValidator passwordValidator;

    private final PasswordConstraintAttributes passwordConstraintAttributes;

    public void isValid(String password) {
        var result = passwordValidator.validate(new PasswordData(password));

        if (!result.isValid()) {
            throw new BadRequestException(
                    String.format("Senha não corresponde aos requisitos mínimos de segurança." +
                            " A senha deve conter letras maiúsculas, minúsculas, números e" +
                            " pelo menos %s caractere(s) especial", this.passwordConstraintAttributes.getSpecial())
            );
        }
    }

    public String generatePassword() {
        var rules = new ArrayList<CharacterRule>();

        for (var rule : this.passwordConstraintAttributes.generateRules()) {
            if (rule instanceof CharacterRule characterRule) {
                rules.add(characterRule);
            }
        }
        var generator = new PasswordGenerator();
        return generator.generatePassword(8, rules);
    }
}
