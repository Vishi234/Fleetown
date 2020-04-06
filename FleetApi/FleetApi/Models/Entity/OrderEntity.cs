using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FleetApi.Models.Entity
{
    public class OrderEntity
    {
        public string orderId { get; set; }
        public string userId { get; set; }
        public string noOfItems { get; set; }
        public string totalDiscount { get; set; }
        public string totalPrice { get; set; }
        public string itemList { get; set; }
        public string serviceDate { get; set; }
        public string serviceTime { get; set; }
        public string serviceLoc { get; set; }
        public string serviceType { get; set; }
        public string orderDate { get; set; }
        public string orderStatus { get; set; }
        public string otherIssue { get; set; }
        public string vehicleId { get; set; }
        public string vehicleMakeId { get; set; }
        public string vehicleMake { get; set; }
        public string vehicleModelId { get; set; }
        public string itemDiscount { get; set; }
        public string vehicleModel { get; set; }
        public string vehicleNo { get; set; }
        public string cancelReason { get; set; }
        public string otherReason { get; set; }
        public string itemPrice { get; set; }
        public string itemId { get; set; }
        public string couponId { get; set; }
        public string couponDis { get; set; }
        public string couponCode { get; set; }
        public string cancellationDate { get; set; }
        public string flag { get; set; }
        public string msg { get; set; }
        
    }
}