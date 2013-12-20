using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WebApplication1.SQL;

namespace WebApplication1.Controllers
{
    public class EmailController : ApiController
    {
        // /api/customers/1/emails will match:
        [HttpGet]
        public IEnumerable<string> Get(ApiController parentController, string parentID)
        {
            return App_CustomerPage.getEmailAddressesByCustNbr(parentID).AsEnumerable<string>();
        }

        [HttpPost]
        public void Post(ApiController parentController, string parentID, string emailAddr)
        {
            App_CustomerPage.createEmailAddress(parentID, emailAddr);
        }

        [HttpPut]
        public void Put(ApiController parentController, string parentID, string id, string emailAddr)
        {
            App_CustomerPage.updateEmailAddress(id, emailAddr);
        }

        [HttpDelete]
        public void Delete(ApiController parentController, string parentID, string id)
        {
            App_CustomerPage.deleteEmailAddress(id);
        }
        /*
        // /api/customers/1/orders/123 will match:

        public Order Orders(int customerId, int id)
        // /api/customers/1/products will match:

        public IEnumerable<Product> Products(int customerId)
        // /api/customers/1/products/123 will match:

        public Product Products(int customerId, int id)
         * 
         * */
    }
}
