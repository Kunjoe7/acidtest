package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.model.Admin;
import com.model.ActestManager;
import com.model.Student;

public class UserDao {

    public Admin Login(Connection con, Admin admin)throws Exception {
        Admin resultAdmin = null;
        String sql = "select * from t_admin where userName=? and password=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, admin.getUserName());
        pstmt.setString(2, admin.getPassword());
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            resultAdmin = new Admin();
            resultAdmin.setAdminId(rs.getInt("adminId"));
            resultAdmin.setUserName(rs.getString("userName"));
            resultAdmin.setPassword(rs.getString("password"));
            resultAdmin.setName(rs.getString("name"));
            resultAdmin.setSex(rs.getString("sex"));
            resultAdmin.setTel(rs.getString("tel"));
        }
        return resultAdmin;
    }

    public ActestManager Login(Connection con, ActestManager actestManager)throws Exception {
        ActestManager resultActestManager = null;
        String sql = "select * from t_actestmanager where userName=? and password=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, actestManager.getUserName());
        pstmt.setString(2, actestManager.getPassword());
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            resultActestManager = new ActestManager();
            resultActestManager.setActestManagerId(rs.getInt("actestManId"));
            resultActestManager.setUserName(rs.getString("userName"));
            resultActestManager.setPassword(rs.getString("password"));
            resultActestManager.setActestPlaceId(rs.getInt("actestPlaceId"));
            resultActestManager.setName(rs.getString("name"));
            resultActestManager.setSex(rs.getString("sex"));
            resultActestManager.setTel(rs.getString("tel"));
        }
        return resultActestManager;
    }

    public Student Login(Connection con, Student student)throws Exception {
        Student resultStudent = null;
        String sql = "select * from t_student where stuNum=? and password=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, student.getStuNumber());
        pstmt.setString(2, student.getPassword());
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            resultStudent = new Student();
            resultStudent.setStudentId(rs.getInt("studentId"));
            resultStudent.setStuNumber(rs.getString("stuNum"));
            resultStudent.setPassword(rs.getString("password"));
            int actestPlaceId = rs.getInt("actestPlaceId");
            resultStudent.setActestPlaceId(actestPlaceId);
            resultStudent.setActestPlaceName(ActestPlaceDao.actestPlaceName(con, actestPlaceId));
            resultStudent.setActestStatus(rs.getString("actestStatus"));
            resultStudent.setName(rs.getString("name"));
            resultStudent.setSex(rs.getString("sex"));
            resultStudent.setTel(rs.getString("tel"));
        }
        return resultStudent;
    }

    public int adminUpdate(Connection con, int adminId, String password)throws Exception {
        String sql = "update t_admin set password=? where adminId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, password);
        pstmt.setInt(2, adminId);
        return pstmt.executeUpdate();
    }

    public int managerUpdate(Connection con, int managerId, String password)throws Exception {
        String sql = "update t_actestmanager set password=? where actestManId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, password);
        pstmt.setInt(2, managerId);
        return pstmt.executeUpdate();
    }

    public int studentUpdate(Connection con, int studentId, String password)throws Exception {
        String sql = "update t_student set password=? where studentId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, password);
        pstmt.setInt(2, studentId);
        return pstmt.executeUpdate();
    }

}
