package com.class302.omzteam;

import com.class302.omzteam.model.MemberDto;
import com.class302.omzteam.mybatis.MybatisDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OmzteamApplicationTests {

    @Autowired
    MybatisDao mybatisDao;

    @Test
    void contextLoads() {
    }

    @Test
    public void insertTest(){
        // 멤버 정보를 생성
        MemberDto memberDto = MemberDto.builder()
                .mem_no(100022) // 멤버 번호
                .mem_pw("123456") // 비밀번호
                .mem_name("John Doe") // 이름
                .email("john.doe@example.com") // 이메일
                .build();

        // 멤버를 데이터베이스에 삽입
        int insertCount = mybatisDao.insertMember(memberDto);

        // 삽입이 성공적으로 이루어졌는지 확인
        assert(insertCount > 0); // 예상한 레코드 삽입 수
    }

}
