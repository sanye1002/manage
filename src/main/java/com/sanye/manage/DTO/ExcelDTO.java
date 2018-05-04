package com.sanye.manage.DTO;

import lombok.Data;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-23 下午 8:50
 * @Description description
 */
@Data
public class ExcelDTO {
    // 表头
    private List<String> titles;

    // 数据
    private List<List<Object>> rows;

    // 页签名称
    private String name;
}
