package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.dto.MyFollowConditionDto;
import com.example.kkaddak.core.dto.MyFollowerResDto;
import com.example.kkaddak.core.entity.Member;
import java.util.List;

public interface MemberRepositoryCustom {
    List<MyFollowerResDto> findMyFollowersByMember(MyFollowConditionDto condition, int memberId);
}