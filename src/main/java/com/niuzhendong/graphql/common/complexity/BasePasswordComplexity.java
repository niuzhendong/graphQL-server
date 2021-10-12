package com.niuzhendong.graphql.common.complexity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class BasePasswordComplexity {
    private String name = "easy";
    private Boolean enable = false;
    private Map<String, BasePasswordPattern> patterns;

    public BasePasswordComplexity() {
        BasePasswordPattern medium = new BasePasswordPattern();
        medium.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,15}$");
        medium.setMessage("只能包含大小写字母,数字且长度在6-15位");
        BasePasswordPattern easy = new BasePasswordPattern();
        easy.setPattern("(?=.*[0-9])");
        easy.setMessage("只能包含数字");
        this.patterns = new HashMap();
        this.patterns.put("easy", easy);
        this.patterns.put("medium", medium);
    }
}