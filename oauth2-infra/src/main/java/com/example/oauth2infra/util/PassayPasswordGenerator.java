package com.example.oauth2infra.util;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.springframework.stereotype.Component;
import org.passay.CharacterData;

import com.oauth2core.port.util.PasswordGenerator;

@Component
public class PassayPasswordGenerator implements PasswordGenerator {

    @Override
    public String generate() {
        return generate(10);

    }

    @Override
    public String generate(Integer length) {
        var gen = new org.passay.PasswordGenerator();
        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase);
        CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit);

        // Define special character data
        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "INSUFFICIENT_SPECIAL";
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule specialCharRule = new CharacterRule(specialChars);

        return gen.generatePassword(length, specialCharRule, lowerCaseRule, upperCaseRule, digitRule);

    }

}
