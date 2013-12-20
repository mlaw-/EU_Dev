using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApplication1.Models
{
    public class Customer
    {
        public string ID { get; set; }
        public List<string> emailAddresses { get; set; }
        public Customer(string ID) 
        { 
            this.ID = ID; 
        }
    }
}