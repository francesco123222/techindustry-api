package tech.model.user;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class UserListener {

    @PrePersist
    @PreUpdate
    public void formatarCpf(User user) {
        if (user.getCpf() != null) {
            String cpfLimpo = user.getCpf().trim();
            if (cpfLimpo.matches("^\\d{11}")) {
                String cpfFormatado = String.format("%s.%s.%s-%s",
                        cpfLimpo.substring(0, 3),
                        cpfLimpo.substring(3, 6),
                        cpfLimpo.substring(6, 9),
                        cpfLimpo.substring(9, 11)
                );
                user.setCpf(cpfFormatado); // Altera o estado da entidade antes da persistência
            }
        }
    }
}
