package br.com.carstore.controller;

import br.com.carstore.model.CarDTO;
import br.com.carstore.service.CarService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CarService carService;

    public AdminController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public String dashboard(Model model) {
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("form", new CarDTO());
        return "admin/dashboard";
    }

    @PostMapping("/cars")
    public String create(@Valid @ModelAttribute("form") CarDTO form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("cars", carService.findAll());
            return "admin/dashboard";
        }
        carService.save(form);
        return "redirect:/admin/cars";
    }

    @PostMapping("/cars/{id}/delete")
    public String delete(@PathVariable String id) {
        carService.deleteById(id);
        return "redirect:/admin/cars";
    }

    @PostMapping("/cars/{id}/update")
    public String update(@PathVariable String id,
                         @RequestParam("name") String name,
                         @RequestParam("color") String color,
                         Model model) {
        CarDTO dto = new CarDTO();
        dto.setName(name);
        dto.setColor(color);
        carService.update(id, dto);
        return "redirect:/admin/cars";
    }
}
