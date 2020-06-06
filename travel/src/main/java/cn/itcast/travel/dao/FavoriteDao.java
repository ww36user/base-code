package cn.itcast.travel.dao;

import java.util.List;

public interface FavoriteDao {
    public boolean checkFavorite(int uid,int rid);
    public int findCount(int rid);
    public void addFavorite(int rid,int uid);
    public List<Integer> findRidByUid(int uid);

}
