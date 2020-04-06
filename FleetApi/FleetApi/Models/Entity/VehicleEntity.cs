using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FleetApi.Models.Entity
{
    public class VehicleEntity
    {
        public string id { get; set; }
        public string userId { get; set; }
        public string vehicleMake { get; set; }
        public string vehicleModel { get; set; }
        public string vehicleNo { get; set; }
        public string status { get; set; }
        public string flag { get; set; }
        public string msg { get; set; }
        public string isDefault { get; set; }
    }
}