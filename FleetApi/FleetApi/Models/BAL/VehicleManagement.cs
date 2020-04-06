using FleetApi.Models.Entity;
using Microsoft.ApplicationBlocks.Data;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using System.Web.Script.Serialization;

namespace FleetApi.Models.BAL
{
    public class VehicleManagement
    {
        JavaScriptSerializer serializer = new JavaScriptSerializer();
        string sqlconn = ConfigurationManager.ConnectionStrings["DBCONN"].ConnectionString;
        public string GetMasterData(string dataType)
        {
            List<Dictionary<string, object>> rows = new List<Dictionary<string, object>>();
            Dictionary<string, object> row = null;
            string[] metaDataKey = ConfigurationManager.AppSettings["MetaDataKey"].Split(',');
            string str = "";
            SqlParameter[] sqlParameter = new SqlParameter[1];
            sqlParameter[0] = new SqlParameter("@DATA_TYPE", ((dataType != "" && dataType != null) ? dataType : ""));
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(sqlconn, CommandType.StoredProcedure, "SP_GET_MASTER_DATA", sqlParameter);
            if (ds != null)
            {
                for (int i = 0; i <= ds.Tables.Count - 1; i++)
                {
                    rows = new List<Dictionary<string, object>>();
                    foreach (DataRow dr in ds.Tables[i].Rows)
                    {
                        row = new Dictionary<string, object>();
                        foreach (DataColumn col in ds.Tables[i].Columns)
                        {
                            row.Add(col.ColumnName.Trim(), dr[col].ToString());
                        }
                        rows.Add(row);
                    }
                    str += '"' + metaDataKey[i] + '"' + ":" + serializer.Serialize(rows) + ",";
                }
            }
            return str;
        }
        public string ManageVehicleInfo(VehicleEntity vehicle)
        {
            List<VehicleEntity> lstVehicle = new List<VehicleEntity>();
            VehicleEntity objVehicle;
            DataSet ds = new DataSet();
            DataTable dt = new DataTable();
            string str = string.Empty;
            SqlParameter[] sqlParameter = new SqlParameter[8];
            sqlParameter[0] = new SqlParameter("@VEHICLE_ID", ((vehicle.id != "" && vehicle.id != null) ? Convert.ToInt32(vehicle.id) : 0));
            sqlParameter[1] = new SqlParameter("@USER_ID", vehicle.userId);
            sqlParameter[2] = new SqlParameter("@VEHICLE_MAKE", vehicle.vehicleMake);
            sqlParameter[3] = new SqlParameter("@VEHICLE_MODEL", vehicle.vehicleModel);
            sqlParameter[4] = new SqlParameter("@VEHICLE_NO", vehicle.vehicleNo);
            sqlParameter[5] = new SqlParameter("@STATUS", ((vehicle.status != "" && vehicle.status != null) ? Convert.ToChar(vehicle.status) : '1'));
            sqlParameter[6] = new SqlParameter("@FLAG", SqlDbType.Char);
            sqlParameter[6].Direction = ParameterDirection.Output;
            sqlParameter[6].Size = 1;
            sqlParameter[7] = new SqlParameter("@MSG", SqlDbType.NVarChar);
            sqlParameter[7].Direction = ParameterDirection.Output;
            sqlParameter[7].Size = 2000;
            ds = SqlHelper.ExecuteDataset(sqlconn, CommandType.StoredProcedure, "SP_VEHICLE_INFORMATION", sqlParameter);
            if (ds != null)
            {
                EHCommon.WriteExceptionText("Phase1");
                dt = ds.Tables[0];
                if (dt.Rows.Count > 0)
                {
                    EHCommon.WriteExceptionText("Phase2");
                    for (int i = 0; i < dt.Rows.Count; i++)
                    {
                        EHCommon.WriteExceptionText("Phase3");
                        objVehicle = new VehicleEntity();
                        objVehicle.id = dt.Rows[i]["ID"].ToString();
                        objVehicle.userId = dt.Rows[i]["USER_ID"].ToString();
                        objVehicle.vehicleMake = dt.Rows[i]["VEHICLE_MAKE"].ToString();
                        objVehicle.vehicleModel = dt.Rows[i]["VEHICLE_MODEL"].ToString();
                        objVehicle.vehicleNo = dt.Rows[i]["VEHICLE_NO"].ToString();
                        objVehicle.isDefault = dt.Rows[i]["IS_DEFAULT"].ToString();
                        objVehicle.flag = sqlParameter[6].Value.ToString();
                        objVehicle.msg = sqlParameter[7].Value.ToString();
                        lstVehicle.Add(objVehicle);
                    }

                }
            }
            str = serializer.Serialize(new
            {
                Vehicle = lstVehicle
            });
            return str;
        }

        public string GetUserVehicle(string userId, string vehicleId,string flag)
        {
            DataTable dt = new DataTable();
            List<VehicleEntity> lstvehicle = new List<VehicleEntity>();
            VehicleEntity vehicleEntity;
            string str = string.Empty;
            SqlParameter[] sqlParameter = new SqlParameter[3];
            sqlParameter[0] = new SqlParameter("@VEHICLE_ID", ((vehicleId == "" || vehicleId == null) ? 0 : Convert.ToInt32(vehicleId)));
            sqlParameter[1] = new SqlParameter("@USER_ID", Convert.ToInt32(userId));
            sqlParameter[2] = new SqlParameter("@FLAG", flag);
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(sqlconn, CommandType.StoredProcedure, "SP_GET_USER_VEHICLE", sqlParameter);
            if (ds != null)
            {
                dt = ds.Tables[0];
                if (dt.Rows.Count > 0)
                {
                    for (int i = 0; i < dt.Rows.Count; i++)
                    {
                        vehicleEntity = new VehicleEntity();
                        vehicleEntity.id = dt.Rows[i]["ID"].ToString();
                        vehicleEntity.userId = dt.Rows[i]["USER_ID"].ToString();
                        vehicleEntity.vehicleMake = dt.Rows[i]["VEHICLE_MAKE"].ToString();
                        vehicleEntity.vehicleModel = dt.Rows[i]["VEHICLE_MODEL"].ToString();
                        vehicleEntity.vehicleNo = dt.Rows[i]["VEHICLE_NO"].ToString();
                        vehicleEntity.isDefault = dt.Rows[i]["IS_DEFAULT"].ToString();
                        lstvehicle.Add(vehicleEntity);
                    }

                }
            }
            str = serializer.Serialize(new
            {
                Result = lstvehicle
            });
            return str;
        }
    }
}