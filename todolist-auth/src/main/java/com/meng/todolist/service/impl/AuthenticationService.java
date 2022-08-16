package com.meng.todolist.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meng.todolist.common.constant.RedisConstants;
import com.meng.todolist.common.response.ServerResponse;
import com.meng.todolist.common.util.PasswordEncoder;
import com.meng.todolist.mapper.AuthenticationMapper;
import com.meng.todolist.modules.auth.service.IAuthenticationService;
import com.meng.todolist.modules.user.models.bo.UserInfoBO;
import com.meng.todolist.modules.user.models.entity.User;
import com.meng.todolist.modules.user.models.vo.TokenVO;
import com.meng.todolist.util.TokenManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AuthenticationService
 * @Description Authtication service implementation
 * @Author wangmeng
 * @Date 2022/8/13
 */
@Service
public class AuthenticationService extends ServiceImpl<AuthenticationMapper, User> implements IAuthenticationService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AuthenticationMapper authenticationMapper;

    @Override
    public TokenVO storeUserAndGetToken(UserInfoBO userInfoBO) {
        String token = TokenManagement.generateToken();
        // convert the user to the map as a value stored in redis
        Map<String, Object> userMap = BeanUtil.beanToMap(userInfoBO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));

        // login token key of a user in redis
        String tokenKey = RedisConstants.LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        stringRedisTemplate.expire(tokenKey, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
        TokenVO tokenVO = new TokenVO();
        tokenVO.setAccessToken(tokenKey);
        return tokenVO;
    }

    @Override
    public ServerResponse<UserInfoBO> validateUserAndGetToken(String email, String password) {
        if (email == null || "".equals(email)) return ServerResponse.showFailMsg("Email can no be null");
        if (password == null || "".equals(password)) return ServerResponse.showFailMsg("Password can no be null");
        // get the user according to the email, using MyBatis
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", email);
        User user = authenticationMapper.selectOne(userQueryWrapper);
        if (user == null) return ServerResponse.showFailMsg("User does not exist");
        Boolean matches = PasswordEncoder.matches(user.getPassword(), password);
        if (!matches) return ServerResponse.showFailMsg("Email or the password is not correct");
        UserInfoBO userInfoBO = new UserInfoBO();
        userInfoBO.setUserId(user.getId());
        return ServerResponse.success(userInfoBO);
    }

    @Override
    public ServerResponse<UserInfoBO> getUserByToken(String token) {
        String key = RedisConstants.LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);
        if (userMap.isEmpty()) {
            return ServerResponse.showFailMsg("User does not exist");
        }

        UserInfoBO userInfoBO = BeanUtil.fillBeanWithMap(userMap, new UserInfoBO(), false);
        return ServerResponse.success(userInfoBO);
    }
}
