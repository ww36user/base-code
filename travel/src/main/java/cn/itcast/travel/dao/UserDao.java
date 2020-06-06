package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao  {
    /**注册-
     * 根据用户名查询用户信息
     * @param useranme
     * @return
     */
    public User findByUsername(String useranme);

    /**注册成功
     * 保存用户信息.
     * @param user
     */
    public void saveUser(User user);

    public User findByCode(String code);

    public void updateStatu(User user);

    public User findByUsernameAndPassword(User user);
}
