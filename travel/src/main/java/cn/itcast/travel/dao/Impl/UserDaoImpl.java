package cn.itcast.travel.dao.Impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Wei
 * @createTime 2020-05-25 15:04
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(String username) {
        User user = null;

        try {
            String sql = "select * from tab_user where username=?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (DataAccessException e) {
        }

        return user;
    }

    @Override
    public void saveUser(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex," +
                "telephone,email,status,code) value(?,?,?,?,?,?,?,?,?)";
        Object[] params = {user.getUsername(), user.getPassword(), user.getName(),
                user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(),
                user.getStatus(),user.getCode()};

        jdbcTemplate.update(sql, params);
    }

    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
            return user;
        } catch (DataAccessException e) {
        }
        return user;
    }

    @Override
    public void updateStatu(User user) {
        String sql = "update tab_user set status='Y' where uid = ? ";
        jdbcTemplate.update(sql,user.getUid());
    }

    @Override
    public User findByUsernameAndPassword(User user) {
        User u = null;
        try {
            String sql = "select * from tab_user where username=? and password=?";
            u = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), user.getUsername(),user.getPassword());
          } catch (DataAccessException e) {

          }
        return u;
    }
}
