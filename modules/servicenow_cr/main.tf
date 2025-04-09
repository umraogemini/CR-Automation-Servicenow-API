// File: modules/servicenow_cr/main.tf
resource "http_request" "create_cr" {
  url    = "${var.servicenow_instance}/api/now/table/change_request" // add files
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

output "cr_id" {
  value = jsondecode(http_request.create_cr.response_body).result.sys_id
}
