package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();
    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();
        ObjectMapper mapper = new ObjectMapper();
        //验证码校验
        //方便注册---忽略
        /*String form_code = request.getParameter("check");
        HttpSession session = request.getSession();
        String real_code = (String) session.getAttribute("CHECKCODE_SERVER");
        //保证验证码只使用一次,移除session属性
        session.removeAttribute("CHECKCODE_SERVER");
        if(!form_code.equalsIgnoreCase(real_code) || form_code==null){
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
            response.setContentType("application/json;charset=utf-8");
            String json = mapper.writeValueAsString(info);
            response.getWriter().write(json);;
            return;
        }*/

        //1,获取数据并封装对象.
        Map<String, String[]> UserMap = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, UserMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //2.调用service完成注册
        boolean flag = userService.regist(user);

        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }
        //将info对象序列化为json

        String json = mapper.writeValueAsString(info);

        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        ;

    }

    /**
     * 登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //封装
        Map<String, String[]> userMap = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, userMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //调用service查询
        User u = userService.login(user);
        //判断用户名和密码是否正确
        ResultInfo info = new ResultInfo();
        ObjectMapper mapper = new ObjectMapper();
        HttpSession session = request.getSession();
        String real_code = (String) session.getAttribute("check");
        response.setContentType("application/json;charset=utf-8");
        if (real_code != null && !real_code.equalsIgnoreCase(request.getParameter("check"))) {
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
            mapper.writeValue(response.getOutputStream(), info);
            return;
        }
        if (u == null) {

            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误!");
        }
        if (u != null && !"Y".equals(u.getStatus())) {
            //用户尚未激活
            info.setErrorMsg("用户未激活!请登录邮箱激活");
            info.setFlag(false);
            mapper.writeValue(response.getOutputStream(), info);
            return;
        }
        if (u != null && "Y".equals(u.getStatus())) {
            info.setFlag(true);
            request.getSession().setAttribute("user", u);
        }

        mapper.writeValue(response.getOutputStream(), info);
    }

    /**
     * 查询一个用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取登录用户
        User user = (User) request.getSession().getAttribute("user");
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);*/
        writeValue(user,response);
    }

    /**
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //session自毁
        request.getSession().invalidate();
        //2,跳转页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 激活功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code != null){
            boolean flag = userService.active(code);
            String msg = null;
            if(flag){
                msg = "激活成功,请<a href = 'login.html'>登录</a>";
            }else{
                msg = "激活码错误";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }




}