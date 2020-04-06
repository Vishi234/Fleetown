using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FleetApi.Models.Entity
{
    public class UserEntity
    {
        public string userId { get; set; }
        public string loginId { get; set; }
        public string name { get; set; }
        public string email { get; set; }
        public string imeiNo { get; set; }
        public string language { get; set; }
        public string addressId { get; set; }
        public string address { get; set; }
        public string isGpsLoc { get; set; }
        public string latitude { get; set; }
        public string longitude { get; set; }
        public string lastLogin { get; set; }
        public string status { get; set; }
        public string vehicleId { get; set; }
        public string vehicleMake { get; set; }
        public string vehicleModel { get; set; }
        public string vehicleNo { get; set; }
        public string freeRequestRaise { get; set; }
        public string oper { get; set; }
        public string flag { get; set; }
        public string msg { get; set; }
    }
}