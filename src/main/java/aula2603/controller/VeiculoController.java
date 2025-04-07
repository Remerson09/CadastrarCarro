package aula2603.controller;

import aula2603.model.dao.VeiculoDao;
import aula2603.model.entity.Veiculo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/veiculo")
public class VeiculoController {
    VeiculoDao dao = new VeiculoDao();

    @GetMapping("/list")
    public ModelAndView list(ModelMap model) {
        model.addAttribute("veiculos", dao.veiculos());
        return new ModelAndView("/veiculo/list", model);
    }

    @GetMapping("/form")
    public String form(Veiculo veiculo) {
        return "/veiculo/form";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("veiculo", dao.buscarPorId(id));
        return new ModelAndView("/veiculo/form", model);
    }

    @PostMapping("/save")
    public String save(Veiculo veiculo) {
        if (veiculo.getId() == null) {
            dao.salvar(veiculo);
        } else {
            dao.atualizar(veiculo);
        }
        return "redirect:/veiculo/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        dao.excluir(id);
        return "redirect:/veiculo/list";
    }
    @PostMapping("/buscar")
    public ModelAndView buscar(@RequestParam("modelo") String modelo, ModelMap model) {
        var encontrados = dao.buscarPorModelo(modelo);
        if (encontrados.isEmpty()) {
            model.addAttribute("msgErro", "Nenhum veículo encontrado com o modelo informado.");
            model.addAttribute("veiculos", dao.veiculos());
        } else {
            model.addAttribute("veiculos", encontrados);
        }
        return new ModelAndView("/veiculo/list", model);
    }
}