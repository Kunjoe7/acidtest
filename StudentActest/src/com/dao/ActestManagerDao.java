package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.ActestManager;
import com.model.PageBean;
import com.util.StringUtil;

public class ActestManagerDao {

    public List<ActestManager> actestManagerList(Connection con, PageBean pageBean, ActestManager s_actestManager)throws Exception {
        List<ActestManager> actestManagerList = new ArrayList<ActestManager>();
        StringBuffer sb = new StringBuffer("SELECT * FROM t_actestManager t1");
        if(StringUtil.isNotEmpty(s_actestManager.getName())) {
            sb.append(" where t1.name like '%"+s_actestManager.getName()+"%'");
        } else if(StringUtil.isNotEmpty(s_actestManager.getUserName())) {
            sb.append(" where t1.userName like '%"+s_actestManager.getUserName()+"%'");
        }
        if(pageBean != null) {
            sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString());
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            ActestManager actestManager=new ActestManager();
            actestManager.setActestManagerId(rs.getInt("actestManId"));
            int actestPlaceId = rs.getInt("actestPlaceId");
            actestManager.setActestPlaceId(actestPlaceId);
            actestManager.setActestPlaceName(ActestPlaceDao.actestPlaceName(con, actestPlaceId));
            actestManager.setName(rs.getString("name"));
            actestManager.setSex(rs.getString("sex"));
            actestManager.setUserName(rs.getString("userName"));
            actestManager.setTel(rs.getString("tel"));
            actestManager.setPassword(rs.getString("password"));
            actestManagerList.add(actestManager);
        }
        return actestManagerList;
    }

    public int actestManagerCount(Connection con, ActestManager s_actestManager)throws Exception {
        StringBuffer sb = new StringBuffer("select count(*) as total from t_actestManager t1");
        if(StringUtil.isNotEmpty(s_actestManager.getName())) {
            sb.append(" where t1.name like '%"+s_actestManager.getName()+"%'");
        } else if(StringUtil.isNotEmpty(s_actestManager.getUserName())) {
            sb.append(" where t1.userName like '%"+s_actestManager.getUserName()+"%'");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString());
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            return rs.getInt("total");
        } else {
            return 0;
        }
    }

    public ActestManager actestManagerShow(Connection con, String actestManagerId)throws Exception {
        String sql = "select * from t_actestManager t1 where t1.actestManId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, actestManagerId);
        ResultSet rs=pstmt.executeQuery();
        ActestManager actestManager = new ActestManager();
        if(rs.next()) {
            actestManager.setActestManagerId(rs.getInt("actestManId"));
            actestManager.setActestPlaceId(rs.getInt("actestPlaceId"));
            actestManager.setName(rs.getString("name"));
            actestManager.setSex(rs.getString("sex"));
            actestManager.setUserName(rs.getString("userName"));
            actestManager.setTel(rs.getString("tel"));
            actestManager.setPassword(rs.getString("password"));
        }
        return actestManager;
    }

    public int actestManagerAdd(Connection con, ActestManager actestManager)throws Exception {
        String sql = "insert into t_actestManager values(null,?,?,null,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, actestManager.getUserName());
        pstmt.setString(2, actestManager.getPassword());
        pstmt.setString(3, actestManager.getName());
        pstmt.setString(4, actestManager.getSex());
        pstmt.setString(5, actestManager.getTel());
        return pstmt.executeUpdate();
    }

    public int actestManagerDelete(Connection con, String actestManagerId)throws Exception {
        String sql = "delete from t_actestManager where actestManId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, actestManagerId);
        return pstmt.executeUpdate();
    }

    public int actestManagerUpdate(Connection con, ActestManager actestManager)throws Exception {
        String sql = "update t_actestManager set userName=?,password=?,name=?,sex=?,tel=? where actestManId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, actestManager.getUserName());
        pstmt.setString(2, actestManager.getPassword());
        pstmt.setString(3, actestManager.getName());
        pstmt.setString(4, actestManager.getSex());
        pstmt.setString(5, actestManager.getTel());
        pstmt.setInt(6, actestManager.getActestManagerId());
        return pstmt.executeUpdate();
    }

    public boolean haveManagerByUser(Connection con, String userName) throws Exception {
        String sql = "select * from t_actestmanager t1 where t1.userName=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, userName);
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()) {
            return true;
        }
        return false;
    }


}
