package org.judalabs.validator;

import org.judalabs.PessoaDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PessoaValidator implements ConstraintValidator<PessoaValida, PessoaDTO> {

    @Override
    public boolean isValid(PessoaDTO pessoaDTO, ConstraintValidatorContext context) {
        if("1".equals(pessoaDTO.getNome()) || "1".equals(pessoaDTO.getApelido()))
            return false;

        if(pessoaDTO.getStack() == null) return true;

        for(String item : pessoaDTO.getStack()) {
            if("1".equals(item) || item.length() > 32) return false;
        }
        return true;

    }
}
