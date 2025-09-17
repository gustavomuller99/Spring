package tacos.web.MVCcontrollers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tacos.web.configuration.OrderProps;
import tacos.web.entities.Order;
import tacos.web.entities.User;
import tacos.web.repository.OrderRepository;

@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/MVCorders")
public class MVCOrderController {

    private final OrderRepository orderRepository;
    private final OrderProps orderProps;

    @Autowired
    public MVCOrderController(OrderRepository orderRepository, OrderProps orderProps) {
        this.orderRepository = orderRepository;
        this.orderProps = orderProps;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        return "orderForm";
    }

    @PostMapping()
    public String orderSubmit(@ModelAttribute("order") Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        order.setUser(user);

        orderRepository.save(order);
        sessionStatus.setComplete();

        log.info(order.toString());
        return "redirect:/";
    }
}
