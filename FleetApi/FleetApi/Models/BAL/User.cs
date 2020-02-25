using FleetApi.Models.Entity;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web;

namespace FleetApi.Models.BAL
{
    public class User
    {
        string sqlconn = ConfigurationManager.ConnectionStrings["DBCONN"].ConnectionString;
        public string UserAuthorization(UserEntity userEntity)
        {
            string str = "";

            return str;
        }
    }
}