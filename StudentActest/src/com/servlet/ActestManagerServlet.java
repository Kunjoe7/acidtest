package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ActestManagerDao;
import com.model.ActestManager;
import com.model.PageBean;
import com.util.DbUtil;
import com.util.StringUtil;

@WebServlet(name = "ActestManagerServlet")
public class ActestManagerServlet extends HttpServlet {
    DbUtil dbUtil = new DbUtil();
    ActestManagerDao ActestManagerDao = new ActestManagerDao();

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
        String s_ActestManagerText = request.getParameter("s_ActestManagerText");
        String searchType = request.getParameter("searchType");
        String page = request.getParameter("page");
        String action = request.getParameter("action");
        ActestManager ActestManager = new ActestManager();
        if("preSave".equals(action)) {
            ActestManagerPreSave(request, response);
            return;
        } else if("save".equals(action)){
            ActestManagerSave(request, response);
            return;
        } else if("delete".equals(action)){
            ActestManagerDelete(request, response);
            return;
        } else
        if("list".equals(action)) {
            if(StringUtil.isNotEmpty(s_ActestManagerText)) {
                if("name".equals(searchType)) {
                    ActestManager.setName(s_ActestManagerText);
                } else if("userName".equals(searchType)) {
                    ActestManager.setUserName(s_ActestManagerText);
                }
            }
            session.removeAttribute("s_ActestManagerText");
            session.removeAttribute("searchType");
            request.setAttribute("s_ActestManagerText", s_ActestManagerText);
            request.setAttribute("searchType", searchType);
        } else if("search".equals(action)){
            if (StringUtil.isNotEmpty(s_ActestManagerText)) {
                if ("name".equals(searchType)) {
                    ActestManager.setName(s_ActestManagerText);
                } else if ("userName".equals(searchType)) {
                    ActestManager.setUserName(s_ActestManagerText);
                }
                session.setAttribute("searchType", searchType);
                session.setAttribute("s_ActestManagerText", s_ActestManagerText);
            } else {
                session.removeAttribute("s_ActestManagerText");
                session.removeAttribute("searchType");
            }
        } else {
            if(StringUtil.isNotEmpty(s_ActestManagerText)) {
                if("name".equals(searchType)) {
                    ActestManager.setName(s_ActestManagerText);
                } else if("userName".equals(searchType)) {
                    ActestManager.setUserName(s_ActestManagerText);
                }
                session.setAttribute("searchType", searchType);
                session.setAttribute("s_ActestManagerText", s_ActestManagerText);
            }
            if(StringUtil.isEmpty(s_ActestManagerText)) {
                Object o1 = session.getAttribute("s_ActestManagerText");
                Object o2 = session.getAttribute("searchType");
                if(o1!=null) {
                    if("name".equals((String)o2)) {
                        ActestManager.setName((String)o1);
                    } else if("userName".equals((String)o2)) {
                        ActestManager.setUserName((String)o1);
                    }
                }
            }
        }
        if(StringUtil.isEmpty(page)) {
            page="1";
        }
        Connection con = null;
        PageBean pageBean = new PageBean(Integer.parseInt(page), 5);
        request.setAttribute("pageSize", pageBean.getPageSize());
        request.setAttribute("page", pageBean.getPage());
        try {
            con=dbUtil.getCon();
            List<ActestManager> ActestManagerList = ActestManagerDao.actestManagerList(con, pageBean, ActestManager);
            int total=ActestManagerDao.actestManagerCount(con, ActestManager);
            String pageCode = this.genPagation(total, Integer.parseInt(page),5);
            request.setAttribute("pageCode", pageCode);
            request.setAttribute("ActestManagerList", ActestManagerList);
            request.setAttribute("mainPage", "admin/ActestManager.jsp");
            request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void ActestManagerDelete(HttpServletRequest request,
                                   HttpServletResponse response) {
        String ActestManagerId = request.getParameter("ActestManagerId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            ActestManagerDao.actestManagerDelete(con, ActestManagerId);
            request.getRequestDispatcher("ActestManager?action=list").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void ActestManagerSave(HttpServletRequest request,
                                 HttpServletResponse response)throws ServletException, IOException {
        String ActestManagerId = request.getParameter("ActestManagerId");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String tel = request.getParameter("tel");
        ActestManager ActestManager = new ActestManager(userName, password, name, sex, tel);
        if(StringUtil.isNotEmpty(ActestManagerId)) {
            ActestManager.setActestManagerId(Integer.parseInt(ActestManagerId));
        }
        Connection con = null;
        try {
            con = dbUtil.getCon();
            int saveNum = 0;
            if(StringUtil.isNotEmpty(ActestManagerId)) {
                saveNum = ActestManagerDao.actestManagerUpdate(con, ActestManager);
            } else if(ActestManagerDao.haveManagerByUser(con, ActestManager.getUserName())){
                request.setAttribute("ActestManager", ActestManager);
                request.setAttribute("error", "该用户名已存在");
                request.setAttribute("mainPage", "admin/ActestManagerSave.jsp");
                request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
                try {
                    dbUtil.closeCon(con);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            } else {
                saveNum = ActestManagerDao.actestManagerAdd(con, ActestManager);
            }
            if(saveNum > 0) {
                request.getRequestDispatcher("ActestManager?action=list").forward(request, response);
            } else {
                request.setAttribute("ActestManager", ActestManager);
                request.setAttribute("error", "保存失败");
                request.setAttribute("mainPage", "ActestManager/ActestManagerSave.jsp");
                request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void ActestManagerPreSave(HttpServletRequest request,
                                    HttpServletResponse response)throws ServletException, IOException {
        String ActestManagerId = request.getParameter("ActestManagerId");
        if(StringUtil.isNotEmpty(ActestManagerId)) {
            Connection con = null;
            try {
                con = dbUtil.getCon();
                ActestManager ActestManager = ActestManagerDao.actestManagerShow(con, ActestManagerId);
                request.setAttribute("ActestManager", ActestManager);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    dbUtil.closeCon(con);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        request.setAttribute("mainPage", "admin/ActestManagerSave.jsp");
        request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
    }

    private String genPagation(int totalNum, int currentPage, int pageSize){
        int totalPage = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
        StringBuffer pageCode = new StringBuffer();
        pageCode.append("<li><a href='ActestManager?page=1'>首页</a></li>");
        if(currentPage==1) {
            pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
        }else {
            pageCode.append("<li><a href='ActestManager?page="+(currentPage-1)+"'>上一页</a></li>");
        }
        for(int i=currentPage-2;i<=currentPage+2;i++) {
            if(i<1||i>totalPage) {
                continue;
            }
            if(i==currentPage) {
                pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
            } else {
                pageCode.append("<li><a href='ActestManager?page="+i+"'>"+i+"</a></li>");
            }
        }
        if(currentPage==totalPage) {
            pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
        } else {
            pageCode.append("<li><a href='ActestManager?page="+(currentPage+1)+"'>下一页</a></li>");
        }
        pageCode.append("<li><a href='ActestManager?page="+totalPage+"'>尾页</a></li>");
        return pageCode.toString();
    }
}
