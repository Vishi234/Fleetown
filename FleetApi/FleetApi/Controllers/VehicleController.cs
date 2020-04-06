using System;
using System.Net.Http;
using System.Web.Http;
using static FleetApi.Models.BAL.Common;
using FleetApi.Models.BAL;
using FleetApi.Models.Entity;
using System.Net;
using System.Text;
using System.Data;

namespace FleetApi.Controllers
{
    public class VehicleController : ApiController
    {
        string response = string.Empty;
        HttpResponseMessage result;
        DataTable dt = new DataTable();
        // GET: Vehicle
        [Route("api/Vehicle/ManageVehicleInfo")]
        [HttpPost]
        public HttpResponseMessage VehicleInfo(VehicleEntity vehicle)
        {
            try
            {

                VehicleManagement objVehicle = new VehicleManagement();
                result = Serializer(objVehicle.ManageVehicleInfo(vehicle));
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(ListResponse("F", ex.Message, dt));
            }
            return result;

        }

        [Route("api/Vehicle/GetUserVehicleList")]
        [HttpGet]
        public HttpResponseMessage GetUserVehicleList(string userId, string vehicleId , string flag)
        {
            try
            {
                VehicleManagement objVehicle = new VehicleManagement();
                result = Serializer(objVehicle.GetUserVehicle(userId,vehicleId,flag));
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