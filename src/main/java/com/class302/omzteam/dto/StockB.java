package com.class302.omzteam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.util.Date;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class StockB {
    private String category;
    private String itemName;
    private int itemNum;
    private String bName;
    private int pStock;
    private String icDate;
    private String manufacturer;
    private int bNum;
}
