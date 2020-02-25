using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Reflection;
using System.Web;
using System.Web.Script.Serialization;

namespace FleetApi.Models.BAL
{
    public static class Common
    {
        public static string ListResponse(string flag, string msg ,DataTable dt)
        {
            string response = string.Empty;
            JavaScriptSerializer serializer = new JavaScriptSerializer();
            List<Dictionary<string, object>> rows = new List<Dictionary<string, object>>();
            Dictionary<string, object> row = null;
            if (dt.Rows.Count == 0)
            {
                dt = new DataTable();
                dt.Columns.Add("FLAG");
                dt.Columns.Add("MSG");
                DataRow drow = dt.NewRow();
                drow[0] = flag;
                drow[1] = msg;
                dt.Rows.Add(drow);
            }
            else
            {
                DataColumn flagCol = new DataColumn("FLAG", typeof(string));
                DataColumn msgCol = new DataColumn("MSG", typeof(string));
                flagCol.DefaultValue = flag;
                msgCol.DefaultValue = msg;
                dt.Columns.Add(flagCol);
                dt.Columns.Add(msgCol);
            }
            foreach (DataRow dr in dt.Rows)
            {
                row = new Dictionary<string, object>();
                foreach (DataColumn col in dt.Columns)
                {
                    row.Add(col.ColumnName.Trim(), dr[col]);
                }
                rows.Add(row);
            }
            return serializer.Serialize(new { Result = rows });
        }
        public static DataTable ListToDatatable<T>(List<T> items)
        {
            DataTable dataTable = new DataTable(typeof(T).Name);

            //Get all the properties
            PropertyInfo[] Props = typeof(T).GetProperties(BindingFlags.Public | BindingFlags.Instance);
            foreach (PropertyInfo prop in Props)
            {
                //Defining type of data column gives proper data table 
                var type = (prop.PropertyType.IsGenericType && prop.PropertyType.GetGenericTypeDefinition() == typeof(Nullable<>) ? Nullable.GetUnderlyingType(prop.PropertyType) : prop.PropertyType);
                //Setting column names as Property names
                dataTable.Columns.Add(prop.Name, type);
            }
            foreach (T item in items)
            {
                var values = new object[Props.Length];
                for (int i = 0; i < Props.Length; i++)
                {
                    //inserting property values to datatable rows
                    values[i] = Props[i].GetValue(item, null);
                }
                dataTable.Rows.Add(values);
            }
            //put a breakpoint here and check datatable
            return dataTable;
        }
    }
    public static class EHCommon
    {
        public static void WriteException(Exception e)
        {
            string logfileadd = ConfigurationManager.AppSettings["exception_log_file"].ToString();
            using (System.IO.StreamWriter writer = new System.IO.StreamWriter(logfileadd, true))
            {

                writer.WriteLine("Exception :" + e.ToString() + "\r\n");
                writer.WriteLine("Time :" + DateTime.Now.ToString("dd-MMM-yyyy HH:mm:ss") + "\r\n");
                writer.WriteLine("----------------------------------------------------------------------------------------------------------------------");
                writer.Close();
            }

        }
    }
}