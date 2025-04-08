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

// File: terraform.tfvars
servicenow_instance = "https://hsbcitidu.service-now.com/servicenow"
servicenow_username = "your-username"
servicenow_password = "your-password"
requested_by        = "uma.rao@noexternalmail.hsbc.com"
assignment_group    = "ET-FINEX-BFF-PEAK-IT"
