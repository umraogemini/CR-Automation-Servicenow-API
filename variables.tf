## Global Variable - Defines input variables for Terraform
variable "servicenow_instance" {
  description = "ServiceNow instance URL"
  type        = string
}

variable "servicenow_username" {
  description = "ServiceNow API username"
  type        = string
  sensitive   = true
}

variable "servicenow_password" {
  description = "ServiceNow API password"
  type        = string
  sensitive   = true
}

variable "assignment_group" {
  description = "Assignment group for CR"
  type        = string
  default     = "ITSM Team"
}
