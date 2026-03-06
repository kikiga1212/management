package com.example.management.Controller;

import com.example.management.DTO.CustomerDTO;
import com.example.management.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    //목록
    @GetMapping({"/", "", "/index"})
    public String listPage(@RequestParam(required = false) String keyword,
                           @RequestParam(required = false) String grade,
                           @PageableDefault(size=10)Pageable pageable, Model model) {
        /*Page<CustomerDTO> page = customerService.searchCustomers(keyword, grade, pageable);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("grade", grade);*/

        return "customer/list";
    }
    //등록폼
    @GetMapping("/customer/register")
    public String registerForm(Model model) {
        model.addAttribute("customer", new CustomerDTO());
        return "customer/register";
    }
    //등록처리
    @PostMapping("/customer/register")
    public String registerProcess(@ModelAttribute CustomerDTO customerDTO) {
        customerService.createCustomer(customerDTO);
        return "redirect:/";
    }
    //상세보기
    @GetMapping("/customer/detail/{cid}")
    public String detailPage(@PathVariable Long cid, Model model) {
        CustomerDTO customer = customerService.getCustomerById(cid);
        model.addAttribute("customer", customer);
        return "customer/detail";
    }
    //수정폼
    @GetMapping("/customer/edit/{cid}")
    public String editForm(@PathVariable Long cid, Model model) {
        CustomerDTO customer = customerService.getCustomerById(cid);
        model.addAttribute("customer", customer);
        return "customer/edit";
    }
    //수정처리
    @PostMapping("/customer/edit/{cid}")
    public String editProcess(@PathVariable Long cid,
                              @ModelAttribute CustomerDTO customerDTO) {
        customerService.updateCustomer(cid, customerDTO);
        return "redirect:/customer/detail/" + cid;
    }
    //삭제
    @GetMapping("/customer/delete/{cid}")
    public String deleteProcess(@PathVariable Long cid) {
        customerService.deleteCustomer(cid);
        return "redirect:/";
    }

}
