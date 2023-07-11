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

import com.dao.ActestPlaceDao;
import com.model.ActestPlace;
import com.model.ActestManager;
import com.model.PageBean;
import com.util.DbUtil;
import com.util.StringUtil;

@WebServlet(name = "ActestPlaceServlet")
public class ActestPlaceServlet extends HttpServlet {
    DbUtil dbUtil = new DbUtil();
    com.dao.ActestPlaceDao ActestPlaceDao = new ActestPlaceDao();

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
        String s_ActestPlaceName = request.getParameter("s_ActestPlaceName");
        String page = request.getParameter("page");
        String action = request.getParameter("action");
        ActestPlace ActestPlace = new ActestPlace();
        if("preSave".equals(action)) {
            ActestPlacePreSave(request, response);
            return;
        } else if("save".equals(action)){
            ActestPlaceSave(request, response);
            return;
        } else if("delete".equals(action)){
            ActestPlaceDelete(request, response);
            return;
        } else if("manager".equals(action)){
            ActestPlaceManager(request, response);
            return;
        } else if("addManager".equals(action)){
            ActestPlaceAddManager(request, response);
        } else if("move".equals(action)){
            managerMove(request, response);
        } else if("list".equals(action)) {
            if(StringUtil.isNotEmpty(s_ActestPlaceName)) {
                ActestPlace.setActestPlaceName(s_ActestPlaceName);
            }
            session.removeAttribute("s_ActestPlaceName");
            request.setAttribute("s_ActestPlaceName", s_ActestPlaceName);
        } else if("search".equals(action)){
            if(StringUtil.isNotEmpty(s_ActestPlaceName)) {
                ActestPlace.setActestPlaceName(s_ActestPlaceName);
                session.setAttribute("s_ActestPlaceName", s_ActestPlaceName);
            }else {
                session.removeAttribute("s_ActestPlaceName");
            }
        } else {
            if(StringUtil.isNotEmpty(s_ActestPlaceName)) {
                ActestPlace.setActestPlaceName(s_ActestPlaceName);
                session.setAttribute("s_ActestPlaceName", s_ActestPlaceName);
            }
            if(StringUtil.isEmpty(s_ActestPlaceName)) {
                Object o = session.getAttribute("s_ActestPlaceName");
                if(o!=null) {
                    ActestPlace.setActestPlaceName((String)o);
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
            List<ActestPlace> ActestPlaceList = ActestPlaceDao.actestPlaceList(con, pageBean, ActestPlace);
            int total=ActestPlaceDao.ActestPlaceCount(con, ActestPlace);
            String pageCode = this.genPagation(total, Integer.parseInt(page), 5);
            request.setAttribute("pageCode", pageCode);
            request.setAttribute("ActestPlaceList", ActestPlaceList);
            request.setAttribute("mainPage", "admin/ActestPlace.jsp");
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

    private void managerMove(HttpServletRequest request,
                             HttpServletResponse response) {
        String ActestPlaceId = request.getParameter("actestPlaceId");
        String ActestManagerId = request.getParameter("actestManagerId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            ActestPlaceDao.managerUpdateWithId(con, ActestManagerId, "0");
            request.getRequestDispatcher("ActestPlace?action=manager&actestPlaceId="+ActestPlaceId).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ActestPlaceAddManager(HttpServletRequest request,
                                       HttpServletResponse response) {
        String ActestPlaceId = request.getParameter("actestPlaceId");
        String ActestManagerId = request.getParameter("actestManagerId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            ActestPlaceDao.managerUpdateWithId(con, ActestManagerId, ActestPlaceId);
            request.getRequestDispatcher("ActestPlace?action=manager&actestPlaceId="+ActestPlaceId).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ActestPlaceManager(HttpServletRequest request,
                                    HttpServletResponse response) {
        String ActestPlaceId = request.getParameter("actestPlaceId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            List<ActestManager> managerListWithId = ActestPlaceDao.actestManWithPlaceId(con, ActestPlaceId);
            List<ActestManager> managerListToSelect = ActestPlaceDao.actestManWithoutPlace(con);
            request.setAttribute("actestPlaceId", ActestPlaceId);
            request.setAttribute("managerListWithId", managerListWithId);
            request.setAttribute("managerListToSelect", managerListToSelect);
            request.setAttribute("mainPage", "admin/selectManager.jsp");
            request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ActestPlaceDelete(HttpServletRequest request,
                                   HttpServletResponse response) {
        String ActestPlaceId = request.getParameter("actestPlaceId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            if(ActestPlaceDao.existManOrActestWithId(con, ActestPlaceId)) {
                request.setAttribute("error", "核酸地点有阳性或核酸管理员，不能删除该核酸地点");
            } else {
                ActestPlaceDao.actestPlaceDelete(con, ActestPlaceId);
            }
            request.getRequestDispatcher("ActestPlace?action=list").forward(request, response);
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

    private void ActestPlaceSave(HttpServletRequest request,
                                 HttpServletResponse response)throws ServletException, IOException {
        String ActestPlaceId = request.getParameter("ActestPlaceId");
        String ActestPlaceName = request.getParameter("ActestPlaceName");
        String detail = request.getParameter("detail");
        ActestPlace ActestPlace = new ActestPlace(ActestPlaceName, detail);
        if(StringUtil.isNotEmpty(ActestPlaceId)) {
            ActestPlace.setActestPlaceId(Integer.parseInt(ActestPlaceId));
        }
        Connection con = null;
        try {
            con = dbUtil.getCon();
            int saveNum = 0;
            if(StringUtil.isNotEmpty(ActestPlaceId)) {
                saveNum = ActestPlaceDao.actestPlaceUpdate(con, ActestPlace);
            } else {
                saveNum = ActestPlaceDao.actestPlaceAdd(con, ActestPlace);
            }
            if(saveNum > 0) {
                request.getRequestDispatcher("ActestPlace?action=list").forward(request, response);
            } else {
                request.setAttribute("ActestPlace", ActestPlace);
                request.setAttribute("error", "保存失败");
                request.setAttribute("mainPage", "ActestPlace/ActestPlaceSave.jsp");
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

    private void ActestPlacePreSave(HttpServletRequest request,
                                    HttpServletResponse response)throws ServletException, IOException {
        String ActestPlaceId = request.getParameter("ActestPlaceId");
        if(StringUtil.isNotEmpty(ActestPlaceId)) {
            Connection con = null;
            try {
                con = dbUtil.getCon();
                ActestPlace ActestPlace = ActestPlaceDao.actestPlaceShow(con, ActestPlaceId);
                request.setAttribute("ActestPlace", ActestPlace);
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
        request.setAttribute("mainPage", "admin/ActestPlaceSave.jsp");
        request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
    }

    private String genPagation(int totalNum, int currentPage, int pageSize){
        int totalPage = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
        StringBuffer pageCode = new StringBuffer();
        pageCode.append("<li><a href='ActestPlace?page=1'>首页</a></li>");
        if(currentPage==1) {
            pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
        }else {
            pageCode.append("<li><a href='ActestPlace?page="+(currentPage-1)+"'>上一页</a></li>");
        }
        for(int i=currentPage-2;i<=currentPage+2;i++) {
            if(i<1||i>totalPage) {
                continue;
            }
            if(i==currentPage) {
                pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
            } else {
                pageCode.append("<li><a href='ActestPlace?page="+i+"'>"+i+"</a></li>");
            }
        }
        if(currentPage==totalPage) {
            pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
        } else {
            pageCode.append("<li><a href='ActestPlace?page="+(currentPage+1)+"'>下一页</a></li>");
        }
        pageCode.append("<li><a href='ActestPlace?page="+totalPage+"'>尾页</a></li>");
        return pageCode.toString();
    }
}
