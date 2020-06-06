package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.Impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Wei
 * @createTime 2020-05-27 15:26
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //redis数据库查看是否是第一次访问
        Jedis jedis = JedisUtil.getJedis();
        //sortedSet 排序查询
        HashSet<String> categorys = (HashSet<String>) jedis.zrange("category", 0, -1);
        List<Category> cs = null;
        if(categorys.size()==0 || categorys ==null){
            cs = categoryDao.findAll();
            for (Category c : cs) {
                //遍历想redis中添加分类数据.
                jedis.zadd("category",c.getCid(),c.getCname());
            }
        }
        //如果不为空,将set的数据存入list
        cs = new ArrayList<>();
        categorys = (HashSet<String>) jedis.zrange("category", 0, -1);
        for (String name : categorys) {
            Category category = new Category();
            category.setCname(name);
            category.setCid((int) Double.parseDouble(String.valueOf(jedis.zscore("category",name))));
            cs.add(category);
        }
        return cs;
    }
}
