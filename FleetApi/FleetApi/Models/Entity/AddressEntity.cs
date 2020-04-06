using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FleetApi.Models.Entity
{
    public class AddressEntity
    {
        public string userId { get; set; }
        public string addressId { get; set; }
        public string address { get; set; }
        public string isGpsLoc { get; set; }
        public string latitude { get; set; }
        public string longitude { get; set; }
        public string isDefault { get; set; }
    }
}