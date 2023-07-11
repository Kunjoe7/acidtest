package com.servlet;

import com.dao.UserDao;
import com.model.Admin;
import com.model.ActestManager;
import com.model.Student;
import com.util.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "LoginServlet")

public class LoginServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;

    DbUtil dbUtil = new DbUtil();
    UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        String userType = request.getParameter("userType");

        Connection con = null;
        try {
            con=dbUtil.getCon();
            Admin currentAdmin = null;
            ActestManager currentActestManager = null;
            Student currentStudent = null;
            if("admin".equals(userType)) {
                Admin admin = new Admin(userName, password);
                currentAdmin = userDao.Login(con, admin);
                if(currentAdmin == null) {
//                    response.sendRedirect("/ActestManage");
                    request.setAttribute("user", admin);
                    request.setAttribute("currentUserType", "admin");
                    request.setAttribute("error", "用户名或密码错误！");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                } else {
                    if("remember-me".equals(remember)) {
                        rememberMe(userName, password, userType,response);
                    } else {
                        deleteCookie(userName, request, response);
                    }
                    session.setAttribute("currentUserType", "admin");
                    session.setAttribute("currentUser", currentAdmin);
                    request.setAttribute("mainPage", "admin/blank.jsp");
                    request.getRequestDispatcher("/mainAdmin.jsp").forward(request, response);
                }
            } else if("ActestManager".equals(userType)) {
                ActestManager ActestManager = new ActestManager(userName, password);
                currentActestManager = userDao.Login(con, ActestManager);
                if(currentActestManager == null) {
                    request.setAttribute("user", ActestManager);
                    request.setAttribute("currentUserType", "ActestManager");
                    request.setAttribute("error", "用户名或密码错误！");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                } else {
                    if("remember-me".equals(remember)) {
                        rememberMe(userName, password, userType,response);
                    } else {
                        deleteCookie(userName, request, response);
                    }
                    session.setAttribute("currentUserType", "ActestManager");
                    session.setAttribute("currentUser", currentActestManager);
                    request.setAttribute("mainPage", "ActestManager/blank.jsp");
                    request.getRequestDispatcher("mainManager.jsp").forward(request, response);
                }
            } else if("student".equals(userType)) {
                Student student = new Student(userName, password);
                currentStudent = userDao.Login(con, student);
                if(currentStudent == null) {
                    request.setAttribute("user", student);
                    request.setAttribute("currentUserType", "student");
                    request.setAttribute("error", "用户名或密码错误！");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                } else {
                    if("remember-me".equals(remember)) {
                        rememberMe(userName, password, userType,response);
                    } else {
                        deleteCookie(userName, request, response);
                    }
                    session.setAttribute("currentUserType", "student");
                    session.setAttribute("currentUser", currentStudent);
                    request.setAttribute("mainPage", "student/blank.jsp");
                    request.getRequestDispatcher("mainStudent.jsp").forward(request, response);
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void rememberMe(String userName, String password, String userType, HttpServletResponse response) {
        Cookie user = new Cookie("Actestuser", userName+"-"+password+"-"+userType+"-"+"yes");
        user.setMaxAge(1*60*60*24*7);
        response.addCookie(user);
    }

    private void deleteCookie(String userName, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies=request.getCookies();
        for(int i=0;cookies!=null && i<cookies.length;i++){
            if(cookies[i].getName().equals("Actestuser")){
                if(userName.equals(userName=cookies[i].getValue().split("-")[0])) {
                    Cookie cookie = new Cookie(cookies[i].getName(), null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }
}