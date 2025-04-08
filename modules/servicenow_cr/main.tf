// File: modules/servicenow_cr/main.tf
resource "servicenow_change_request" "cr" {
  short_description = var.short_description
  description       = var.description
  risk              = var.risk
  impact            = var.impact
  assignment_group  = var.assignment_group
  requested_by      = var.requested_by
}
