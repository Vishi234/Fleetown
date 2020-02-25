using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FleetApi.Models.Entity
{
    public class UserEntity
    {
        public string loginId { get; set; }
        public string userId { get; set; }
        public string name { get; set; }
        public string lastLogin { get; set; }
        public string imeiNo { get; set; }
        public string language { get; set; }
    }
}