using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using Utilities.Data;

namespace WebApplication1.SQL
{
    public class APP_CRM
    {
        private static string connectionString = ConfigurationManager.ConnectionStrings["App_CRM"].ConnectionString;

        public static string getName(string custNbr)
        {
            string retVal = "";
            SqlParameter[] vars = new SqlParameter[] { new SqlParameter("@custNbr", custNbr) };
            DataTable dt = CommonAccess.GetData(connectionString, "sp_GetCustName", vars);

            foreach (DataRow row in dt.Rows)
            {
                retVal = row["CustName"].ToString();
            }

            return retVal;
        }
    }

}