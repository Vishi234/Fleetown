using FleetApi.Models.Entity;
using Microsoft.ApplicationBlocks.Data;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Globalization;
using System.Linq;
using System.Web;
using System.Web.Script.Serialization;

namespace FleetApi.Models.BAL
{
    public class OrderManagement
    {
        JavaScriptSerializer serializer = new JavaScriptSerializer();
        string sqlconn = ConfigurationManager.ConnectionStrings["DBCONN"].ConnectionString;
        public string Placement(OrderEntity order)
        {
            CultureInfo provider = CultureInfo.InvariantCulture;
            List<OrderEntity> lstOrder = new List<OrderEntity>();
            OrderEntity orderEntity;
            string str = "";
            SqlParameter[] sqlParameter = new SqlParameter[23];
            sqlParameter[0] = new SqlParameter("@ORDER_ID", ((order.orderId != "" && order.orderId != null) ? order.orderId : "0"));
            sqlParameter[1] = new SqlParameter("@USER_ID", order.userId);
            sqlParameter[2] = new SqlParameter("@NO_OF_ITEMS", order.noOfItems);
            sqlParameter[3] = new SqlParameter("@TOTAL_DISCOUNT", order.totalDiscount);
            sqlParameter[4] = new SqlParameter("@TOTAL_PRICE", order.totalPrice);
            sqlParameter[5] = new SqlParameter("@ITEM_LIST", order.itemList);
            sqlParameter[6] = new SqlParameter("@SERVICE_DATE", order.serviceDate);
            sqlParameter[7] = new SqlParameter("@SERVICE_TIME", order.serviceTime);
            sqlParameter[8] = new SqlParameter("@SERVICE_LOC", order.serviceLoc);
            sqlParameter[9] = new SqlParameter("@SERVICE_TYPE", order.serviceType);
            sqlParameter[10] = new SqlParameter("@ORDER_DATE", DateTime.Now.ToString("dd-MMM-yyyy hh:mm"));
            sqlParameter[11] = new SqlParameter("@ORDER_STATUS", order.orderStatus);
            sqlParameter[12] = new SqlParameter("@OTHER_ISSUE", order.otherIssue);
            sqlParameter[13] = new SqlParameter("@VEHICLE_ID",Convert.ToInt32(order.vehicleId));
            sqlParameter[14] = new SqlParameter("@CANCEL_REASON", order.cancelReason);
            sqlParameter[15] = new SqlParameter("@OTHER_REASON", order.otherReason);
            sqlParameter[16] = new SqlParameter("@CANCEL_DATE", ((order.orderStatus == "1" ) ? DateTime.Now.ToString("dd-MMM-yyyy hh:mm") : ""));
            sqlParameter[17] = new SqlParameter("@COUPON_ID", order.couponId);
            sqlParameter[18] = new SqlParameter("@COUPON_CODE", order.couponCode);
            sqlParameter[19] = new SqlParameter("@COUPON_DISCOUNT", order.couponDis);
            sqlParameter[20] = new SqlParameter("@NEW_ORDER_ID", SqlDbType.NVarChar);
            sqlParameter[20].Direction = ParameterDirection.Output;
            sqlParameter[20].Size = 200;
            sqlParameter[21] = new SqlParameter("@FLAG", SqlDbType.Char);
            sqlParameter[21].Direction = ParameterDirection.Output;
            sqlParameter[21].Size = 1;
            sqlParameter[22] = new SqlParameter("@MSG", SqlDbType.NVarChar);
            sqlParameter[22].Direction = ParameterDirection.Output;
            sqlParameter[22].Size = 2000;
            SqlHelper.ExecuteScalar(sqlconn, CommandType.StoredProcedure, "SP_MANAGE_ORDERS", sqlParameter);

            orderEntity = new OrderEntity();
            orderEntity.flag = sqlParameter[21].Value.ToString();
            orderEntity.msg = sqlParameter[22].Value.ToString();
            orderEntity.orderId = sqlParameter[20].Value.ToString();
            lstOrder.Add(orderEntity);
            str = serializer.Serialize( new { Result = lstOrder});
            return str;
        }
        public string GetOrders(string userId,string orderId,string orderStatus,string vehicleNo,string fromDate,string toDate)
        {
            List<OrderEntity> lstOrder = new List<OrderEntity>();
            OrderEntity orderEntity;
            string str = "";
            DataSet ds = new DataSet();
            SqlParameter[] sqlParameter = new SqlParameter[6];
            sqlParameter[0] = new SqlParameter("@USER_ID", Convert.ToInt32(userId));
            sqlParameter[1] = new SqlParameter("@ORDER_ID", orderId);
            sqlParameter[2] = new SqlParameter("@ORDERSTATUS", Convert.ToChar(orderStatus));
            sqlParameter[3] = new SqlParameter("@VEHICLE_NO", vehicleNo);
            sqlParameter[4] = new SqlParameter("@FROM_DATE", ((fromDate != "" && fromDate != null) ? fromDate : ""));
            sqlParameter[5] = new SqlParameter("@TO_DATE", ((toDate != "" && toDate != null) ? toDate : ""));
            ds = SqlHelper.ExecuteDataset(sqlconn, CommandType.StoredProcedure, "SP_GET_USER_ORDERS", sqlParameter);
            if (ds != null)
            {
                if (ds.Tables[0].Rows.Count > 0)
                {
                    for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
                    {
                        orderEntity = new OrderEntity();
                        orderEntity.orderId = ds.Tables[0].Rows[i]["ORDER_ID"].ToString();
                        orderEntity.userId = ds.Tables[0].Rows[i]["USER_ID"].ToString();
                        orderEntity.noOfItems = ds.Tables[0].Rows[i]["NO_OF_ITEMS"].ToString();
                        orderEntity.totalPrice = ds.Tables[0].Rows[i]["TOTAL_PRICE"].ToString();
                        orderEntity.serviceDate = ds.Tables[0].Rows[i]["SERVICE_DATE"].ToString();
                        orderEntity.serviceTime = ds.Tables[0].Rows[i]["SERVICE_TIME"].ToString();
                        orderEntity.serviceLoc = ds.Tables[0].Rows[i]["SERVICE_LOCATION"].ToString();
                        orderEntity.serviceType = ds.Tables[0].Rows[i]["SERVICE_TYPE"].ToString();
                        orderEntity.orderDate = ds.Tables[0].Rows[i]["ORDER_DATE"].ToString();
                        orderEntity.orderStatus = ds.Tables[0].Rows[i]["ORDER_STATUS"].ToString();
                        orderEntity.otherIssue = ds.Tables[0].Rows[i]["OTHER_ISSUE"].ToString();
                        orderEntity.vehicleId = ds.Tables[0].Rows[i]["VEHICLE_ID"].ToString();
                        orderEntity.vehicleMake = ds.Tables[0].Rows[i]["VEHICLE_MAKE"].ToString();
                        orderEntity.vehicleModel = ds.Tables[0].Rows[i]["VEHICLE_MODEL"].ToString();
                        orderEntity.vehicleNo = ds.Tables[0].Rows[i]["VEHICLE_NO"].ToString();
                        orderEntity.itemList = ds.Tables[0].Rows[i]["ORDER_ITEMS"].ToString();
                        orderEntity.cancelReason = ds.Tables[0].Rows[i]["CANCEL_REASON"].ToString();
                        orderEntity.otherReason = ds.Tables[0].Rows[i]["OTHER_REASON"].ToString();
                        orderEntity.cancellationDate = ds.Tables[0].Rows[i]["CANCELLATION_DATE"].ToString();
                        lstOrder.Add(orderEntity);
                    }
                }
            }
            str = serializer.Serialize(new { Result = lstOrder });
            return str;
        }
        public string GetSingleOrders(string orderId, string orderStatus)
        {
            List<OrderEntity> lstOrder = new List<OrderEntity>();
            OrderEntity orderEntity;
            string str = "";
            DataSet ds = new DataSet();
            EHCommon.WriteExceptionText("Inside orderId=" + orderId);
            EHCommon.WriteExceptionText("Inside orderStatus=" + orderStatus);
            SqlParameter[] sqlParameter = new SqlParameter[2];
            sqlParameter[0] = new SqlParameter("@ORDER_ID", orderId);
            sqlParameter[1] = new SqlParameter("@ORDERSTATUS", Convert.ToChar(orderStatus));
            ds = SqlHelper.ExecuteDataset(sqlconn, CommandType.StoredProcedure, "SP_GET_ORDER_ITEM_DETAILS", sqlParameter);
            if (ds != null)
            {
                EHCommon.WriteExceptionText("Total Record=" + ds.Tables[0].Rows.Count);
                if (ds.Tables[0].Rows.Count > 0)
                {
                    for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
                    {
                        orderEntity = new OrderEntity();
                        orderEntity.orderId = ds.Tables[0].Rows[i]["ORDER_ID"].ToString();
                        orderEntity.userId = ds.Tables[0].Rows[i]["USER_ID"].ToString();
                        orderEntity.noOfItems = ds.Tables[0].Rows[i]["NO_OF_ITEMS"].ToString();
                        orderEntity.itemPrice = ds.Tables[0].Rows[i]["PRICE"].ToString();
                        orderEntity.itemDiscount = ds.Tables[0].Rows[i]["DISCOUNT"].ToString();
                        orderEntity.itemId = ds.Tables[0].Rows[i]["SERVICE_ID"].ToString();
                        orderEntity.totalPrice = ds.Tables[0].Rows[i]["TOTAL_PRICE"].ToString();
                        orderEntity.serviceDate = ds.Tables[0].Rows[i]["SERVICE_DATE"].ToString();
                        orderEntity.serviceTime = ds.Tables[0].Rows[i]["SERVICE_TIME"].ToString();
                        orderEntity.serviceLoc = ds.Tables[0].Rows[i]["SERVICE_LOCATION"].ToString();
                        orderEntity.serviceType = ds.Tables[0].Rows[i]["SERVICE_TYPE"].ToString();
                        orderEntity.orderDate = ds.Tables[0].Rows[i]["ORDER_DATE"].ToString();
                        orderEntity.orderStatus = ds.Tables[0].Rows[i]["ORDER_STATUS"].ToString();
                        orderEntity.otherIssue = ds.Tables[0].Rows[i]["OTHER_ISSUE"].ToString();
                        orderEntity.vehicleId = ds.Tables[0].Rows[i]["VEHICLE_ID"].ToString();
                        orderEntity.vehicleMakeId = ds.Tables[0].Rows[i]["VEHICLE_MAKE_ID"].ToString();
                        orderEntity.vehicleMake = ds.Tables[0].Rows[i]["VEHICLE_MAKE"].ToString();
                        orderEntity.vehicleModelId = ds.Tables[0].Rows[i]["VEHICLE_MODEL_ID"].ToString();
                        orderEntity.vehicleModel = ds.Tables[0].Rows[i]["VEHICLE_MODEL"].ToString();
                        orderEntity.vehicleNo = ds.Tables[0].Rows[i]["VEHICLE_NO"].ToString();
                        orderEntity.itemList = ds.Tables[0].Rows[i]["ORDER_ITEMS"].ToString();
                        orderEntity.cancelReason = ds.Tables[0].Rows[i]["CANCEL_REASON"].ToString();
                        orderEntity.otherReason = ds.Tables[0].Rows[i]["OTHER_REASON"].ToString();
                        orderEntity.cancellationDate = ds.Tables[0].Rows[i]["CANCELLATION_DATE"].ToString();
                        lstOrder.Add(orderEntity);
                    }
                }
            }
            str = serializer.Serialize(new { Result = lstOrder });
            return str;
        }
        public string FreeBooking(FreeBookingEntity free)
        {
            List<FreeBookingEntity> lstOrder = new List<FreeBookingEntity>();
            FreeBookingEntity orderEntity;
            string str = "";
            SqlParameter[] sqlParameter = new SqlParameter[13];
            sqlParameter[0] = new SqlParameter("@REQUEST_ID", free.reqId);
            sqlParameter[1] = new SqlParameter("@USER_ID", Convert.ToInt32(free.userId));
            sqlParameter[2] = new SqlParameter("@ITEM_NAME", free.itemName);
            sqlParameter[3] = new SqlParameter("@PRICE", free.price);
            sqlParameter[4] = new SqlParameter("@REQ_DATE", DateTime.Now.ToString("dd-MMM-yyyy hh:mm"));
            sqlParameter[5] = new SqlParameter("@SCHEDULE_DATE", free.schDate);
            sqlParameter[6] = new SqlParameter("@SCHEDULE_TIME", free.schTime);
            sqlParameter[7] = new SqlParameter("@LOCATION", free.location);
            sqlParameter[8] = new SqlParameter("@VEHICLE_ID",Convert.ToInt32(free.vehicleId));
            sqlParameter[9] = new SqlParameter("@STATUS", Convert.ToChar(free.status));
            sqlParameter[10] = new SqlParameter("@USER_REQ_STA", SqlDbType.Char);
            sqlParameter[10].Direction = ParameterDirection.Output;
            sqlParameter[10].Size = 1;
            sqlParameter[11] = new SqlParameter("@FLAG", SqlDbType.Char);
            sqlParameter[11].Direction = ParameterDirection.Output;
            sqlParameter[11].Size = 1;
            sqlParameter[12] = new SqlParameter("@MSG", SqlDbType.NVarChar);
            sqlParameter[12].Direction = ParameterDirection.Output;
            sqlParameter[12].Size = 2000;
            SqlHelper.ExecuteScalar(sqlconn, CommandType.StoredProcedure, "SP_MANAGE_FREE_BOOKING", sqlParameter);
            orderEntity = new FreeBookingEntity();
            orderEntity.userReqSta = sqlParameter[10].Value.ToString();
            orderEntity.flag = sqlParameter[11].Value.ToString();
            orderEntity.msg = sqlParameter[12].Value.ToString();
            lstOrder.Add(orderEntity);
            str = serializer.Serialize(new { Result = lstOrder });
            return str;
        }
        public string GetCouponList(string userId)
        {
            List<CouponEntity> lstCoupon = new List<CouponEntity>();
            CouponEntity couponEntity;
            string str = "";
            DataSet ds = new DataSet();
            SqlParameter[] sqlParameter = new SqlParameter[1];
            sqlParameter[0] = new SqlParameter("@USER_ID", Convert.ToInt32(userId));
            ds = SqlHelper.ExecuteDataset(sqlconn, CommandType.StoredProcedure, "SP_GET_COUPON_LIST", sqlParameter);
            if (ds != null)
            {
                EHCommon.WriteExceptionText("Total Record=" + ds.Tables[0].Rows.Count);
                if (ds.Tables[0].Rows.Count > 0)
                {
                    for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
                    {
                        couponEntity = new CouponEntity();
                        couponEntity.id = ds.Tables[0].Rows[i]["ID"].ToString();
                        couponEntity.userId = ds.Tables[0].Rows[i]["USER_ID"].ToString();
                        couponEntity.couponId = ds.Tables[0].Rows[i]["COUPON_ID"].ToString();
                        couponEntity.couponCode = ds.Tables[0].Rows[i]["COUPON_CODE"].ToString();
                        couponEntity.couponDesc = ds.Tables[0].Rows[i]["COUPON_DESC"].ToString();
                        couponEntity.couponDiscount = ds.Tables[0].Rows[i]["COUPON_DISCOUNT"].ToString();
                        couponEntity.validFrom = ds.Tables[0].Rows[i]["VALID_FROM"].ToString();
                        couponEntity.validTo = ds.Tables[0].Rows[i]["VALID_TO"].ToString();
                        couponEntity.currentStatus = ds.Tables[0].Rows[i]["CURRENT_STATUS"].ToString();
                        lstCoupon.Add(couponEntity);
                    }
                }
            }
            str = serializer.Serialize(new { Result = lstCoupon });
            return str;
        }
    }
}