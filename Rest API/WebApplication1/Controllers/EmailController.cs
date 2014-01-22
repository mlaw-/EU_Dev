using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WebApplication1.Models;
using WebApplication1.SQL;

namespace WebApplication1.Controllers
{
    public class EmailController : ApiController
    {
        // /api/customers/1/emails will match:

        [HttpGet]
        public List<Email> Get(ApiController parentController, string parentID)
        {
            return App_CustomerPage.getEmailAddressesByCustNbr(parentID);
        }
        [HttpGet]
        public Email Get(string id)
        {
            Email tmp = new Email();
            tmp.ID = 1;
            tmp.custNbr = "999";
            tmp.emailAddress = "test@email.com";

            return tmp;
        }
        [HttpPost]
        public bool Post(ApiController parentController, string parentID, string emailAddr)
        {
            App_CustomerPage.createEmailAddress(parentID, emailAddr);
            return true;
        }

        [HttpPut]
        public void Put(ApiController parentController, string parentID, string id, string emailAddr)
        {
            App_CustomerPage.updateEmailAddress(id, emailAddr);
        }

        [HttpDelete]
        public void Delete(ApiController parentController, string parentID, int id)
        {
            App_CustomerPage.deleteEmailAddress(id);
        }
       
    }
}
