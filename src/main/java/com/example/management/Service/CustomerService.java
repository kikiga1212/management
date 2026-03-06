package com.example.management.Service;

import com.example.management.DTO.CustomerDTO;
import com.example.management.Entity.CustomerEntity;
import com.example.management.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    //목록
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerEntity> customerEntities = customerRepository.findAll();

        List<CustomerDTO> customerDTOS = customerEntities.stream()
                .map(c->modelMapper.map(c, CustomerDTO.class))
                .collect(Collectors.toList());
        return customerDTOS;

        //return customerRepository.findAll().stream()
        //        .map(c->modelMapper.map(c, CustomerDTO.class))
        //        .collect(Collectors.toList());
    }
    //검색(조회)
    public Page<CustomerDTO> searchCustomers(String keyword, String grade, Pageable pageable) {
        //유효성
        String kw = StringUtils.hasText(keyword) ?  keyword : null; //키워드가 문자열로 구성되었으면 저장, 아니면 null
        String gr = StringUtils.hasText(grade) ? grade : null;
        System.out.println(kw);
        System.out.println(gr);
        System.out.println(customerRepository.search(kw, gr, pageable).toString());
        return customerRepository.search(kw, gr, pageable)
                .map(c->modelMapper.map(c, CustomerDTO.class));
    }

    //상세보기
    public CustomerDTO getCustomerById(Long cid) {
        CustomerEntity customer = customerRepository.findById(cid)
                .orElseThrow(()->new IllegalStateException("고객 정보를 찾을 수 없습니다."));

        return modelMapper.map(customer, CustomerDTO.class);
    }

    //삽입
    @Transactional
    public void createCustomer(CustomerDTO dto) {
        CustomerEntity customer = modelMapper.map(dto, CustomerEntity.class);
        customer.setRegDate(LocalDate.now().toString()); //현재 날짜를 문자열로 변환해서 저장
        customerRepository.save(customer);
    }
    //수정
    @Transactional
    public void updateCustomer(Long cid, CustomerDTO dto) {
        CustomerEntity customer = customerRepository.findById(cid)
                .orElseThrow(()->new IllegalStateException("고객 정보를 찾을 수 없습니다."));
        //Long key = customer.getCid();
        //builder를 이용해서 customer에 변경할 dto값을 적용
        modelMapper.map(dto, customer); //기본키(번호)까지 변경
        customer.setCid(cid); //조회한 번호로 기본키값을 변경해서 오류방지

        customerRepository.save(customer);
    }
    //삭제
    @Transactional
    public void deleteCustomer(Long cid) {
        customerRepository.deleteById(cid);
    }
}
//@Transactional을 적용하면 실패서 rollback적용
