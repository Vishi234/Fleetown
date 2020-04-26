using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FleetApi.Models.Entity
{
    public class CouponEntity
    {
        public string id { get; set; }
        public string userId { get; set; }
        public string couponId { get; set; }
        public string couponCode { get; set; }
        public string couponDesc { get; set; }
        public string couponDiscount { get; set; }
        public string validFrom { get; set; }
        public string validTo { get; set; }
        public string currentStatus { get; set; }
    }
}