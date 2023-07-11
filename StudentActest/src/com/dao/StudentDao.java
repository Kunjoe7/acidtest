package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import  com.model.PageBean;
import com.model.ActestPlace;
import com.model.Student;
import com.util.StringUtil;


public class StudentDao {
	public List<Student> studentList(Connection con, PageBean pageBean, Student s_student)throws Exception {
		List<Student> studentList = new ArrayList<Student>();
		StringBuffer sb = new StringBuffer("select * from t_student t1");
		if(StringUtil.isNotEmpty(s_student.getName())) {
			sb.append(" and t1.name like '%"+s_student.getName()+"%'");
		} else if(StringUtil.isNotEmpty(s_student.getStuNumber())) {
			sb.append(" and t1.stuNum like '%"+s_student.getStuNumber()+"%'");
		} else if(StringUtil.isNotEmpty(s_student.getActestStatus())) {
			sb.append(" and t1.actestStatus like '%"+s_student.getActestStatus()+"%'");
		}
		if(s_student.getActestPlaceId()!=0) {
			sb.append(" and t1.actestPlaceId="+s_student.getActestPlaceId());
		}
		if(pageBean != null) {
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			Student student=new Student();
			student.setStudentId(rs.getInt("studentId"));
			int actestPlaceId = rs.getInt("actestPlaceId");
			student.setActestPlaceId(actestPlaceId);
			student.setActestPlaceName(ActestPlaceDao.actestPlaceName(con, actestPlaceId));
			student.setActestStatus(rs.getString("actestStatus"));
			student.setName(rs.getString("name"));
			student.setSex(rs.getString("sex"));
			student.setStuNumber(rs.getString("stuNum"));
			student.setTel(rs.getString("tel"));
			student.setPassword(rs.getString("password"));
			studentList.add(student);
		}
		return studentList;
	}
    public int StudentCount(Connection con, Student s_student)throws Exception {
        StringBuffer sb = new StringBuffer("select count(*) as total from t_student t1");
        if(StringUtil.isNotEmpty(s_student.getName())) {
            sb.append(" where t1.name like '%"+s_student.getName()+"%'");
        } else if(StringUtil.isNotEmpty(s_student.getUserName())) {
            sb.append(" where t1.userName like '%"+s_student.getUserName()+"%'");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString());
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            return rs.getInt("total");
        } else {
            return 0;
        }
    }


    public static Student getNameById(Connection con, String studentNumber, int actestPlaceId)throws Exception {
        String sql = "select * from t_student t1 where t1.stuNum=? and t1.actestPlaceId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, studentNumber);
        pstmt.setInt(2, actestPlaceId);
        ResultSet rs=pstmt.executeQuery();
        Student student = new Student();
        if(rs.next()) {
            student.setName(rs.getString("name"));
            student.setActestPlaceId(rs.getInt("actestPlaceId"));
            student.setActestStatus(rs.getString("actestStatus"));
        }
        return student;
    }

    public boolean haveNameByNumber(Connection con, String studentNumber)throws Exception {
        String sql = "select * from t_student t1 where t1.stuNum=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, studentNumber);
        ResultSet rs=pstmt.executeQuery();
        Student student = new Student();
        if(rs.next()) {
            student.setName(rs.getString("name"));
            student.setActestPlaceId(rs.getInt("actestPlaceId"));
            student.setActestStatus(rs.getString("actestStatus"));
            return true;
        }
        return false;
    }

    public List<Student> studentListWithPlace(Connection con, PageBean pageBean, Student s_student, int PlaceId)throws Exception {
        List<Student> studentList = new ArrayList<Student>();
        StringBuffer sb = new StringBuffer("select * from t_student t1");
        if(StringUtil.isNotEmpty(s_student.getName())) {
            sb.append(" and t1.name like '%"+s_student.getName()+"%'");
        } else if(StringUtil.isNotEmpty(s_student.getStuNumber())) {
            sb.append(" and t1.stuNum like '%"+s_student.getStuNumber()+"%'");
        } else if(StringUtil.isNotEmpty(s_student.getActestStatus())) {
            sb.append(" and t1.actestStatus like '%"+s_student.getActestStatus()+"%'");
        }
        sb.append(" and t1.actestPlaceId="+PlaceId);
        if(pageBean != null) {
            sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            Student student=new Student();
            student.setStudentId(rs.getInt("studentId"));
            int actestPlaceId = rs.getInt("actestPlaceId");
            student.setActestPlaceId(actestPlaceId);
            student.setActestPlaceName(ActestPlaceDao.actestPlaceName(con, actestPlaceId));
            student.setActestStatus(rs.getString("actestStatus"));
            student.setName(rs.getString("name"));
            student.setSex(rs.getString("sex"));
            student.setStuNumber(rs.getString("stuNum"));
            student.setTel(rs.getString("tel"));
            student.setPassword(rs.getString("password"));
            studentList.add(student);
        }
        return studentList;
    }

    public List<ActestPlace> actestPlaceList(Connection con)throws Exception {
        List<ActestPlace> actestPlaceList = new ArrayList<ActestPlace>();
        String sql = "select * from t_actestPlace";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            ActestPlace actestPlace=new ActestPlace();
            actestPlace.setActestPlaceId(rs.getInt("actestPlaceId"));
            actestPlace.setActestPlaceName(rs.getString("actestPlaceName"));
            actestPlace.setDetail(rs.getString("actestPlaceDetail"));
            actestPlaceList.add(actestPlace);
        }
        return actestPlaceList;
    }

    public int studentCount(Connection con, Student s_student)throws Exception {
        StringBuffer sb = new StringBuffer("select count(*) as total from t_student t1");
        if(StringUtil.isNotEmpty(s_student.getName())) {
            sb.append(" and t1.name like '%"+s_student.getName()+"%'");
        } else if(StringUtil.isNotEmpty(s_student.getStuNumber())) {
            sb.append(" and t1.stuNum like '%"+s_student.getStuNumber()+"%'");
        } else if(StringUtil.isNotEmpty(s_student.getActestStatus())) {
            sb.append(" and t1.actestStatus like '%"+s_student.getActestStatus()+"%'");
        }
        if(s_student.getActestPlaceId()!=0) {
            sb.append(" and t1.actestPlaceId="+s_student.getActestPlaceId());
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            return rs.getInt("total");
        } else {
            return 0;
        }
    }

    public Student studentShow(Connection con, String studentId)throws Exception {
        String sql = "select * from t_student t1 where t1.studentId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, studentId);
        ResultSet rs=pstmt.executeQuery();
        Student student = new Student();
        if(rs.next()) {
            student.setStudentId(rs.getInt("studentId"));
            int actestPlaceId = rs.getInt("actestPlaceId");
            student.setActestPlaceId(actestPlaceId);
            student.setActestPlaceName(ActestPlaceDao.actestPlaceName(con, actestPlaceId));
            student.setActestStatus(rs.getString("actestStatus"));
            student.setName(rs.getString("name"));
            student.setSex(rs.getString("sex"));
            student.setStuNumber(rs.getString("stuNum"));
            student.setTel(rs.getString("tel"));
            student.setPassword(rs.getString("password"));
        }
        return student;
    }

    public int studentAdd(Connection con, Student student)throws Exception {
        String sql = "insert into t_student values(null,?,?,?,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, student.getStuNumber());
        pstmt.setString(2, student.getPassword());
        pstmt.setString(3, student.getName());
        pstmt.setInt(4, student.getActestPlaceId());
        pstmt.setString(5, student.getActestStatus());
        pstmt.setString(6, student.getSex());
        pstmt.setString(7, student.getTel());
        return pstmt.executeUpdate();
    }

    public int studentDelete(Connection con, String studentId)throws Exception {
        String sql = "delete from t_student where studentId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, studentId);
        return pstmt.executeUpdate();
    }

    public int studentUpdate(Connection con, Student student)throws Exception {
        String sql = "update t_student set stuNum=?,password=?,name=?,actestPlaceId=?,actestStatus=?,sex=?,tel=? where studentId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, student.getStuNumber());
        pstmt.setString(2, student.getPassword());
        pstmt.setString(3, student.getName());
        pstmt.setInt(4, student.getActestPlaceId());
        pstmt.setString(5, student.getActestStatus());
        pstmt.setString(6, student.getSex());
        pstmt.setString(7, student.getTel());
        pstmt.setInt(8, student.getStudentId());
        return pstmt.executeUpdate();
    }


}
