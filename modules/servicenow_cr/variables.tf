## Module Variables - Defines module input variables
variable "short_description" {
  description = "Short description for the CR"
  type        = string
}

variable "description" {
  description = "Detailed description for the CR"
  type        = string
}

variable "category" {
  description = "Category of the CR"
  type        = string
  default     = "Software"
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
}

variable "requested_by" {
  description = "The user who requested the CR"
  type        = string
}
