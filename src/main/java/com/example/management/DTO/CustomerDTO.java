package com.example.management.DTO;

import lombok.*;

@Data
//@Getter
//@Setter
//@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Long cid;       //번호
    private String name;    //이름
    private String email;   //이메일
    private String phone;   //전화번호
    private String address; //주소
    private String company; //회사명
    private String grade;   //등급(VIP, GOLD, SILVER, NORMAL)
    private String memo;    //메모
    private String regDate; //등록일
}
