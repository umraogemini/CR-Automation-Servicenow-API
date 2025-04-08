// Module Variables - Defines input variables for Minion API CR module
variable "short_description" {
  description = "Short description for the CR"
  type        = string
  default     ="Automation CR creation Using ServiceNow"
}

variable "description" {
  description = "Detailed description for the CR"
  type        = string
  default     ="Automated Change Request creation and appoval via terraform. A Github pull request triggers the CR request,While Jenkins manges approval and send email notification"
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
  default     ="ET-FINEX-BFF-PEAK-IT"
}

Variable "requested_by"{
  description  = "The useer who requested the CR'
  type         = string
  default        ="uma.rao@noexternalmail.hsbc.com"
}
