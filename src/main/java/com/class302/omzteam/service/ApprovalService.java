package com.class302.omzteam.service;

import com.class302.omzteam.model.ApprovalDto;
import com.class302.omzteam.model.ApprovalSubDto;
import com.class302.omzteam.mybatis.ApprovalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class ApprovalService {
    private final ApprovalDao approvalDao;

    @Autowired
    public ApprovalService(ApprovalDao approvalDao) {
        this.approvalDao = approvalDao;
    }

}
