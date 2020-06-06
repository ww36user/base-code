package cn.itcast.travel.dao.Impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * @author Wei
 * @createTime 2020-05-29 14:00
 */
public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 判断是否收藏
     * @param uid
     * @param rid
     * @return
     */
    @Override
    public boolean checkFavorite(int uid, int rid) {
        List<Favorite> favorites = null;
        try{
            String sql = "select * from tab_favorite where uid = ? and rid = ? ";
            favorites = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Favorite.class), uid, rid);
        }catch (Exception e){
        }
        if(favorites==null || favorites.size()==0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public int findCount(int rid) {

        String sql = "select count(*) from tab_favorite where rid = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void addFavorite(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        jdbcTemplate.update(sql,rid,new Date(),uid);

    }

    @Override
    public List<Integer> findRidByUid(int uid) {
        try {
            String sql = "select rid from tab_favorite where uid = ?";
            List<Integer> list = jdbcTemplate.queryForList(sql,Integer.class,uid);
            return list;
        } catch (DataAccessException e) {
                throw new RuntimeException("sql错辣!");
            }
        }
}
