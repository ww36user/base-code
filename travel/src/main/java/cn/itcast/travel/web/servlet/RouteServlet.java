package cn.itcast.travel.web.servlet;

import cn.itcast.travel.dao.Impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.events.StartDocument;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 旅游路线Servlet
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收参数
        String cidStr = request.getParameter("cid");
        String pageSizeStr = request.getParameter("pageSize");
        String currentPageStr = request.getParameter("currentPage");

        //接收rname,线路名称,并处理乱码
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");

        //2.处理参数
        int cid = 0;
        if(cidStr!=null && cidStr.length()>0 && !cidStr.equals("null")){
            cid = Integer.parseInt(cidStr);
        }

        //如果不传递页码,默认为第一页.
        int currentPage = 0;
        if(currentPageStr!=null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage=1;
        }

        //如果不传递每页显示的条数,默认为5条.
        int pageSize = 0;
        if(pageSizeStr!=null && pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }

        //3.调用service,查询PageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize,rname);
        //4.将PageBean对象序列化为json,返回
        writeValue(pb,response);
    }

    /**
     * 根据id查询一个旅游线路的详细信息.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收cid
        String rid = request.getParameter("rid");
        //2.调用service查询route对象
        Route route = routeService.findOne(Integer.parseInt(rid));
        //3.转为json 写回客户端

        writeValue(route,response);
    }


    /**
     * 检查是否收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void checkFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid= request.getParameter("rid");
        User user = ((User)request.getSession().getAttribute("user"));
        int uid = 0;
        if(user!=null){
        uid=user.getUid();
        }
        boolean flag  = favoriteService.checkFavorite(uid, Integer.parseInt(rid));
        writeValue(flag,response);

    }

    /***
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid= request.getParameter("rid");

        User user = ((User)request.getSession().getAttribute("user"));
        if(user==null){
            return;
        }
        int uid = user.getUid();
        favoriteService.addFavorite(Integer.parseInt(rid),uid);
    }


    public void myFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = ((User)request.getSession().getAttribute("user"));
        if(user==null){
            return;
        }
        int uid = user.getUid();
        List<Route> routeList = routeService.myFavorite(uid);
        writeValue(routeList,response);
    }

    public void myFavoritePlus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //uid, currentPage, pageSize
        String pageSizeStr = request.getParameter("pageSize");
        String currentPageStr = request.getParameter("currentPage");
        int uid=7;
        //如果不传递页码,默认为第一页.

        int currentPage = 0;
        if(currentPageStr!=null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage=1;
        }

        //如果不传递每页显示的条数,默认为5条.
        int pageSize = 0;
        if(pageSizeStr!=null && pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 8;
        }
        int start = (currentPage-1)*8;
        PageBean<Route> pb = routeService.myFavoritePlus(uid, currentPage,pageSize,start);
        writeValue(pb,response);
    }

    /*public void myFavoriteB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        List<Route> routeList = routeService.myFavorite(uid);

        //1.接收参数
        String pageSizeStr = request.getParameter("pageSize");
        String currentPageStr = request.getParameter("currentPage");

        //接收rname,线路名称,并处理乱码
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");

        //2.处理参数
        User user = ((User)request.getSession().getAttribute("user"));
        if(user==null){
            return;
        }
        int uid = user.getUid();
        //如果不传递页码,默认为第一页.
        int currentPage = 0;
        if(currentPageStr!=null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage=1;
        }

        //如果不传递每页显示的条数,默认为5条.
        int pageSize = 0;
        if(pageSizeStr!=null && pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 8;
        }

        //pageBean装routeList
        Map<PageBean,List<Route>> pb = routeService.pageQueryMyFavorite(uid, currentPage, pageSize,rname);
        //4.将PageBean对象序列化为json,返回
        writeValue(pb,response);
    }*/
}

