package tech.templates.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tech.model.component.Componente;
import tech.model.user.User;
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
        User usuario = tech.utils.user.DatabaseUtils.usuarioLogado();

        model.addAttribute("nomebanco", nomebanco);
        model.addAttribute("usuarioLogado", usuario);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("tabelas", tabelas);
        model.addAttribute("componentes", componentes);
    }


    @GetMapping("/main")
    @PreAuthorize("hasRole('ADMIN')")
    public String paginaPrincipal(Model model) {

        preencherDadosPaginaPrincipal(model);
        return "index";
    }

    @GetMapping("/main/editar/{id}")
    public String carregarDadosModal(@PathVariable("id") Long id, Model model) {
        User usuarioExistente = tech.templates.DatabaseUtils.carregarDadosModal(jdbcTemplate, id);

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
        tech.templates.DatabaseUtils.salvarEdicaoModal(jdbcTemplate, usuarioAtualizado);

        return "redirect:/api/main";
    }

    @GetMapping("/main/excluir/{id}")
    public String excluirUsuario(@PathVariable("id") Long id, Model model) {
        User usuarioParaDeletar = tech.templates.DatabaseUtils.excluirUsuario(jdbcTemplate, id);

        model.addAttribute("usuarioParaDeletar", usuarioParaDeletar);
        return "index :: fragmentoModalDeletar";
    }

    @PostMapping("/main/excluir")
    public String executarExclusao(Long id) {
        tech.templates.DatabaseUtils.executarExclusao(jdbcTemplate, id);

        return "redirect:/api/main";
    }
}