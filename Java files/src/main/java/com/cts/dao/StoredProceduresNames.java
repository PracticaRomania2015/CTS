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

	GetAllCategories {
		public String toString() {
			return "dbo.GetAllCategories";
		}
	},

	ValidateLogin {
		public String toString() {
			return "dbo.ValidateLogin";
		}
	},

	CheckEmailForRecoveryPasswordAndUpdatePassword {
		public String toString() {
			return "dbo.CheckEmailForRecoveryPasswordAndUpdatePassword";
		}
	}
}
