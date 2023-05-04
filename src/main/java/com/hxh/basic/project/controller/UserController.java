package com.hxh.basic.project.controller;


import com.alibaba.fastjson.JSONObject;
import com.hxh.basic.project.annotation.OpLog;
import com.hxh.basic.project.config.UserToken;
import com.hxh.basic.project.enums.OperationType;
import com.hxh.basic.project.enums.ResultEnum;
import com.hxh.basic.project.form.user.AddUserForm;
import com.hxh.basic.project.form.user.ListUserForm;
import com.hxh.basic.project.service.IUserService;
import com.hxh.basic.project.utils.JwtUtil;
import com.hxh.basic.project.utils.ResultVoUtil;
import com.hxh.basic.project.annotation.JwtIgnore;
import com.hxh.basic.project.vo.ResultVo;
import com.hxh.basic.project.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author huangxunhui
 * @date Created in 2020/3/6 4:30 下午
 * Utils: Intellij Idea
 * Description: 用户前端控制器
 */
@RestController
@Api(tags = "用户")
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private IUserService userService;


    @JwtIgnore
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultVo login(@Validated @RequestBody AddUserForm userForm, HttpServletResponse response){

        //...参数合法性验证

        //从数据库获取用户信息

        //....用户、密码验证

        //创建token，并将token放在响应头

        //创建token，并将token放在响应头
        UserToken userToken = new UserToken();
        String token = JwtUtil.createToken(JSONObject.toJSONString(userToken));
        response.setHeader(JwtUtil.AUTH_HEADER_KEY, token);

        //定义返回结果
        UserVo result = new UserVo();
        return ResultVoUtil.success(result);


    }

    /**
     * 添加用户
     * @param userForm 表单数据
     * @return 成功或者失败
     * 使用了基于注解的AOP OpLog
     */
    @JwtIgnore
    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    @OpLog(value = "添加用户", level = 8, operationUnit = "UserController", operationType = OperationType.INSERT)
    public ResultVo addUser(@Validated @RequestBody AddUserForm userForm){
        if(userService.addUser(userForm)){
            return ResultVoUtil.success();
        }else{
            return ResultVoUtil.error(ResultEnum.ADD_ERROR);
        }
    }

    /**
     * 获取用户列表
     * @param listUserForm 表单数据
     * @return 用户列表
     */
    @ApiOperation("获取用户列表")
    @GetMapping("/listUser")
    @ApiResponses(
            @ApiResponse(code = 200, message = "操作成功", response = UserVo.class)
    )
    public ResultVo listUser(@Validated ListUserForm listUserForm){
        return ResultVoUtil.success(userService.listUser(listUserForm));
    }

    /**
     * 删除用户
     * @param id 用户编号
     * @return 成功或者失败
     */
    @ApiOperation("删除用户")
    @DeleteMapping("/deleteUser/{id}")
    public ResultVo deleteUser(@PathVariable("id") String id){
        userService.deleteUser(id);
        return ResultVoUtil.success();
    }
}
