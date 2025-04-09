// File: modules/servicenow_cr/outputs.tf
output "cr_id" {
  description = "The ServiceNow Change Request ID created via the HTTP request"
  value       = jsondecode(http_request.create_cr.response_body).result.sys_id
}
