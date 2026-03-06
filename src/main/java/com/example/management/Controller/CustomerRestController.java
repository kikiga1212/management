package com.example.management.Controller;

import com.example.management.DTO.CustomerDTO;
import com.example.management.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//rest(비동기) 방식은 맵핑명이 /api로 일반적으로 시작
@RestController //@Controller + @ResponseBody
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerRestController {
    private final CustomerService customerService;

    //조회
    //ResponseEntity<데이터형> 하나의 값만 전달
    //ResponseEntity<DTO> DTO를 이용해서 다중 변수 전달
    //ResponseEntity<Map<변수명 데이터형, 값 형>> 다중 변수로 전달
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCustomers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String grade,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        //Service에서도 페이지 정보를 만들 수 있다.
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerDTO> pageResult = customerService.searchCustomers(keyword, grade, pageable);
        System.out.println(pageResult.getContent().toString());
        //model.addAttribute == response.put
        Map<String, Object> response = new HashMap<>(); //맵 생성==>model과 사용방법이 동일
        response.put("content", pageResult.getContent());  //조회한 DTO list
        response.put("totalElements", pageResult.getTotalElements()); //페이지정보
        response.put("totalPages", pageResult.getTotalPages());
        response.put("currentPage", pageResult.getNumber());
        response.put("first", pageResult.isFirst());
        response.put("last", pageResult.isLast());

        return ResponseEntity.ok(response);
    }
    //개별조회
    @GetMapping("/{cid}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long cid) {
        CustomerDTO customer = customerService.getCustomerById(cid);

        return ResponseEntity.ok(customer);
    }
    //삽입처리
    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            customerService.createCustomer(customerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("고객이 등록되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("등록 실패 :"+e.getMessage()); //오류메세지와 함께 전달
        }
    }
    //수정처리 (부분수정은 PatchMapping)
    @PutMapping("/{cid}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long cid,
                                                 @RequestBody CustomerDTO customerDTO) {
        try {
            customerService.updateCustomer(cid, customerDTO);
            return ResponseEntity.ok("고객 정보가 수정되엇습니다.");
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("고객 수정 실패 : "+e.getMessage());
        }
    }
    //삭제처리
    @DeleteMapping("/{cid}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long cid) {
        try {
            customerService.deleteCustomer(cid);
            return ResponseEntity.ok("고객이 삭제되었습니다.");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("고객 삭제 실패 :"+e.getMessage());
        }
    }
}
//Form : GetMapping, PostMapping
//Controller : GetMapping, PostMapping
//조회 : /customer, 개별조회 : /customer/1, 삽입처리 : /customer/insert
//수정처리 : /customer/update/1, 삭제처리 : /customer/delete/1

//JavaScript : GetMapping, PostMapping, PutMapping, PatchMapping, DeleteMapping
//RestController : GetMapping, PostMapping, PutMapping, PatchMapping, DeleteMapping
//조회 : /api/customer, 개별조회 : /api/customer/1, 삽입 : /api/customer
//수정처리 : /api/customer/1, 삭제처리 : /api/customer/1