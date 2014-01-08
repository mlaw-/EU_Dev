using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Web;

namespace WebApplication1.Models
{
    public class Email
    {
        [DisplayName("ID")]
        public int ID { get; set; }
        [DisplayName("Email Address")]
        public string emailAddress { get; set; }
        [DisplayName("Customer Number")]
        public string custNbr { get; set; }

        public Email() { }
    }
}