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
import com.dao.StudentDao;
import com.model.ActestManager;
import com.model.PageBean;
import com.model.Student;
import com.util.DbUtil;
import com.util.StringUtil;

@WebServlet(name = "StudentServlet")
public class StudentServlet extends HttpServlet {
    DbUtil dbUtil = new DbUtil();
    StudentDao studentDao = new StudentDao();

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
        Object currentUserType = session.getAttribute("currentUserType");
        String s_studentText = request.getParameter("s_studentText");
        String ActestPlaceId = request.getParameter("PlaceToSelect");
        String searchType = request.getParameter("searchType");
        String page = request.getParameter("page");
        String action = request.getParameter("action");
        Student student = new Student();
        if("preSave".equals(action)) {
            studentPreSave(request, response);
            return;
        } else if("save".equals(action)){
            studentSave(request, response);
            return;
        } else if("delete".equals(action)){
            studentDelete(request, response);
            return;
        } else if("list".equals(action)) {
            if(StringUtil.isNotEmpty(s_studentText)) {
                if("name".equals(searchType)) {
                    student.setName(s_studentText);
                } else if("number".equals(searchType)) {
                    student.setStuNumber(s_studentText);
                } else if("Actest".equals(searchType)) {
                    student.setActestStatus(s_studentText);
                }
            }
            if(StringUtil.isNotEmpty(ActestPlaceId)) {
                student.setActestPlaceId(Integer.parseInt(ActestPlaceId));
            }
            session.removeAttribute("s_studentText");
            session.removeAttribute("searchType");
            session.removeAttribute("PlaceToSelect");
            request.setAttribute("s_studentText", s_studentText);
            request.setAttribute("searchType", searchType);
            request.setAttribute("PlaceToSelect", ActestPlaceId);
        } else if("search".equals(action)){
            if(StringUtil.isNotEmpty(s_studentText)) {
                if("name".equals(searchType)) {
                    student.setName(s_studentText);
                } else if("number".equals(searchType)) {
                    student.setStuNumber(s_studentText);
                } else if("Actest".equals(searchType)) {
                    student.setActestStatus(s_studentText);
                }
                session.setAttribute("s_studentText", s_studentText);
                session.setAttribute("searchType", searchType);
            } else {
                session.removeAttribute("s_studentText");
                session.removeAttribute("searchType");
            }
            if(StringUtil.isNotEmpty(ActestPlaceId)) {
                student.setActestPlaceId(Integer.parseInt(ActestPlaceId));
                session.setAttribute("PlaceToSelect", ActestPlaceId);
            }else {
                session.removeAttribute("PlaceToSelect");
            }
        } else {
            if("admin".equals((String)currentUserType)) {
                if(StringUtil.isNotEmpty(s_studentText)) {
                    if("name".equals(searchType)) {
                        student.setName(s_studentText);
                    } else if("number".equals(searchType)) {
                        student.setStuNumber(s_studentText);
                    } else if("Actest".equals(searchType)) {
                        student.setActestStatus(s_studentText);
                    }
                    session.setAttribute("s_studentText", s_studentText);
                    session.setAttribute("searchType", searchType);
                }
                if(StringUtil.isNotEmpty(ActestPlaceId)) {
                    student.setActestPlaceId(Integer.parseInt(ActestPlaceId));
                    session.setAttribute("PlaceToSelect", ActestPlaceId);
                }
                if(StringUtil.isEmpty(s_studentText) && StringUtil.isEmpty(ActestPlaceId)) {
                    Object o1 = session.getAttribute("s_studentText");
                    Object o2 = session.getAttribute("searchType");
                    Object o3 = session.getAttribute("PlaceToSelect");
                    if(o1!=null) {
                        if("name".equals((String)o2)) {
                            student.setName((String)o1);
                        } else if("number".equals((String)o2)) {
                            student.setStuNumber((String)o1);
                        } else if("Actest".equals((String)o2)) {
                            student.setActestStatus((String)o1);
                        }
                    }
                    if(o3 != null) {
                        student.setActestPlaceId(Integer.parseInt((String)o3));
                    }
                }
            } else if("ActestManager".equals((String)currentUserType)) {
                if(StringUtil.isNotEmpty(s_studentText)) {
                    if("name".equals(searchType)) {
                        student.setName(s_studentText);
                    } else if("number".equals(searchType)) {
                        student.setStuNumber(s_studentText);
                    } else if("Actest".equals(searchType)) {
                        student.setActestStatus(s_studentText);
                    }
                    session.setAttribute("s_studentText", s_studentText);
                    session.setAttribute("searchType", searchType);
                }
                if(StringUtil.isEmpty(s_studentText)) {
                    Object o1 = session.getAttribute("s_studentText");
                    Object o2 = session.getAttribute("searchType");
                    if(o1!=null) {
                        if("name".equals((String)o2)) {
                            student.setName((String)o1);
                        } else if("number".equals((String)o2)) {
                            student.setStuNumber((String)o1);
                        } else if("Actest".equals((String)o2)) {
                            student.setActestStatus((String)o1);
                        }
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
            if("admin".equals((String)currentUserType)) {
                List<Student> studentList = studentDao.studentList(con, pageBean,student);
                int total=studentDao.studentCount(con, student);
                String pageCode = this.genPagation(total, Integer.parseInt(page),5);
                request.setAttribute("pageCode", pageCode);
                request.setAttribute("ActestPlaceList", studentDao.actestPlaceList(con));
                request.setAttribute("studentList", studentList);
                request.setAttribute("mainPage", "admin/student.jsp");
                request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
            } else if("ActestManager".equals((String)currentUserType)) {
                ActestManager manager = (ActestManager)(session.getAttribute("currentUser"));
                int PlaceId = manager.getActestPlaceId();
                String PlaceName = ActestPlaceDao.actestPlaceName(con, PlaceId);
                List<Student> studentList = studentDao.studentListWithPlace(con,pageBean, student, PlaceId);
                int total=studentDao.studentCount(con, student);
                String pageCode = this.genPagation(total, Integer.parseInt(page),5);
                request.setAttribute("pageCode", pageCode);
                request.setAttribute("ActestPlaceName", PlaceName);
                request.setAttribute("studentList", studentList);
                request.setAttribute("mainPage", "ActestManager/student.jsp");
                request.getRequestDispatcher("mainManager.jsp").forward(request, response);
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

    private void studentDelete(HttpServletRequest request,
                               HttpServletResponse response) {
        String studentId = request.getParameter("studentId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            studentDao.studentDelete(con, studentId);
            request.getRequestDispatcher("student?action=list").forward(request, response);
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

    private void studentSave(HttpServletRequest request,
                             HttpServletResponse response)throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String ActestPlaceId = request.getParameter("actestPlaceId");
        String ActestStatus = request.getParameter("actestStatus");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String tel = request.getParameter("tel");
        Student student = new Student(userName, password, Integer.parseInt(ActestPlaceId), ActestStatus, name, sex, tel);
        if(StringUtil.isNotEmpty(studentId)) {
            student.setStudentId(Integer.parseInt(studentId));
        }
        Connection con = null;
        try {
            con = dbUtil.getCon();
            int saveNum = 0;
            if(StringUtil.isNotEmpty(studentId)) {
                saveNum = studentDao.studentUpdate(con, student);
            } else if(studentDao.haveNameByNumber(con, student.getStuNumber())){
                request.setAttribute("student", student);
                request.setAttribute("error", "该学号已存在");
                request.setAttribute("mainPage", "admin/studentSave.jsp");
                request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
                try {
                    dbUtil.closeCon(con);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            } else {
                saveNum = studentDao.studentAdd(con, student);
            }
            if(saveNum > 0) {
                request.getRequestDispatcher("student?action=list").forward(request, response);
            } else {
                request.setAttribute("student", student);
                request.setAttribute("error", "保存失败");
                request.setAttribute("mainPage", "admin/studentSave.jsp");
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

    private void studentPreSave(HttpServletRequest request,
                                HttpServletResponse response)throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            request.setAttribute("ActestPlaceList", studentDao.actestPlaceList(con));
            if (StringUtil.isNotEmpty(studentId)) {
                Student student = studentDao.studentShow(con, studentId);
                request.setAttribute("student", student);
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
        request.setAttribute("mainPage", "admin/studentSave.jsp");
        request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
    }
    private String genPagation(int totalNum, int currentPage, int pageSize){
        int totalPage = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
        StringBuffer pageCode = new StringBuffer();
        pageCode.append("<li><a href='student?page=1'>首页</a></li>");
        if(currentPage==1) {
            pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
        }else {
            pageCode.append("<li><a href='student?page="+(currentPage-1)+"'>上一页</a></li>");
        }
        for(int i=currentPage-2;i<=currentPage+2;i++) {
            if(i<1||i>totalPage) {
                continue;
            }
            if(i==currentPage) {
                pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
            } else {
                pageCode.append("<li><a href='student?page="+i+"'>"+i+"</a></li>");
            }
        }
        if(currentPage==totalPage) {
            pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
        } else {
            pageCode.append("<li><a href='student?page="+(currentPage+1)+"'>下一页</a></li>");
        }
        pageCode.append("<li><a href='student?page="+totalPage+"'>尾页</a></li>");
        return pageCode.toString();
    }
}
