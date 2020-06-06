package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.Impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.Impl.RouteDaoImpl;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;

import java.util.List;

/**
 * @author Wei
 * @createTime 2020-05-29 14:02
 */
public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();

    /**
     * 判断是否收藏
     * @param uid
     * @param rid
     * @return
     */
    @Override
    public boolean checkFavorite(int uid, int rid) {

        return favoriteDao.checkFavorite(uid,rid);
    }

    @Override
    public void addFavorite(int rid, int uid) {
        favoriteDao.addFavorite(rid,uid);
    }


}
