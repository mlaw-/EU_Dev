using OMS.Models;
using OMS.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

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

            return OutageRepository.getOutage(
                (from == "" || !DateTime.TryParse(from, out tmp)) ? null : (DateTime?)tmp,
                (to == "" || !DateTime.TryParse(to, out tmp2)) ? null : (DateTime?)tmp2,
                (openOnly == "1" ? true : false),
                (ID == "" || !Int32.TryParse(ID, out tmp3)) ? null : (int?)tmp3
                );
        }

        // POST api/outage
        [HttpPost]
        public void Post(string custNbr, string locationID, int serviceTypeID, int problemTypeID,  DateTime dateReceived, int dispatchedID,
            string notes = null, DateTime? dateDispatched = null, DateTime? dateReconnected = null)
        {
            OutageRepository.createOutage(custNbr, locationID, serviceTypeID, problemTypeID, dateReceived, dispatchedID, notes, dateDispatched, dateReconnected);
        }

        // PUT api/outage/5
        [HttpPut]
        public void Put(int id, string custNbr = null, string locationID = null, string notes = null, int? serviceTypeID = null, int? problemTypeID = null, DateTime? dateReceived = null, int? dispatchedID = null,
             DateTime? dateDispatched = null, DateTime? dateReconnected = null)
        {
            OutageRepository.updateOutage(id, custNbr, locationID, notes, serviceTypeID, problemTypeID, dateReceived, dispatchedID, dateDispatched, dateReconnected);
        }

        // DELETE api/outage/5
        [HttpDelete]
        public void Delete(int id)
        {
            OutageRepository.deleteOutage(id);
        }
    }
}
