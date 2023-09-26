package com.class302.omzteam.mybatis;

import com.class302.omzteam.model.MemberDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MybatisDao {
//    Integer selectCount();
//    @Select("SELECT COUNT(*) FROM your_table_name")
//    Integer selectCount();
//
//    // 또는 파라미터를 사용하는 경우
//    @Select("SELECT COUNT(*) FROM your_table_name WHERE column_name = #{parameterName}")
//    Integer selectCountWithParameter(@Param("parameterName") String parameterName);

    @Select("SELECT * FROM member WHERE mem_no = #{memNo}")
    MemberDto findByMemNo(@Param("memNo") String memNo);

    @Insert("INSERT INTO member (mem_no, mem_pw, mem_name, email) " +
            "VALUES (#{member.mem_no}, #{member.mem_pw}, #{member.mem_name}, #{member.email})")
    int insertMember(@Param("member") MemberDto member);

}
