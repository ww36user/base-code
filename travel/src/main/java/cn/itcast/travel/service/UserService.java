package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * 注册用户.
     * @param user
     */
    public boolean regist(User user);

    public boolean active(String code);

    public User login(User user);
}
