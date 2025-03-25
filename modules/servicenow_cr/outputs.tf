## Module Outputs - Outputs CR ID for external reference
output "cr_id" {
  description = "The sys_id of the created Change Request"
  value       = servicenow_record.change_request.sys_id
}
