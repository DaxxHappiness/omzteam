package com.class302.omzteam.mybatis;

import com.class302.omzteam.spring.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberDao {


    @Select("select * FROM MEMBER WHERE mem_no = #{mem_no}")
    Member memberByno(int mem_no);

    @Select("select * FROM MEMBER WHERE dept_no = #{dept_no}")
    List<Member> memberBydept(int dept_no);

    @Select("select * FROM MEMBER")
    List<Member> memberAll();




}
