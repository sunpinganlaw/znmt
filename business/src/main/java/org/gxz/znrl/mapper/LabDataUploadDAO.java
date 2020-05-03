package org.gxz.znrl.mapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.gxz.znrl.entity.LabDataUploadEntity;

import java.sql.*;

/**
 * Created by xieyt on 15-12-25.
 */
public class LabDataUploadDAO {
    private static LabDataUploadDAO dataManagerSingleton;

    public static LabDataUploadDAO getInstance(){
        if(dataManagerSingleton == null){
            dataManagerSingleton = new LabDataUploadDAO();
        }
        return dataManagerSingleton;
    }
    /**
     * 同步数据通用查询语句
     * @return
     * @throws Exception
     */
    public String getSyncData(LabDataUploadEntity config, Connection conn, String laborCode) throws Exception {
        JSONArray returnJA = new JSONArray();
        String sql = config.getGetSql();

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {

            if (conn == null) {
                throw new Exception("获取数据库连接失败");
            }

            ps = conn.prepareStatement(sql);

            if (laborCode != null && !laborCode.equals("")) {
                ps.setString(1, laborCode);
            }

            rs = ps.executeQuery();

            if (rs != null) {
                ResultSetMetaData metaData = rs.getMetaData();
                JSONObject rowJO;
                while (rs.next()) {
                    rowJO = new JSONObject();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {

                        //字段名称统一使用大写
                        rowJO.put(metaData.getColumnName(i).toUpperCase(), rs.getString(metaData.getColumnName(i)));
                    }
                    returnJA.add(rowJO);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return returnJA.toString();
    }

    public String getSyncDataSqlite(LabDataUploadEntity config, Connection conn, String laborCode) throws Exception {
        JSONArray returnJA = new JSONArray();
        String sql = config.getGetSql();

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {

            if (conn == null) {
                throw new Exception("获取数据库连接失败");
            }

            ps = conn.prepareStatement(sql);

            if (laborCode != null && !laborCode.equals("")) {
                ps.setString(1, laborCode);
            }

            rs = ps.executeQuery();

            if (rs != null) {
                ResultSetMetaData metaData = rs.getMetaData();
                JSONObject rowJO;
                while (rs.next()) {
                    rowJO = new JSONObject();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {

                        byte[] bytes = rs.getBytes(i);
                        String value =new String(bytes,"utf-8");

                        //字段名称统一使用大写
                        rowJO.put(metaData.getColumnName(i).toUpperCase(),value);

                    }
                    returnJA.add(rowJO);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return returnJA.toString();
    }

    /**
     * 回写同步数据
     * @return
     * @throws Exception
     */
    public boolean writeBackSyncData(LabDataUploadEntity config, String data, Connection conn) throws Exception {
        JSONArray dataJA = JSONArray.parseArray(data);
        String sql = config.getWriteBackSql();
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);//关闭自动提交
            ps = conn.prepareStatement(sql);

            for (int j=0; j<dataJA.size(); j++) {
                if (config.getWbParam1Type() != null ) {
                    if (config.getWbParam1Type().equalsIgnoreCase("INT")){
                        ps.setInt(1, Integer.parseInt((String)((JSONObject) (dataJA.get(j))).get(config.getWbParam1Field())));
                    } else if (config.getWbParam1Type().equalsIgnoreCase("LON")) {
                        ps.setLong(1, Long.parseLong((String)((JSONObject) (dataJA.get(j))).get(config.getWbParam1Field())));
                    } else if (config.getWbParam1Type().equalsIgnoreCase("STR")) {
                        ps.setString(1, (String)((JSONObject) (dataJA.get(j))).get(config.getWbParam1Field()));
                    }
                }

                if (config.getWbParam2Type() != null ) {
                    if (config.getWbParam2Type().equalsIgnoreCase("INT")){
                        ps.setInt(2, Integer.parseInt((String)((JSONObject) (dataJA.get(j))).get(config.getWbParam2Field())));
                    } else if (config.getWbParam2Type().equalsIgnoreCase("LON")) {
                        ps.setLong(2, Long.parseLong((String)((JSONObject) (dataJA.get(j))).get(config.getWbParam2Field())));
                    } else if (config.getWbParam2Type().equalsIgnoreCase("STR")) {
                        ps.setString(2, (String)((JSONObject) (dataJA.get(j))).get(config.getWbParam2Field()));
                    }
                }

                //执行update
                int i = ps.executeUpdate();

                //为解决傻逼微软的连接释放问题，他妈的睡一会
                Thread.sleep(100);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }
}
