// Module Outputs - Outputs CR ID for tracking
output "cr_id" {
  description = "The ID of the created Change Request"
  value       = http_request.create_cr.response_body.cr_id
}
