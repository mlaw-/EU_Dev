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
        /// <summary>
        /// Gets outages. Optional Parameters: Datetime 'from' (default:""), Datetime 'to' (default:""), bit 'openOnly' (default:"0"), string ID (default:""). May be used to retrieve a range of outages or a single outage by ID. Parameters may be used in any combination
        /// </summary>
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
        /// <summary>
        /// Create outage entry. Required Parameters: string custNbr, string locationID, int serviceTypeID, int problemTypeID, DateTime dateReceived, int dispatchedID || Optional Parametersstring notes (default: null),
        /// DateTime dateDispatched (default: null), dateReconnected (default: null)
        /// </summary>
        // POST api/outage
        [HttpPost]
        public void Post(string custNbr, string locationID, int serviceTypeID, int problemTypeID,  DateTime dateReceived, int dispatchedID,
            string notes = null, DateTime? dateDispatched = null, DateTime? dateReconnected = null)
        {
            OutageRepository.createOutage(custNbr, locationID, serviceTypeID, problemTypeID, dateReceived, dispatchedID, notes, dateDispatched, dateReconnected);
        }
        /// <summary>
        /// Update outage entry by ID. Required Parameters: int id || Optional Parameters: string custNbr, string locationID, int serviceTypeID, int problemTypeID, DateTime dateReceived, int dispatchedID, string notes,
        /// DateTime dateDispatched (default: null), dateReconnected (default: null)
        /// </summary>
        // PUT api/outage/5
        [HttpPut]
        public void Put(int id, string custNbr = null, string locationID = null, string notes = null, int? serviceTypeID = null, int? problemTypeID = null, DateTime? dateReceived = null, int? dispatchedID = null,
             DateTime? dateDispatched = null, DateTime? dateReconnected = null)
        {
            OutageRepository.updateOutage(id, custNbr, locationID, notes, serviceTypeID, problemTypeID, dateReceived, dispatchedID, dateDispatched, dateReconnected);
        }
        /// <summary>
        /// Delete outage entry by ID. Required Parameters: int id 
        /// </summary>
        // DELETE api/outage/5
        [HttpDelete]
        public void Delete(int id)
        {
            OutageRepository.deleteOutage(id);
        }
    }
}
