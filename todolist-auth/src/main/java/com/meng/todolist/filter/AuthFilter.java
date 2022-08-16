package com.meng.todolist.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.meng.todolist.common.adapter.AuthConfigAdapter;
import com.meng.todolist.common.handler.HttpHandler;
import com.meng.todolist.common.response.ResponseEnum;
import com.meng.todolist.common.response.ServerResponse;
import com.meng.todolist.modules.auth.service.IAuthenticationService;
import com.meng.todolist.modules.user.models.bo.UserInfoBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName AuthFilter
 * @Description Authorization and filter
 * @Author wangmeng
 * @Date 2022/8/14
 */
@Component
public class AuthFilter implements Filter {

    @Autowired
    private AuthConfigAdapter authConfigAdapter;

    @Autowired
    private HttpHandler httpHandler;

    @Autowired
    private IAuthenticationService authenticationService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        List<String> excludePathPatterns = authConfigAdapter.excludePathPatterns();

        // 如果匹配不需要授权的路径，就不需要校验是否需要授权
        // if matching paths that do not require authorization, let it go directly
        if (CollectionUtil.isNotEmpty(excludePathPatterns)) {
            for (String excludePathPattern : excludePathPatterns) {
                AntPathMatcher pathMatcher = new AntPathMatcher();
                if (pathMatcher.match(excludePathPattern, req.getRequestURI())) {
                    chain.doFilter(req, resp);
                    return;
                }
            }
        }

        // Retrieve "Authorization" from the header
        String accessToken = req.getHeader("Authorization");

        if (StrUtil.isBlank(accessToken)) {
            httpHandler.printServerResponseToWeb(ServerResponse.fail(ResponseEnum.UNAUTHORIZED));
            return;
        }

        ServerResponse<UserInfoBO> userInfoBOServerResponse = authenticationService.getUserByToken(accessToken);
        if (userInfoBOServerResponse == null || !userInfoBOServerResponse.isSuccess() || userInfoBOServerResponse.getData() == null) {
            httpHandler.printServerResponseToWeb(ServerResponse.fail(ResponseEnum.UNAUTHORIZED));
        }

        UserInfoBO userInfoBO = userInfoBOServerResponse.getData();
        try {
            // save the current user to the thread local variable
            AuthUserContext.set(userInfoBO);

            chain.doFilter(req, resp);
        } finally {
            AuthUserContext.clean();
        }

    }
}
