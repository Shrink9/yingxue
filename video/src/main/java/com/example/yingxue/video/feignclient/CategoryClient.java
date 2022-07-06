package com.example.yingxue.video.feignclient;

import com.example.yingxue.common.category.entity.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//映射类：
@FeignClient("category") //通过Feign访问category集群
public interface CategoryClient{
    //指定这个方法对应的url以及参数类型和返回类型
    @GetMapping("/categories/{id}")
    Category getCategoryById(@PathVariable("id") Integer id);
}
