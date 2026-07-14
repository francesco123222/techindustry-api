package tech.templates.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tech.model.component.Componente;
import tech.model.user.User;
import tech.model.user.enums.UserRole;
import tech.utils.user.DatabaseUtils;
import tech.utils.user.ValidadorCPF;

import java.sql.Connection;
import java.util.List;

@RequestMapping("/api")
@Controller
public class AdminController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private void preencherDadosPaginaPrincipal(Model model) {
        String nomebanco = jdbcTemplate.execute(Connection::getCatalog);
        List<User> usuarios = DatabaseUtils.listarUsuarios(jdbcTemplate);
        List<String> tabelas = tech.templates.DatabaseUtils.listarTabelas(jdbcTemplate);
        List<Componente> componentes = tech.utils.component.DatabaseUtils.listarComponentes(jdbcTemplate);

        model.addAttribute("nomebanco", nomebanco);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("tabelas", tabelas);
        model.addAttribute("componentes", componentes);
    }


    @GetMapping("/main")
    public String paginaPrincipal(Model model) {

        preencherDadosPaginaPrincipal(model);
        return "index";
    }

    @GetMapping("/main/editar/{id}")
    public String carregarDadosModal(@PathVariable("id") Long id, Model model) {
        String sql = "SELECT id, usuario, cpf, role FROM sch_techindustry.tb_usuario WHERE id = ?";

        User usuarioExistente = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            User u = new User();
            u.setId(rs.getLong("id"));
            u.setUsuario(rs.getString("usuario"));
            u.setCpf(rs.getString("cpf"));
            String roleValue = rs.getString("role");
            if (roleValue != null) {
                u.setRole(UserRole.valueOf(roleValue.toUpperCase()));
            }

            return u;
        }, id);

        model.addAttribute("usuarioParaEditar", usuarioExistente);
        return "index :: fragmentoModalEditar";
    }

    @PostMapping("/main/editar")
    public String salvarEdicaoModal(@Valid @ModelAttribute("usuarioParaEditar") User usuarioAtualizado,
                                    BindingResult result,
                                    Model model) {

        if (usuarioAtualizado.getCpf() != null && !usuarioAtualizado.getCpf().isBlank()) {
            if (!ValidadorCPF.isValido((usuarioAtualizado.getCpf()))) {

                result.rejectValue("cpf", "FieldError", "Por favor, insira um CPF válido.");
            }
        } else {
            result.rejectValue("cpf", "FieldError", "O CPF não pode estar em branco.");
        }


        if (result.hasErrors()) {
            if (!result.hasFieldErrors("senha") || result.getErrorCount() > 1) {

                preencherDadosPaginaPrincipal(model);

                model.addAttribute("usuarioParaEditar", usuarioAtualizado);
                return "index";
            }
        }

        String sql = "UPDATE sch_techindustry.tb_usuario SET usuario = ?, cpf = ?, role = ? WHERE id = ?";
        String roleStr = null;
        if (usuarioAtualizado.getRole() != null) {
            roleStr = usuarioAtualizado.getRole().name();
        }

        jdbcTemplate.update(sql,
                usuarioAtualizado.getUsuario(),
                usuarioAtualizado.getCpf(),
                roleStr,
                usuarioAtualizado.getId());

        return "redirect:/api/main";
    }

    @GetMapping("/main/excluir/{id}")
    public String excluirUsuario(@PathVariable("id") Long id, Model model) {
        String sql = "SELECT id, usuario FROM sch_techindustry.tb_usuario WHERE id = ?";

        User usuarioParaDeletar = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            User u = new User();
            u.setId(rs.getLong("id"));
            u.setUsuario(rs.getString("usuario"));
            return u;
        }, id);

        model.addAttribute("usuarioParaDeletar", usuarioParaDeletar);
        return "index :: fragmentoModalDeletar";
    }

    @PostMapping("/main/excluir")
    public String executarExclusao(Long id) {
        String sql = "DELETE FROM sch_techindustry.tb_usuario WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return "redirect:/api/main";
    }
}