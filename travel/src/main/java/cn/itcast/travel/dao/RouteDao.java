package cn.itcast.travel.dao;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import com.sun.javafx.logging.PulseLogger;

import java.util.List;

public interface RouteDao {
    /**
     * 根据cid查询总记录数
     * @param cid
     * @return
     */
    public int findTotalCount(int cid,String rname);

    /**
     * 查询总记录数
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    public List<Route> findByPage(int cid,int start,int pageSize,String rname);

    /**
     * 根据cid查询,返回route
     * @param rid
     * @return
     */
    public Route findOne(int rid);

    List<Route> findByPageAndrid(Integer integer, int start, int pageSize, String rname);

    public List<Route> findByFavoriteByUid(int uid,int start,int pageSize);
}
