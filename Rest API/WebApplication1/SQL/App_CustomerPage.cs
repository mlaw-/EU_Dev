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
        public static List<Email> getEmailAddressesByCustNbr(string custNbr)
        {
            List<Email> retVal = new List<Email>();
            SqlParameter[] vars = new SqlParameter[] {new SqlParameter("@custNbr", custNbr)};
            DataTable dt = CommonAccess.GetData(connectionString, "Ext.GetEmailAddresses", vars);

            foreach (DataRow row in dt.Rows)
            {
                Email tmp = new Email();
                tmp.custNbr = custNbr;
                tmp.emailAddress = row["Email"].ToString();
                tmp.ID = Int32.Parse(row["ID"].ToString());

                retVal.Add(tmp);
            }

            return retVal;
        }
        public static Email getEmailAddressByID(int ID)
        {
            Email retVal = new Email();
            SqlParameter[] vars = new SqlParameter[] { new SqlParameter("@ID", ID) };
            DataTable dt = CommonAccess.GetData(connectionString, "Ext.GetEmailAddress", vars);

            foreach (DataRow row in dt.Rows)
            {

                retVal.custNbr = row["CustNbr"].ToString(); ;
                retVal.emailAddress = row["Email"].ToString();
                retVal.ID = Int32.Parse(row["ID"].ToString());
            }

            return retVal;
        }
        public static List<Email> getAllEmailAddresses()
        {
            List<Email> retVal = new List<Email>();
            DataTable dt = CommonAccess.GetData(connectionString, "Ext.GetAllEmailAddresses");

            foreach (DataRow row in dt.Rows)
            {
                Email tmp = new Email();
                tmp.custNbr = row["CustNbr"].ToString();
                tmp.emailAddress = row["Email"].ToString();
                tmp.ID = Int32.Parse(row["ID"].ToString());

                retVal.Add(tmp);
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

        public static void deleteEmailAddress(int ID)
        {
            SqlParameter[] vars = new SqlParameter[] { 
                new SqlParameter("@ID", ID)
            };
            CommonAccess.ExecuteNonQuery(connectionString, "Ext.DeleteEmailAddress", vars);
        }

        public static void editEmailAddress(int ID, string custNbr, string email)
        {
            SqlParameter[] vars = new SqlParameter[] { 
                new SqlParameter("@ID", ID),
                new SqlParameter("@custNbr", custNbr),
                new SqlParameter("@email", email)
            };

            CommonAccess.ExecuteNonQuery(connectionString, "Ext.EditEmailAddress", vars);
        }
        #endregion
    }
}