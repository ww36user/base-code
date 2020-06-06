package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryServiceImpl();
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categoryList = categoryService.findAll();
        writeValue(categoryList,response);
    }








    public void add2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Add...");
    }
    public void add3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Add...");
    }
    public void add4(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Add...");
    }



}
