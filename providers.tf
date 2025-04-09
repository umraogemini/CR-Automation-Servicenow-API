// File: providers.tf
terraform {
  required_providers {
    servicenow = {
      source  = "servicenow/servicenow"
      version = "~> 0.3.0"
    }
  }
}

provider "servicenow" {
  instance  = var.servicenow_instance
  username  = var.servicenow_username
  password  = var.servicenow_password
}
