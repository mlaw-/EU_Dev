using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using Utilities.Data;
using WebApplication1.Models;

namespace WebApplication1.SQL
{
    public static class App_CustomerPage
    {
        private static string connectionString = ConfigurationManager.ConnectionStrings["App_CustomerPage"].ConnectionString;

        #region Customer SQL
        public static Customer getCustomerByID(string custNbr)

        {
            Customer retVal = new Customer(custNbr);
            retVal.emailAddresses = getEmailAddressesByCustNbr(custNbr);

            return retVal;
        }

        public static bool validateCustomer(string custNbr)
        {
            SqlParameter[] vars = new SqlParameter[] {new SqlParameter("@custNbr", custNbr)};
            string result = CommonAccess.GetDataString(connectionString, "Ext.IsValidCustomer", vars);

            return result == "0" ? false : true;
        }

        #endregion

        #region Email SQL
        public static void createEmailAddress(string custNbr, string email)
        {
            SqlParameter[] vars = new SqlParameter[] { 
                new SqlParameter("@custNbr", custNbr), 
                new SqlParameter("@email", email)
            };
            CommonAccess.ExecuteNonQuery(connectionString, "Ext.CreateEmailAddress", vars);

        }
        public static List<string> getEmailAddressesByCustNbr(string custNbr)
        {
            List<string> retVal = new List<string>();
            SqlParameter[] vars = new SqlParameter[] {new SqlParameter("@custNbr", custNbr)};
            DataTable dt = CommonAccess.GetData(connectionString, "Ext.GetEmailAddresses", vars);

            foreach (DataRow row in dt.Rows)
            {
                retVal.Add(row["Email"].ToString());
            }

            return retVal;
        }

        public static void updateEmailAddress(string ID, string emailAddr)
        {
            SqlParameter[] vars = new SqlParameter[] { 
                new SqlParameter("@ID", ID), 
                new SqlParameter("@email", emailAddr)
            };
            CommonAccess.ExecuteNonQuery(connectionString, "Ext.UpdateEmailAddress", vars);
        }

        public static void deleteEmailAddress(string ID)
        {
            SqlParameter[] vars = new SqlParameter[] { 
                new SqlParameter("@ID", ID)
            };
            CommonAccess.ExecuteNonQuery(connectionString, "Ext.DeleteEmailAddress", vars);
        }
        #endregion
    }
}