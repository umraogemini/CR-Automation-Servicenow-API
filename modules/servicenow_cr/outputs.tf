// File: modules/servicenow_cr/outputs.tf
output "cr_id" {
  description = "The ID of the created Change Request"
  value       = servicenow_change_request.cr.sys_id
}
