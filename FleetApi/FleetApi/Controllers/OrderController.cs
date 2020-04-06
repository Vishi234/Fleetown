using FleetApi.Models.BAL;
using FleetApi.Models.Entity;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Web.Http;

namespace FleetApi.Controllers
{
    public class OrderController : ApiController
    {
        string response = string.Empty;
        DataTable dt = new DataTable();
        HttpResponseMessage result;

        [Route("api/Order/Place")]
        [HttpPost]
        public HttpResponseMessage PlaceOrder(OrderEntity order)
        {
            try
            {
                OrderManagement objUser = new OrderManagement();
                result = Serializer(objUser.Placement(order));
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(Common.ListResponse("F", ex.Message, dt));
            }
            return result;

        }
        [Route("api/Order/Get")]
        [HttpGet]
        public HttpResponseMessage GetUserOrders(string userId, string orderId, string orderStatus, string vehicleNo, string fromDate, string toDate)
        {
            try
            {
                OrderManagement objUser = new OrderManagement();
                result = Serializer(objUser.GetOrders(userId,orderId,orderStatus,vehicleNo,fromDate,toDate));
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(Common.ListResponse("F", ex.Message, dt));
            }
            return result;

        }
        [Route("api/Order/GetOrderItems")]
        [HttpGet]
        public HttpResponseMessage GetOrderItems(string orderId, string orderStatus)
        {
            try
            {
                EHCommon.WriteExceptionText("orderId=" + orderId);
                EHCommon.WriteExceptionText("orderStatus=" + orderStatus);
                OrderManagement objUser = new OrderManagement();
                result = Serializer(objUser.GetSingleOrders(orderId, orderStatus));
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(Common.ListResponse("F", ex.Message, dt));
            }
            return result;

        }
        [Route("api/Order/Free")]
        [HttpPost]
        public HttpResponseMessage FreeBooking(FreeBookingEntity free)
        {
            try
            {
                OrderManagement objUser = new OrderManagement();
                result = Serializer(objUser.FreeBooking(free));
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(Common.ListResponse("F", ex.Message, dt));
            }
            return result;

        }
        [Route("api/Coupons/Get")]
        [HttpGet]
        public HttpResponseMessage GetCouponList(string userId)
        {
            try
            {
                OrderManagement objUser = new OrderManagement();
                result = Serializer(objUser.GetCouponList(userId));
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(Common.ListResponse("F", ex.Message, dt));
            }
            return result;

        }
        public HttpResponseMessage Serializer(string rows)
        {
            result = Request.CreateResponse(HttpStatusCode.OK);
            result.Content = new StringContent(rows, Encoding.UTF8, "application/json");
            return result;
        }
    }
}