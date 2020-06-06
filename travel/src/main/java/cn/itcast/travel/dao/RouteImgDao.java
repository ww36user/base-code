package cn.itcast.travel.dao;
import cn.itcast.travel.domain.RouteImg;
import java.util.List;

public interface RouteImgDao {
    /**
     * 根据router的id去查询对应的图片,返回list
     * @param rid
     * @return
     */
    public List<RouteImg> findByRid(int rid);
}
