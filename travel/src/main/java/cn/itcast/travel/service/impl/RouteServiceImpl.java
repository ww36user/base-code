package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.Impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.Impl.RouteDaoImpl;
import cn.itcast.travel.dao.Impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.Impl.SellerDaoImpl;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.*;

/**
 * @author Wei
 * @createTime 2020-05-27 20:16
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerdao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize ,String rname) {////        cid=5&currentPage=3&rname=null
        //封装PageBean
        PageBean<Route> pb = new PageBean<>();

        //设置当前页面
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);

        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);

        //设置当前页显示的集合.
        int start = (currentPage-1)*pageSize;//开始的记录数
        List<Route> routeList = routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(routeList);

        //设置总页数
        int totalPage =totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    /**
     * 根据rid查询,返回rouote对象
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        //1.根据rid去route表中查询route对象
        Route route = routeDao.findOne(rid);
        //2.根据route的rid 查询图片的信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(rid);
        route.setRouteImgList(routeImgList);
        //3.根据sid查询商家信息
        Seller seller = sellerdao.findSellerBySid(route.getSid());
        route.setSeller(seller);
        //4.查询收藏次数
        int  count = favoriteDao.findCount(rid);
        route.setCount(count);

        return route;
    }

    @Override
    public List<Route> myFavorite(int uid) {
            List<Route> routeList = new ArrayList<>();
            //1.调用favoriteDao通过uid 找到此用户的所有rid --线路id
            List<Integer> ridList = favoriteDao.findRidByUid(uid);
            //2.调用 routeDao 通过 rid 查询出route
            for (Integer rid : ridList) {
                Route route = new RouteServiceImpl().findOne(rid);
                routeList.add(route);
            }
            return routeList;
        }

//    @Override
    public Map<PageBean, List<Route>> pageQueryMyFavorite(int uid, int currentPage, int pageSize, String rname) {
        //封装PageBean-装list 集合,list集合装route集合
        Map<PageBean, List<Route>> pageBeanMap= new HashMap<>();
        List<Integer> integers = favoriteDao.findRidByUid(uid);
        for (int i = 0; i <integers.size() ; i++) {
            //几次几个list<route>
            PageBean pb = new PageBean();
            //设置当前页面
            pb.setCurrentPage(currentPage);
            //设置每页显示条数
            pb.setPageSize(pageSize);
            //设置总记录数
            int totalCount = integers.size();
            pb.setTotalCount(totalCount);
            //设置总页数
            int totalPage =totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
            pb.setTotalPage(totalPage);
            //设置当前页显示的集合.
            int start = (currentPage-1)*pageSize;//开始的记录数
            List<Route> routeList = routeDao.findByPageAndrid(integers.get(i),start,pageSize,rname);
            pb.setList(routeList);
            pageBeanMap.put(pb,routeList);
        }
      /*
        private int totalPage; //页数
        private List<T> list; //每页显示的数据集合*/
        return pageBeanMap;
    }

    @Override
    public PageBean<Route> myFavoritePlus(int uid, int currentPage, int pageSize, int start ) {
        PageBean<Route> pb = new PageBean<>();

        //设置当前页面
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);

        //设置总记录数
        int totalCount = favoriteDao.findRidByUid(uid).size();
        pb.setTotalCount(totalCount);

        //设置当前页显示的集合.

        List<Route> routeList = routeDao.findByFavoriteByUid(uid, start, pageSize);
        pb.setList(routeList);

        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);


        return  pb;
/*
//        return pb;
        List<Route> routeList = new LinkedList<>();
        //1.调用favoriteDao通过uid 找到此用户的所有rid --线路id
        List<Integer> ridList = favoriteDao.findRidByUid(uid);
        //2.调用 routeDao 通过 rid 查询出route
        int i=0;
        for (Integer rid : ridList) {
            Route route = new RouteServiceImpl().findOne(rid);

            if(i++<(currentPage-1)*8){
                continue;
            }
            routeList.add(route);

            if(routeList.size()==8){
                break;
            }
        }

        return routeList;

    }
*/
    }
}
