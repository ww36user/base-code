package cn.itcast.travel.dao.Impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wei
 * @createTime 2020-05-27 20:04
 */
public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid , String rname) {
        String sql = "select count(*) from tab_route where 1=1";
        List params = new ArrayList();
        if(cid!=0){
            sql+=" and cid = ?";
            params.add(cid);
        }
        if(rname!=null && rname.length()>0 && !rname.equals("null")){
            sql+=" and rname like ?";
            params.add("%"+rname+"%");
        }
        return jdbcTemplate.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize ,String rname) {
//      String sql = "select * from tab_route where cid = ? limit ?,?";
        String sql = "select * from tab_route where 1=1";
        List params = new ArrayList();
        if(cid!=0){
            sql+=" and cid = ?";
            params.add(cid);
        }
        if(rname!=null && rname.length()>0 && !rname.equals("null")){
            sql+=" and rname like ?";
            params.add("%"+rname+"%");
        }
        sql+=" limit ?,?";
        params.add(start);
        params.add(pageSize);


        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = " select * from tab_route where rid = ?";
        Route route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
        return route;
    }

    @Override
    public List<Route> findByPageAndrid(Integer rid, int start, int pageSize, String rname) {

        String sql = "select * from tab_route where 1=1";
        List params = new ArrayList();
        if(rid!=0){
            sql+=" and rid = ?";
            params.add(rid);
        }
        if(rname!=null && rname.length()>0 && !rname.equals("null")){
            sql+=" and rname like ?";
            params.add("%"+rname+"%");
        }
        sql+=" limit ?,?";
        params.add(start);
        params.add(pageSize);


        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Route.class),params.toArray());
    }

    @Override
    public List<Route> findByFavoriteByUid(int uid, int start, int pageSize) {
        try {
            String sql = "select * from tab_route a ,tab_favorite b where a.rid=b.rid and uid = ? limit ?,?";
            List<Route> routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class),uid,start,pageSize);
            return routes;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }


    }


}
