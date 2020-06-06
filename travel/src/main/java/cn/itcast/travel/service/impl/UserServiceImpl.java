package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.Impl.UserDaoImpl;
import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

/**
 * @author Wei
 * @createTime 2020-05-25 15:02
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     */
    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        //2.保存用户信息
        if(u!=null){
            return false;
        }
        //设置激活码,设置激活状态
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userDao.saveUser(user);
        //激活邮件发送,邮件正文
        String content = "<a href = 'http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活黑马旅游网账号!");
        return true;

    }

    /**
     * 激活账户
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        //2.调用dao的修改激活状态的方法
        if(user!=null){
            userDao.updateStatu(user);
            return true;
        }
        return false;
    }

    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user);
    }


}
