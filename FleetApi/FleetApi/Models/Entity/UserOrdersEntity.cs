using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FleetApi.Models.Entity
{
    public class UserOrdersEntity
    {
        public string orderId { get; set; }
        public string userId { get; set; }
        public string noOfItems { get; set; }
        public string totalPrice { get; set; }
        public string serviceDate { get; set; }
        public string serviceTime { get; set; }
        public string serviceLocation { get; set; }
        public string serviceType { get; set; }
        public string orderDate { get; set; }
        public string orderStatus { get; set; }
        public string otherIssue { get; set; }
        public string vehicleId { get; set; }
        public string vehicleMake { get; set; }
        public string vehicleModel { get; set; }
        public string vehicleNo { get; set; }
        public string orderItems { get; set; }
    }
}