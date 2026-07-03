package tech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Status do Servidor")
@RestController
@RequestMapping("/api")
@CrossOrigin(
        origins = "http://localhost:4200",
        allowCredentials = "true"
)
public class ServerStatus {

    @GetMapping("/status")
    @Operation(description = "Retornar estado do servidor")
    public Map<String, Object> getStatus() {
        Map<String, Object> statusData = Map.of(
                "code_status", 200,
                "message_status", "Ok"
        );

        return Map.of("status", statusData);
    }
}
