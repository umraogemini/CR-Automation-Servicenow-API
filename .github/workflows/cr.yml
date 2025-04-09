// File: modules/servicenow_cr/variables.tf
variable "short_description" {
  description = "Short description for the CR"
  type        = string
  default     = "Automation CR creation Using ServiceNow"
}

variable "description" {
  description = "Detailed description for the CR"
  type        = string
  default     = "Automated Change Request creation and approval via Terraform. A GitHub pull request triggers the CR request, while Jenkins manages approval and sends email notification."
}

variable "risk" {
  description = "Risk level of the CR"
  type        = string
  default     = "low"
}

variable "impact" {
  description = "Impact level of the CR"
  type        = string
  default     = "low"
}

variable "assignment_group" {
  description = "The assignment group for the CR"
  type        = string
  default     = "ET-FINEX-BFF-PEAK-IT"
}

variable "requested_by" {
  description = "The user who requested the CR"
  type        = string
  default     = "uma.rao@noexternalmail.hsbc.com"
}

variable "servicenow_instance" {
  type = string
}

variable "servicenow_username" {
  type      = string
  sensitive = true
}

variable "servicenow_password" {
  type      = string
  sensitive = true
}
