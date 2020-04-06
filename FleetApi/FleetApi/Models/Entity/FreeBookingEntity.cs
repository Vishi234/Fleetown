using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FleetApi.Models.Entity
{
    public class FreeBookingEntity
    {
        public string reqId { get; set; }
        public string userId { get; set; }
        public string itemName { get; set; }
        public string price { get; set; }
        public string reqDate { get; set; }
        public string schDate { get; set; }
        public string schTime { get; set; }
        public string location { get; set; }
        public string vehicleId { get; set; }
        public string status { get; set; }
        public string flag { get; set; }
        public string msg { get; set; }
        public string userReqSta { get; set; }
    }
}