using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebApplication1.Models;
using WebApplication1.SQL;

namespace WebApplication1.Controllers
{
    public class EmailViewController : Controller
    {
        //
        // GET: /EmailView/
        public ActionResult Index()
        {
            var emails = App_CustomerPage.getAllEmailAddresses();

            return View(emails);
        }
        //
        // GET: /EmailView/Customer/999999
        public ActionResult Customer(int id)
        {
            var emails = App_CustomerPage.getEmailAddressesByCustNbr(id.ToString());
            ViewBag.HeaderText = string.Format("E-mail Addresses for CustNbr {0}", id.ToString());
            return View(emails);
        }

        //
        // GET: /EmailView/Details/5
        public ActionResult Details(int id)
        {
            return View(App_CustomerPage.getEmailAddressByID(id));
        }

        //
        // GET: /EmailView/Create
        /*
        public ActionResult Create()
        {
            return View(new Email());
        }*/

        //
        // GET: /EmailView/Create
        public ActionResult Create(string custNbr)
        {
            var email = new Email();
            email.custNbr = custNbr;
            return View(email);
        }

        //
        // POST: /EmailView/Create
        [HttpPost]
        public ActionResult Create(FormCollection collection)
        {
            try
            {
                // TODO: Add insert logic here
                string custNbr, email;
                custNbr = collection["custNbr"];
                email = collection["emailAddress"];

                App_CustomerPage.createEmailAddress(custNbr, email);
                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        //
        // GET: /EmailView/Edit/5
        public ActionResult Edit(int id)
        {
            return View(App_CustomerPage.getEmailAddressByID(id));
        }

        //
        // POST: /EmailView/Edit/5
        [HttpPost]
        public ActionResult Edit(int id, FormCollection collection)
        {
            try
            {
                // TODO: Add update logic here
                string emailAddress = collection["emailAddress"];
                string custNbr = collection["custNbr"];
                App_CustomerPage.editEmailAddress(id, custNbr, emailAddress);
                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        //
        // GET: /EmailView/Delete/5
        public ActionResult Delete(int id)
        {
            return View(App_CustomerPage.getEmailAddressByID(id));
        }

        //
        // POST: /EmailView/Delete/5
        [HttpPost]
        public ActionResult Delete(int id, FormCollection collection)
        {
            try
            {
                // TODO: Add delete logic here
                App_CustomerPage.deleteEmailAddress(id);
                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }
    }
}
