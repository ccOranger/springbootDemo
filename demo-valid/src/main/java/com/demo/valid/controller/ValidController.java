package com.demo.valid.controller;


import com.demo.common.result.Result;
import com.demo.valid.service.First;
import com.demo.valid.service.Group;
import com.demo.valid.service.Second;
import com.demo.valid.entity.OrderVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/valid")
public class ValidController {

    /**
     *  @author: 李臣臣
     *  @Date: 2020/12/17 0017 16:30
     *  @Description: @Validated没有分组时 全部校验没有添加分组的字段
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增")
    public Result add(@RequestBody @Validated OrderVO orderVO) {
        return Result.build();
    }

    /**
     *  @author: 李臣臣
     *  @Date: 2020/12/17 0017 16:30
     *  @Description: @Validated有分组时 只校验分组的字段,这种校验是无序的
     */
    @PostMapping("/add2")
    @ApiOperation(value = "新增2")
    public Result add2(@RequestBody @Validated({First.class, Second.class}) OrderVO orderVO) {
        return Result.build();
    }


    /**
     *  @author: 李臣臣
     *  @Date: 2020/12/17 0017 16:30
     *  @Description: 只校验分组的字段,这种校验是是有序的
     */
    @PostMapping("/add3")
    @ApiOperation(value = "新增3")
    public Result add3(@RequestBody @Validated({Group.class}) OrderVO orderVO) {
        return Result.build();
    }

    /**
     *  @author: 李臣臣
     *  @Date: 2020/12/17 0017 16:19
     *  @Description: 有分组，则只校验分组
     */
    @PostMapping("/update")
    @ApiOperation(value = "编辑")
    public Result update(@RequestBody @Validated({First.class}) OrderVO orderVO) {
        return Result.build();
    }
}
