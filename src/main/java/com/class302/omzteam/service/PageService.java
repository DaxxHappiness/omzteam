package com.class302.omzteam.service;

import com.class302.omzteam.spring.Page;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
public class PageService {
    public Page Pagein(int pageNo, int count){
        Page page = new Page(pageNo,count);
        return page;
    }


}
