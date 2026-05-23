package com.campustrade.common;

import java.util.List;

/**
 * 分页响应格式
 * 前端收到的JSON格式:
 * {"total": 100, "page": 1, "size": 10, "records": [...]}
 *
 * total   总条数
 * page    当前页码
 * size    每页条数
 * records 当前页数据列表
 */
public class PageDTO<T> {
    private long total;         // 总条数 (Long)
    private long page;          // 当前页码 (Long)
    private long size;          // 每页条数 (Long)
    private List<T> records;    // 数据列表 (Array)

    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
    public long getPage() { return page; }
    public void setPage(long page) { this.page = page; }
    public long getSize() { return size; }
    public void setSize(long size) { this.size = size; }
    public List<T> getRecords() { return records; }
    public void setRecords(List<T> records) { this.records = records; }

    public static <T> PageDTO<T> of(long total, long page, long size, List<T> records) {
        PageDTO<T> dto = new PageDTO<>();
        dto.total = total;
        dto.page = page;
        dto.size = size;
        dto.records = records;
        return dto;
    }
}
