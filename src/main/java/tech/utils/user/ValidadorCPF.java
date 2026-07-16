package tech.utils.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.repository.UserRepository;

@Component
public class ValidadorCPF {

    @Autowired
    private UserRepository userRepository;


    public static boolean isValido(String cpf) {
        if (cpf == null) return false;

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) return false;

        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++ ) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }
            int resto = 11 - (soma % 11);
            int digito1 = (resto == 10 || resto == 11) ? 0 : resto;

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }
            resto = 11 - (soma % 11);
            int digito2 = (resto == 10 || resto == 11) ? 0 : resto;

            return (cpf.charAt(9) - '0' == digito1) && (cpf.charAt(10) - '0' == digito2);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean adminExiste(String usuario) {
        return userRepository.existsByUsuario(usuario);
    }

    public boolean cpfExiste(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return false;
        }

        String cpfBusca = cpf.replaceAll("\\D", "");

        if (cpfBusca.matches("\\d{11}")) {
            cpfBusca = String.format("%s.%s.%s-%s",
                    cpfBusca.substring(0, 3),
                    cpfBusca.substring(3, 6),
                    cpfBusca.substring(6, 9),
                    cpfBusca.substring(9, 11));
        }

        return userRepository.existsByCpf(cpfBusca);
    }

}
