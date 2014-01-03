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
    public class OutageController : ApiController
    {
        // GET api/outage
        [HttpGet]
        public List<Outage> Get( string from = "", string to = "", string openOnly = "0", string ID = "")
        {
            DateTime tmp;
            DateTime tmp2;
            int tmp3;

            return cpApp_OutageManagementSystem.getOutage(
                (from == "" || !DateTime.TryParse(from, out tmp)) ? null : (DateTime?)tmp,
                (to == "" || !DateTime.TryParse(to, out tmp2)) ? null : (DateTime?)tmp2,
                (openOnly == "1" ? true : false),
                (ID == "" || !Int32.TryParse(ID, out tmp3)) ? null : (int?)tmp3
                );
        }

        // POST api/outage
        public void Post([FromBody]string value)
        {
        }

        // PUT api/outage/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/outage/5
        public void Delete(int id)
        {
        }
    }
}
