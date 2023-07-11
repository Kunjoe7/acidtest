package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.ActestPlace;
import com.model.Record;
import com.util.StringUtil;

public class RecordDao {
    public List<Record> recordList(Connection con, Record s_record)throws Exception {
        List<Record> recordList = new ArrayList<Record>();
        StringBuffer sb = new StringBuffer("select * from t_record t1");
        if(StringUtil.isNotEmpty(s_record.getStudentNumber())) {
            sb.append(" and t1.studentNumber like '%"+s_record.getStudentNumber()+"%'");
        } else if(StringUtil.isNotEmpty(s_record.getStudentName())) {
            sb.append(" and t1.studentName like '%"+s_record.getStudentName()+"%'");
        }else if(StringUtil.isNotEmpty(s_record.getActestStatus())) {
            sb.append(" and t1.actestStatus like '%"+s_record.getActestStatus()+"%'");
        }
        if(s_record.getActestPlaceId()!=0) {
            sb.append(" and t1.actestPlaceId="+s_record.getActestPlaceId());
        }
        if(StringUtil.isNotEmpty(s_record.getDate())) {
            sb.append(" and t1.date="+s_record.getDate());
        }
        if(StringUtil.isNotEmpty(s_record.getStartDate())){
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('"+s_record.getStartDate()+"')");
        }
        if(StringUtil.isNotEmpty(s_record.getEndDate())){
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('"+s_record.getEndDate()+"')");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            Record record=new Record();
            record.setRecordId(rs.getInt("recordId"));
            record.setStudentNumber(rs.getString("studentNumber"));
            record.setStudentName(rs.getString("studentName"));
            int actestPlaceId = rs.getInt("actestPlaceId");
            record.setActestPlaceId(actestPlaceId);
            record.setActestPlaceName(ActestPlaceDao.actestPlaceName(con, actestPlaceId));
            record.setActestStatus(rs.getString("actestStatus"));
            record.setDate(rs.getString("date"));
            record.setDetail(rs.getString("detail"));
            recordList.add(record);
        }
        return recordList;
    }

    public List<Record> recordListWithPlace(Connection con, Record s_record, int PlaceId)throws Exception {
        List<Record> recordList = new ArrayList<Record>();
        StringBuffer sb = new StringBuffer("select * from t_record t1");
        if(StringUtil.isNotEmpty(s_record.getStudentNumber())) {
            sb.append(" and t1.studentNumber like '%"+s_record.getStudentNumber()+"%'");
        } else if(StringUtil.isNotEmpty(s_record.getStudentName())) {
            sb.append(" and t1.studentName like '%"+s_record.getStudentName()+"%'");
        }
        sb.append(" and t1.actestPlaceId="+PlaceId);
        if(StringUtil.isNotEmpty(s_record.getStartDate())){
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('"+s_record.getStartDate()+"')");
        }
        if(StringUtil.isNotEmpty(s_record.getEndDate())){
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('"+s_record.getEndDate()+"')");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            Record record=new Record();
            record.setRecordId(rs.getInt("recordId"));
            record.setStudentNumber(rs.getString("studentNumber"));
            record.setStudentName(rs.getString("studentName"));
            int actestPlaceId = rs.getInt("actestPlaceId");
            record.setActestPlaceId(actestPlaceId);
            record.setActestPlaceName(ActestPlaceDao.actestPlaceName(con, actestPlaceId));
            record.setActestStatus(rs.getString("actestStatus"));
            record.setDate(rs.getString("date"));
            record.setDetail(rs.getString("detail"));
            recordList.add(record);
        }
        return recordList;
    }

    public List<Record> recordListWithNumber(Connection con, Record s_record, String studentNumber)throws Exception {
        List<Record> recordList = new ArrayList<Record>();
        StringBuffer sb = new StringBuffer("select * from t_record t1");
        if(StringUtil.isNotEmpty(studentNumber)) {
            sb.append(" and t1.studentNumber ="+studentNumber);
        }
        if(StringUtil.isNotEmpty(s_record.getStartDate())){
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('"+s_record.getStartDate()+"')");
        }
        if(StringUtil.isNotEmpty(s_record.getEndDate())){
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('"+s_record.getEndDate()+"')");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            Record record=new Record();
            record.setRecordId(rs.getInt("recordId"));
            record.setStudentNumber(rs.getString("studentNumber"));
            record.setStudentName(rs.getString("studentName"));
            int actestPlaceId = rs.getInt("actestPlaceId");
            record.setActestPlaceId(actestPlaceId);
            record.setActestPlaceName(ActestPlaceDao.actestPlaceName(con, actestPlaceId));
            record.setActestStatus(rs.getString("actestStatus"));
            record.setDate(rs.getString("date"));
            record.setDetail(rs.getString("detail"));
            recordList.add(record);
        }
        return recordList;
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


    public Record recordShow(Connection con, String recordId)throws Exception {
        String sql = "select * from t_record t1 where t1.recordId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, recordId);
        ResultSet rs=pstmt.executeQuery();
        Record record = new Record();
        if(rs.next()) {
            record.setRecordId(rs.getInt("recordId"));
            record.setStudentNumber(rs.getString("studentNumber"));
            record.setStudentName(rs.getString("studentName"));
            int actestPlaceId = rs.getInt("actestPlaceId");
            record.setActestPlaceId(actestPlaceId);
            record.setActestPlaceName(ActestPlaceDao.actestPlaceName(con, actestPlaceId));
            record.setActestStatus(rs.getString("actestStatus"));
            record.setDate(rs.getString("date"));
            record.setDetail(rs.getString("detail"));
        }
        return record;
    }

    public int recordAdd(Connection con, Record record)throws Exception {
        String sql = "insert into t_record values(null,?,?,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, record.getStudentNumber());
        pstmt.setString(2, record.getStudentName());
        pstmt.setInt(3, record.getActestPlaceId());
        pstmt.setString(4, record.getActestStatus());
        pstmt.setString(5, record.getDate());
        pstmt.setString(6, record.getDetail());
        return pstmt.executeUpdate();
    }

    public int recordDelete(Connection con, String recordId)throws Exception {
        String sql = "delete from t_record where recordId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, recordId);
        return pstmt.executeUpdate();
    }

    public int recordUpdate(Connection con, Record record)throws Exception {
        String sql = "update t_record set studentNumber=?,studentName=?,ActestPlaceId=?,ActestStatus=?,detail=? where recordId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, record.getStudentNumber());
        pstmt.setString(2, record.getStudentName());
        pstmt.setInt(3, record.getActestPlaceId());
        pstmt.setString(4, record.getActestStatus());
        pstmt.setString(5, record.getDetail());
        pstmt.setInt(6, record.getRecordId());
        return pstmt.executeUpdate();
    }


}
