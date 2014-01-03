using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApplication1.Models
{
    public class Outage
    {
        public int ID { get; set; }
        public string address { get; set; }
        public float latitude { get; set; }
        public float longitude { get; set; }
        public string outageType { get; set; }
        public string dateEntered { get; set; }
        public string dateClosed { get; set; }
        public string note { get; set; }

        public Outage() { }
    }
}