package cn.itcast.travel.dao.Impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Wei
 * @createTime 2020-05-29 11:15
 */
public class SellerDaoImpl  implements SellerDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public Seller findSellerBySid(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        Seller seller = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
        return seller;
    }
}
