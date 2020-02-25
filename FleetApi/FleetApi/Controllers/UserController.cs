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

namespace FleetApi.Controllers
{
    public class UserController : ApiController
    {
        string response = string.Empty;
        FleetDBEntities db = new FleetDBEntities();
        DataTable dt = new DataTable();
        //string flag, msg = string.Empty;
        HttpResponseMessage result;

        [Route("api/CheckUserInfo")]
        [HttpPost]
        public HttpResponseMessage CheckUserInfo(UserCredential credentials)
        {
            try
            {

                var record = db.UserCredentials.Where(c => c.UserId == credentials.UserId).ToList();
                if (record.Count == 0)
                {
                    credentials.UserType = 1;
                    db.UserCredentials.Add(credentials);
                    db.SaveChanges();
                    result = Serializer(ListResponse("A", "Login Successfully", dt));
                }
                else
                {
                    var data = (from c in db.UserCredentials
                                join a in db.UserAddresses on c.UserId equals a.UserId into s
                                from x in s.DefaultIfEmpty()
                                where c.UserId == credentials.UserId
                                select new
                                {
                                    c.LoginId,
                                    c.UserId,
                                    c.Name,
                                    c.Language,
                                    c.LastLogin,
                                    c.ImeiNo,
                                    c.Status,
                                    c.Email,
                                    IsGpsLoc = (x == null ? string.Empty : x.IsGpsLoc.ToString()),
                                    Address = (x == null ? string.Empty : x.Address.ToString()),
                                }).ToList();
                    result = Serializer(ListResponse("U", "Existing User", ListToDatatable(data)));
                }
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(ListResponse("F", ex.Message, dt));
            }
            return result;

        }
        [Route("api/UpdateUserInfo")]
        [HttpPost]
        public HttpResponseMessage UpdateUserInfo(UserCredential credentials)
        {
            try
            {
                var record = db.UserCredentials.Where(c => c.UserId == credentials.UserId).ToList();
                record.ForEach(c =>
                {
                    c.Name = credentials.Name;
                    c.Email = credentials.Email;
                    c.Language = credentials.Language;
                    c.Status = c.Status;
                });
                var address = db.UserAddresses.Where(b => b.UserId == credentials.UserId).ToList();
                address.ForEach(b=> {
                    b.
                })
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(ListResponse("F", ex.Message, dt));
            }
            return result;

        }

        [Route("api/GetVehicleMake")]
        [HttpGet]
        public HttpResponseMessage GetVehicleMake()
        {
            try
            {
                result = Serializer(ListResponse("", "", ListToDatatable(db.VehicleMakes.ToList())));
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(ListResponse("F", ex.Message, dt));
            }
            return result;
        }
        [Route("api/GetVehicleModel")]
        [HttpGet]
        public HttpResponseMessage GetVehicleModel(string makeName)
        {
            try
            {
                int makeId = db.VehicleMakes.Where(d => d.Title == makeName).Select(p => p.Id).SingleOrDefault();
                result = Serializer(ListResponse("", "", ListToDatatable(db.VehicleModels.Where(C => C.MakeId == makeId).ToList())));
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(ListResponse("F", ex.Message, dt));
            }
            return result;
        }
        [Route("api/GetServiceList")]
        [HttpGet]
        public HttpResponseMessage GetServiceList(string serviceName)
        {
            try
            {
                int makeId = db.MainServices.Where(d => d.Name == serviceName).Select(p => p.Id).SingleOrDefault();
                result = Serializer(ListResponse("", "", ListToDatatable(db.SubServices.Where(C => C.ParentId == makeId).ToList())));
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(ListResponse("F", ex.Message, dt));
            }
            return result;
        }
        [Route("api/GetMainMenu")]
        [HttpGet]
        public HttpResponseMessage GetMainMenu()
        {
            try
            {
                result = Serializer(ListResponse("", "", ListToDatatable(db.MainServices.Where(C => C.Status == "1").ToList())));
            }
            catch (Exception ex)
            {
                EHCommon.WriteException(ex);
                result = Serializer(ListResponse("F", ex.Message, dt));
            }
            return result;
        }
        [Route("api/GetParamList")]
        [HttpGet]
        public HttpResponseMessage GetParamList(int paramType)
        {
            try
            {
                result = Serializer(ListResponse("", "", ListToDatatable(db.MstParams.Where(C => C.ParamType == paramType).ToList())));
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