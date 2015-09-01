package com.cts.dao;

public enum StoredProceduresNames {

	CreateTicket {
		public String toString() {
			return "dbo.CreateTicket";
		}
	},

	CreateUser {
		public String toString() {
			return "dbo.CreateUser";
		}
	},

	DeleteTicket {
		public String toString() {
			return "dbo.DeleteTicket";
		}
	},

	DeleteUser {
		public String toString() {
			return "dbo.DeleteUser";
		}
	},

	GetCategories {
		public String toString() {
			return "dbo.GetCategories";
		}
	},

	GetSubcategories {
		public String toString() {
			return "dbo.GetSubcategories";
		}
	},

	ValidateLogin {
		public String toString() {
			return "dbo.ValidateLogin";
		}
	},

	ResetPassword {
		public String toString() {
			return "dbo.ResetPassword";
		}
	},

	GetTicketComments {
		public String toString() {
			return "dbo.GetTicketComments";
		}
	},

	AddCommentToTicket {
		public String toString() {
			return "dbo.AddCommentToTicket";
		}
	},

	GetTickets {
		public String toString() {
			return "dbo.GetTickets";
		}
	}
}
