package com.class302.omzteam.mybatis;

import com.class302.omzteam.model.ApprovalDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ApprovalDao {
    
    //게시글 수
    @Select("select count(*) from approval")
    int count();
    
    //한명정보 불러오기
    @Select("SELECT board_id, member_name, areacode,title, content FROM approval where board_id = #{board_id}")
    ApprovalDto selectOne(int board_id);

    //게시판 표시
    @Select("SELECT board_id, pass, member_name, areacode, member_name2, regdate, comdate, title FROM approval order by board_id desc")
    List<ApprovalDto> findAll();

    //결재 등록. pass(통과여부에 '대기' 입력해줌)
    @Insert("INSERT INTO approval (pass, regdate, title, content) VALUES ('대기', NOW(), #{title}, #{content})")
    void insert1(ApprovalDto dto);

    //통과시 업데이트
    @Update("UPDATE approval SET pass = #{pass}, member_name2 = #{member_name2}, comdate = now(), comment = #{comment} WHERE board_id = #{board_id}")
    void updateOne(ApprovalDto dto);

    //반려시 업데이트
    @Update("UPDATE approval SET pass = #{pass}, member_name2 = #{member_name2}, comdate = now(), comment = #{comment} WHERE board_id = #{board_id}")
    void updateTwo(ApprovalDto dto);

}
