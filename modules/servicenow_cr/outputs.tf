// File: outputs.tf
output "cr_id" {
  description = "Change Request ID"
  value       = module.create_cr.cr_id
}
