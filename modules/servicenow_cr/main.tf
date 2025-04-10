// File: modules/servicenow_cr/main.tf

data "http" "create_cr" {
  url    = "${var.servicenow_instance}/api/now/table/change_request"
  method = "POST"

  request_headers = {
    Content-Type  = "application/json"
    Authorization = "Basic ${base64encode("${var.servicenow_username}:${var.servicenow_password}")}"
  }

  request_body = jsonencode({
    short_description = var.short_description
    description       = var.description
    risk              = var.risk
    impact            = var.impact
    assignment_group  = var.assignment_group
    requested_by      = var.requested_by
  })
}

locals {
  cr_response = jsondecode(data.http.create_cr.response_body)
}

output "cr_id" {
  value = local.cr_response.result.sys_id
}
