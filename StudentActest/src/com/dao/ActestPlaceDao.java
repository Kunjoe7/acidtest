package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.ActestPlace;
import com.model.ActestManager;
import com.model.PageBean;
import com.util.StringUtil;

public class ActestPlaceDao {

    public List<ActestPlace> actestPlaceList(Connection con, PageBean pageBean, ActestPlace s_actestPlace)throws Exception {
        List<ActestPlace> actestPlaceList = new ArrayList<ActestPlace>();
        StringBuffer sb = new StringBuffer("select * from t_actestPlace t1");
        if(StringUtil.isNotEmpty(s_actestPlace.getActestPlaceName())) {
            sb.append(" where t1.actestPlaceName like '%"+s_actestPlace.getActestPlaceName()+"%'");
        }
        if(pageBean != null) {
            sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString());
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

    public static String actestPlaceName(Connection con, int actestPlaceId)throws Exception {
        String sql = "select * from t_actestPlace where actestPlaceId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, actestPlaceId);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            return rs.getString("actestPlaceName");
        }
        return null;
    }

    public int ActestPlaceCount(Connection con, ActestPlace s_actestPlace)throws Exception {
        StringBuffer sb = new StringBuffer("select count(*) as total from t_actestPlace t1");
        if(StringUtil.isNotEmpty(s_actestPlace.getActestPlaceName())) {
            sb.append(" where t1.actestPlaceName like '%"+s_actestPlace.getActestPlaceName()+"%'");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString());
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            return rs.getInt("total");
        } else {
            return 0;
        }
    }

    public ActestPlace actestPlaceShow(Connection con, String actestPlaceId)throws Exception {
        String sql = "select * from t_actestPlace t1 where t1.actestPlaceId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, actestPlaceId);
        ResultSet rs=pstmt.executeQuery();
        ActestPlace actestPlace = new ActestPlace();
        if(rs.next()) {
            actestPlace.setActestPlaceId(rs.getInt("actestPlaceId"));
            actestPlace.setActestPlaceName(rs.getString("actestPlaceName"));
            actestPlace.setDetail(rs.getString("actestPlaceDetail"));
        }
        return actestPlace;
    }

    public int actestPlaceAdd(Connection con, ActestPlace actestPlace)throws Exception {
        String sql = "insert into t_actestPlace values(null,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, actestPlace.getActestPlaceName());
        pstmt.setString(2, actestPlace.getDetail());
        return pstmt.executeUpdate();
    }

    public int actestPlaceDelete(Connection con, String actestPlaceId)throws Exception {
        String sql = "delete from t_actestPlace where actestPlaceId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, actestPlaceId);
        return pstmt.executeUpdate();
    }

    public int actestPlaceUpdate(Connection con, ActestPlace actestPlace)throws Exception {
        String sql = "update t_actestPlace set actestPlaceName=?,actestPlaceDetail=? where actestPlaceId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, actestPlace.getActestPlaceName());
        pstmt.setString(2, actestPlace.getDetail());
        pstmt.setInt(3, actestPlace.getActestPlaceId());
        return pstmt.executeUpdate();
    }

    public boolean existManOrActestWithId(Connection con, String actestPlaceId)throws Exception {
        boolean isExist = false;
//		String sql="select * from t_actestPlace,t_actestManager,t_connection where actestManId=managerId and actestPlaceId=PlaceId and actestPlaceId=?";
        String sql = "select *from t_actestManager where actestPlaceId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, actestPlaceId);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            isExist = true;
        } else {
            isExist = false;
        }
        String sql1="select * from t_actestPlace t1,t_actest t2 where t1.actestPlaceId=t2.actestPlaceId and t1.actestPlaceId=?";
        PreparedStatement p=con.prepareStatement(sql1);
        p.setString(1, actestPlaceId);
        ResultSet r = pstmt.executeQuery();
        if(r.next()) {
            return isExist;
        } else {
            return false;
        }
    }

    public List<ActestManager> actestManWithoutPlace(Connection con)throws Exception {
        List<ActestManager> actestManagerList = new ArrayList<ActestManager>();
        String sql = "SELECT * FROM t_actestManager WHERE actestPlaceId IS NULL OR actestPlaceId=0";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            ActestManager actestManager=new ActestManager();
            actestManager.setActestPlaceId(rs.getInt("actestPlaceId"));
            actestManager.setActestManagerId(rs.getInt("actestManId"));
            actestManager.setName(rs.getString("name"));
            actestManager.setUserName(rs.getString("userName"));
            actestManager.setSex(rs.getString("sex"));
            actestManager.setTel(rs.getString("tel"));
            actestManagerList.add(actestManager);
        }
        return actestManagerList;
    }

    public List<ActestManager> actestManWithPlaceId(Connection con, String actestPlaceId)throws Exception {
        List<ActestManager> actestManagerList = new ArrayList<ActestManager>();
        String sql = "select *from t_actestManager where actestPlaceId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, actestPlaceId);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            ActestManager actestManager=new ActestManager();
            actestManager.setActestPlaceId(rs.getInt("actestPlaceId"));
            actestManager.setActestManagerId(rs.getInt("actestManId"));
            actestManager.setName(rs.getString("name"));
            actestManager.setUserName(rs.getString("userName"));
            actestManager.setSex(rs.getString("sex"));
            actestManager.setTel(rs.getString("tel"));
            actestManagerList.add(actestManager);
        }
        return actestManagerList;
    }

    public int managerUpdateWithId (Connection con, String actestManagerId, String actestPlaceId)throws Exception {
        String sql = "update t_actestManager set actestPlaceId=? where actestManId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, actestPlaceId);
        pstmt.setString(2, actestManagerId);
        return pstmt.executeUpdate();
    }
}
