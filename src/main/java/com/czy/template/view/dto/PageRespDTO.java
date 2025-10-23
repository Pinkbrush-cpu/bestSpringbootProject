package com.czy.template.view.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRespDTO<T> {
    private List<T> list;    // 当前页数据
    private long    total;   // 总条数
    private long    pages;   // 总页数
    private long    current; // 当前页码
    private long    size;    // 每页条数

}