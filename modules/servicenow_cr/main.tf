## Reusable CR Module - Creates & approves a ServiceNow CR using a module
resource "servicenow_record" "change_request" {
  table  = "change_request"
  fields = {
    short_description = var.short_description
    description       = var.description
    category         = var.category
    risk            = var.risk
    impact          = var.impact
    assignment_group = var.assignment_group
    requested_by    = var.requested_by
    state           = "new"
  }
}

resource "servicenow_record" "approve_cr" {
  table  = "change_request"
  sys_id = servicenow_record.change_request.sys_id
  fields = {
    state = "approved"
  }
  depends_on = [servicenow_record.change_request]
}
