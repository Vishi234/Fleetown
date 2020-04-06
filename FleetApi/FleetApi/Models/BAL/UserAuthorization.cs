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
    public class UserAuthorization
    {
        JavaScriptSerializer serializer = new JavaScriptSerializer();
        string sqlconn = ConfigurationManager.ConnectionStrings["DBCONN"].ConnectionString;
        public string UserManagement(UserEntity user)
        {
            List<UserEntity> lstUser = new List<UserEntity>();
            UserEntity userEntity;
            List<AddressEntity> lstAddress = new List<AddressEntity>();
            AddressEntity addEntity;
            string str = "";
            SqlParameter[] sqlParameter = new SqlParameter[14];
            sqlParameter[0] = new SqlParameter("@USER_ID", ((user.userId != "" && user.userId != null) ? Convert.ToInt32(user.userId) : 0));
            sqlParameter[1] = new SqlParameter("@LOGIN_ID", user.loginId);
            sqlParameter[2] = new SqlParameter("@NAME", user.name);
            sqlParameter[3] = new SqlParameter("@EMAIL", user.email);
            sqlParameter[4] = new SqlParameter("@IMEI_NO", user.imeiNo);
            sqlParameter[5] = new SqlParameter("@ACTIVE_STATUS", ((user.status != "" && user.status != null) ? Convert.ToChar(user.status) : '1'));
            sqlParameter[6] = new SqlParameter("@ADDRESS_ID", ((user.addressId != "" && user.addressId != null) ? Convert.ToInt32(user.addressId) : 0));
            sqlParameter[7] = new SqlParameter("@ADDRESS", user.address);
            sqlParameter[8] = new SqlParameter("@ISGPSLOC", ((user.isGpsLoc != "" && user.isGpsLoc != null) ? Convert.ToChar(user.isGpsLoc) : '0'));
            sqlParameter[9] = new SqlParameter("@LATITUDE", user.latitude);
            sqlParameter[10] = new SqlParameter("@LONGITUDE", user.longitude);
            sqlParameter[11] = new SqlParameter("@LANGUAGE", user.language);
            sqlParameter[12] = new SqlParameter("@FLAG", SqlDbType.Char);
            sqlParameter[12].Direction = ParameterDirection.Output;
            sqlParameter[12].Size = 1;
            sqlParameter[13] = new SqlParameter("@MSG", SqlDbType.NVarChar);
            sqlParameter[13].Direction = ParameterDirection.Output;
            sqlParameter[13].Size = 2000;
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(sqlconn, CommandType.StoredProcedure, "SP_CHECK_LOGIN", sqlParameter);

            if (ds != null)
            {
                if (ds.Tables[0].Rows.Count > 0)
                {
                    for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
                    {
                        userEntity = new UserEntity();
                        userEntity.userId = ds.Tables[0].Rows[0]["USER_ID"].ToString();
                        userEntity.loginId = ds.Tables[0].Rows[0]["LOGIN_ID"].ToString();
                        userEntity.name = ds.Tables[0].Rows[0]["NAME"].ToString();
                        userEntity.email = ds.Tables[0].Rows[0]["EMAIL"].ToString();
                        userEntity.imeiNo = ds.Tables[0].Rows[0]["IMEI_NO"].ToString();
                        userEntity.lastLogin = ds.Tables[0].Rows[0]["LAST_LOGIN"].ToString();
                        userEntity.language = ds.Tables[0].Rows[0]["LANGUAGE"].ToString();
                        userEntity.status = ds.Tables[0].Rows[0]["ACTIVE_STATUS"].ToString();
                        userEntity.userId = ds.Tables[0].Rows[0]["USER_ID"].ToString();
                        userEntity.addressId = ds.Tables[0].Rows[0]["ID"].ToString();
                        userEntity.address = ds.Tables[0].Rows[0]["ADDRESS"].ToString();
                        userEntity.isGpsLoc = ds.Tables[0].Rows[0]["ISGPSLOC"].ToString();
                        userEntity.latitude = ds.Tables[0].Rows[0]["LATITUDE"].ToString();
                        userEntity.longitude = ds.Tables[0].Rows[0]["LONGITUDE"].ToString();
                        userEntity.vehicleId = ds.Tables[0].Rows[0]["VEHICLE_ID"].ToString();
                        userEntity.vehicleMake = ds.Tables[0].Rows[0]["VEHICLE_MAKE"].ToString();
                        userEntity.vehicleModel = ds.Tables[0].Rows[0]["VEHICLE_MODEL"].ToString();
                        userEntity.vehicleNo = ds.Tables[0].Rows[0]["VEHICLE_NO"].ToString();
                        userEntity.freeRequestRaise = ds.Tables[0].Rows[0]["FREE_REQUEST_RAISED"].ToString();
                        user.flag = sqlParameter[12].Value.ToString();
                        user.msg = sqlParameter[13].Value.ToString();
                        lstUser.Add(userEntity);
                    }

                }
                if (ds.Tables[1].Rows.Count > 0)
                {
                    for (int j = 0; j < ds.Tables[1].Rows.Count; j++)
                    {
                        addEntity = new AddressEntity();
                        addEntity.userId = ds.Tables[1].Rows[0]["USER_ID"].ToString();
                        addEntity.addressId = ds.Tables[1].Rows[0]["ID"].ToString();
                        addEntity.address = ds.Tables[1].Rows[0]["ADDRESS"].ToString();
                        addEntity.isGpsLoc = ds.Tables[1].Rows[0]["ISGPSLOC"].ToString();
                        addEntity.latitude = ds.Tables[1].Rows[0]["LATITUDE"].ToString();
                        addEntity.longitude = ds.Tables[1].Rows[0]["LONGITUDE"].ToString();
                        lstAddress.Add(addEntity);
                    }
                }
            }
            str = serializer.Serialize(new
            {
                UserInfo = lstUser,
                AddressInfo = lstAddress
            });
            return str;
        }
        public DataTable GetServices(string menuId,string vehicleId)
        {
            DataTable dt = new DataTable();
            SqlParameter[] sqlParameter = new SqlParameter[2];
            sqlParameter[0] = new SqlParameter("@MAIN_SERVICE_ID", ((menuId != "" && menuId != null) ? Convert.ToInt32(menuId) : 0));
            sqlParameter[1] = new SqlParameter("@VEHICLE_MAKE_ID", ((vehicleId != "" && vehicleId != null) ? Convert.ToInt32(vehicleId) : 0));
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(sqlconn, CommandType.StoredProcedure, "SP_GET_SERVICES", sqlParameter);
            if (ds != null)
            {
                dt = ds.Tables[0];
            }
            return dt;
        }
        public string UserLogout(string userId)
        {
            DataTable dt = new DataTable();
            SqlParameter[] sqlParameter = new SqlParameter[4];
            sqlParameter[0] = new SqlParameter("@USER_ID", Convert.ToInt32(userId));
            sqlParameter[1] = new SqlParameter("@LAST_LOGIN", DateTime.Now.ToString("dd-MMM-yyyy hh:mm"));
            sqlParameter[2] = new SqlParameter("@FLAG", SqlDbType.Char);
            sqlParameter[2].Direction = ParameterDirection.Output;
            sqlParameter[2].Size = 1;
            sqlParameter[3] = new SqlParameter("@MSG", SqlDbType.NVarChar);
            sqlParameter[3].Direction = ParameterDirection.Output;
            sqlParameter[3].Size = 2000;
            SqlHelper.ExecuteNonQuery(sqlconn, CommandType.StoredProcedure, "SP_USER_LOGOUT", sqlParameter);
            return Common.ListResponse(sqlParameter[2].Value.ToString(), sqlParameter[3].Value.ToString(), dt);
        }
        public string GetUserAddress(string userId, string addressId)
        {
            DataTable dt = new DataTable();
            List<AddressEntity> lstAddress = new List<AddressEntity>();
            AddressEntity addEntity;
            string str = string.Empty;
            SqlParameter[] sqlParameter = new SqlParameter[2];
            sqlParameter[0] = new SqlParameter("@ADDRESS_ID", ((addressId == "" || addressId == null) ? 0 : Convert.ToInt32(addressId)));
            sqlParameter[1] = new SqlParameter("@USER_ID", Convert.ToInt32(userId));
            DataSet ds = new DataSet();
            ds = SqlHelper.ExecuteDataset(sqlconn, CommandType.StoredProcedure, "SP_GET_USER_ADDRESS", sqlParameter);
            if (ds != null)
            {
                dt = ds.Tables[0];
                if (dt.Rows.Count > 0)
                {
                    for (int i = 0; i < dt.Rows.Count; i++)
                    {
                        addEntity = new AddressEntity();
                        addEntity.userId = dt.Rows[i]["USER_ID"].ToString();
                        addEntity.addressId = dt.Rows[i]["ID"].ToString();
                        addEntity.address = dt.Rows[i]["ADDRESS"].ToString();
                        addEntity.latitude = dt.Rows[i]["LATITUDE"].ToString();
                        addEntity.longitude = dt.Rows[i]["LONGITUDE"].ToString();
                        addEntity.isDefault = dt.Rows[i]["IS_DEFAULT"].ToString();
                        lstAddress.Add(addEntity);
                    }

                }
            }
            str = serializer.Serialize(new
            {
                AddressInfo = lstAddress
            });
            return str;
        }
    }
}