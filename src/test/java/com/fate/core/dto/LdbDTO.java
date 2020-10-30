package com.fate.core.dto;

import lombok.Data;

/**
 * @ProjectName: fate-core
 * @Package: com.fate.core.dto
 * @ClassName: LdbDTO
 * @Author: LJP
 * @Description:
 * @Date: 2020/10/29 10:43
 * @Version: 1.0
 */
@Data
public class LdbDTO {

    private int id;
    private String name;
    private int age;

    public LdbDTO(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public LdbDTO() {
    }
}
