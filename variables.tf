// File: variables.tf
variable "servicenow_instance" {
  description = "ServiceNow instance URL"
  type        = string
  default     = "https://hsbcitidu.service-now.com/servicenow"
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
  default     = "ET-FINEX-BFF-PEAK-IT"
}

variable "requested_by" {
  description = "User requesting the CR"
  type        = string
  default     = "uma.rao@noexternalmail.hsbc.com"
}
