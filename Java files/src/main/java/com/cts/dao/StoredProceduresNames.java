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

	GetFullTicket {
		public String toString() {
			return "dbo.GetFullTicket";
		}
	},

	AddCommentToTicket {
		public String toString() {
			return "dbo.AddCommentToTicket";
		}
	},

	GetPersonalTickets {
		public String toString() {
			return "dbo.GetPersonalTickets";
		}
	},

	GetManageTickets {
		public String toString() {
			return "dbo.GetManageTickets";
		}
	},

	AssignTicketToAdmin {
		public String toString() {
			return "dbo.AssignTicketToAdmin";
		}
	},

	CloseTicket {
		public String toString() {
			return "dbo.CloseTicket";
		}
	},

	GetAdminsForCategory {
		public String toString() {
			return "dbo.GetAdminsForCategory";
		}
	},

	UpdateUser {
		public String toString() {
			return "dbo.UpdateUser";
		}
	},

	ChangeTicketPriority {
		public String toString() {
			return "dbo.ChangeTicketPriority";
		}
	},

	GetPriorities {
		public String toString() {
			return "dbo.GetPriorities";
		}
	},

	AddCategory {
		public String toString() {
			return "dbo.AddCategory";
		}
	},

	DeleteCategory {
		public String toString() {
			return "dbo.DeleteCategory";
		}
	},

	GetCategoriesRightsForUser {
		public String toString() {
			return "dbo.GetCategoriesRightsForUser";
		}
	},

	GetUsers {
		public String toString() {
			return "dbo.GetUsers";
		}
	},

	ReopenTicket {
		public String toString() {
			return "dbo.ReopenTicket";
		}
	},

	UpdateCategoriesRightsForUser {
		public String toString() {
			return "dbo.UpdateCategoriesRightsForUser";
		}
	},

	EditCategory {
		public String toString() {
			return "dbo.EditCategory";
		}
	},

	ChangeTicketCategory {
		public String toString() {
			return "dbo.ChangeTicketCategory";
		}
	},

	GetTicketUser {
		public String toString() {
			return "dbo.GetTicketUser";
		}
	},

	GetUserData {
		public String toString() {
			return "dbo.GetUserData";
		}
	},

	GetQuestions {
		public String toString() {
			return "dbo.GetQuestions";
		}
	},

	GetUserNotificationsSettings {
		public String toString() {
			return "dbo.GetUserNotificationsSettings";
		}
	},

	UpdateUserNotificationsSettings {
		public String toString() {
			return "dbo.UpdateUserNotificationsSettings";
		}
	}
}