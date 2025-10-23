package br.com.carstore.controller;

import br.com.carstore.model.CarDTO;
import br.com.carstore.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PublicController {

    private final CarService carService;

    public PublicController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    public String home(Model model, @RequestParam(name = "q", required = false) String q) {
        List<CarDTO> cars = carService.findAll();
        if (q != null && !q.isBlank()) {
            String qq = q.toLowerCase();
            cars = cars.stream()
                    .filter(c -> (c.getName() + " " + c.getColor()).toLowerCase().contains(qq))
                    .toList();
            model.addAttribute("q", q);
        }
        model.addAttribute("cars", cars);
        return "public/index";
        }
}
