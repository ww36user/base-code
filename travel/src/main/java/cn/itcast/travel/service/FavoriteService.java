package cn.itcast.travel.service;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteService {


    /**
     * 判断是否收藏
     * @param uid
     * @param rid
     * @return
     */
    public boolean checkFavorite(int uid,int rid);
    public  void addFavorite(int rid,int uid);
}
