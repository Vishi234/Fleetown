using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Data;
using System.Text;
using static FleetApi.Models.BAL.Common;
using FleetApi.Models.BAL;
using System.Xml;
using System.Xml.Linq;
using System.Web;
using System.IO;
using FleetApi.Models.Entity;
using System.Web.Script.Serialization;

namespace FleetApi.Controllers
{
    public class UserController : ApiController
    {
        string response = string.Empty;
        FleetDBEntities db = new FleetDBEntities();
        DataTable dt = new DataTable(); 
        //string flag, msg = string.Empty;
        HttpResponseMessage result;
        
        [Route("api/User/CheckUserInfo")]
        [HttpPost]
        public HttpResponseMessage CheckUserInfo(UserEntity user)
        {
            try
            {
               
                UserAuthorization objUser = new UserAuthorization();
                result = Serializer(objUser.UserManagement(user));
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(ListResponse("F", ex.Message, dt));
            }
            return result;

        }
        [Route("api/User/Logout")]
        [HttpGet]
        public HttpResponseMessage Logout(string userId)
        {
            try
            {

                UserAuthorization objUser = new UserAuthorization();
                result = Serializer(objUser.UserLogout(userId));
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(ListResponse("F", ex.Message, dt));
            }
            return result;

        }
        [Route("api/User/GetMasterData")]
        [HttpGet]
        public HttpResponseMessage GetMasterData(string dataType)
        {
            try
            {
                VehicleManagement objVehicle = new VehicleManagement();
                response = objVehicle.GetMasterData(dataType);
                result = Serializer("{" + response.Substring(0, response.Length - 1) + "}");
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(ListResponse("F", ex.Message, dt));
            }
            return result;

        }
        [Route("api/User/GetServiceList")]
        [HttpGet]
        public HttpResponseMessage GetServiceList(string menuId,string vehicleId)
        {
            try
            {
                UserAuthorization objUser = new UserAuthorization();
                EHCommon.WriteExceptionText("MenuId" + menuId);
                EHCommon.WriteExceptionText("VehicleId" + vehicleId);
                result = Serializer(ListResponse("", "", objUser.GetServices(menuId, vehicleId)));
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(ListResponse("F", ex.Message, dt));
            }
            return result;
        }
        [Route("api/User/GetUserAddressList")]
        [HttpGet]
        public HttpResponseMessage GetUserAddressList(string userId,string addressId)
        {
            try
            {
                UserAuthorization objUser = new UserAuthorization();
                result = Serializer(objUser.GetUserAddress(userId, addressId));
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(ListResponse("F", ex.Message, dt));
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