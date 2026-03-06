package com.example.management.Repository;

import com.example.management.Entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends
        JpaRepository<CustomerEntity, Long> {
    //" 3개는 다중문장
    //검색 SELECT 추출할 필드명 FROM 테이블명 별칭 WHERE 조건
    //1. CustomerEntity를 c로 대신 이름 사용
    //2. SELECT c : CustomerEntity의 모든 필드값을 읽어온다.(*)
    //3. AND 앞과 뒤에 조건을 모두 만족하면
    //4. 전달받은 keyword가 이름 또는 이메일 또는 회사명 또는 전화번호에 포함되어 있으면
    //%:keyword% 포함되어 있으면 %:keyword ~로 끝나면, :keywrod% ~로 시작하면
    //:grade 같으면(%가 없으면)
    @Query("""
        SELECT c FROM CustomerEntity c
            WHERE (
                :keyword IS NULL OR 
                c.name LIKE %:keyword%
                OR c.email LIKE %:keyword%    
                OR c.company LIKE %:keyword%
                OR c.phone LIKE %:keyword%        
                ) AND (:grade IS NULL OR c.grade = :grade)
    """)
    Page<CustomerEntity> search(
            @Param("keyword") String keyword,
            @Param("grade") String grade,
            Pageable pageable
    );
}
