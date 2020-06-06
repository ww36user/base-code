package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {
    /**
     * 分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize ,String rname);

    /**
     * 根据id查询返回route对象
     * @param rid
     * @return
     */
    public Route findOne(int rid);
    public List<Route> myFavorite(int uid);

//    public Route findOne(Integer rid, int currentPage, int size);
    PageBean<Route> myFavoritePlus(int uid, int currentPage, int i , int start);
}
