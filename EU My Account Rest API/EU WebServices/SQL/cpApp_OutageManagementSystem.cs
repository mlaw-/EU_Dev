using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using Utilities.Data;
using Utilities.Logging;
using WebApplication1.Models;

namespace WebApplication1.SQL
{
    public class cpApp_OutageManagementSystem
    {
        private static string connectionString = ConfigurationManager.ConnectionStrings["cpApp_OutageManagementSystem"].ConnectionString;
        /*
        public static List<Outage> getOutage()
        {
            return parseGetOutage(CommonAccess.GetData(connectionString, "API.GetOutages"));
        }*/
        public static List<Outage> getOutage(DateTime? fromDate, DateTime? toDate, bool openOnly, int? ID)
        {
            List<SqlParameter> vars = new List<SqlParameter>();
            if(fromDate != null)
                vars.Add(new SqlParameter("@fromDate", DateTime.Parse(fromDate.ToString())));
            if(toDate != null)
                vars.Add(new SqlParameter("@toDate", DateTime.Parse(toDate.ToString())));
            if (ID != null)
                vars.Add(new SqlParameter("@ID", ID));

            vars.Add(new SqlParameter("@openOnly", openOnly));
            
            DataTable dt = CommonAccess.GetData(connectionString, "API.GetOutages", vars.ToArray<SqlParameter>());
            return parseGetOutage(dt);
        }

        private static List<Outage> parseGetOutage(DataTable dt)
        {
            List<Outage> retVal = new List<Outage>();
            foreach (DataRow row in dt.Rows)
            {
                Outage tmp = new Outage();
                try 
                { 
                    tmp.ID = Int32.Parse(row["ID"].ToString());
                    tmp.address = row["Address"].ToString();
                    tmp.latitude = float.Parse(row["Latitude"].ToString());
                    tmp.longitude = float.Parse(row["Longitude"].ToString());
                    tmp.outageType = row["ServiceName"].ToString();
                    tmp.dateEntered = row["DateEntered"].ToString();
                    tmp.dateClosed = row["DateClosed"].ToString();
                    tmp.note = row["Note"].ToString();

                    retVal.Add(tmp);
                }
                catch (Exception ex)
                {
                    LogWriter.Write(string.Format("Function {0} failed on outage ID {1}, with exception {2}", new object[] { "parseGetOutage", tmp.ID, ex.Message }));
                }
            }
            return retVal;
        }
    }
}