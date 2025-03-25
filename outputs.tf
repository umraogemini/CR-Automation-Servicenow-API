## Global Outputs - Defines Terraform outputs for the root module
output "cr_id" {
  description = "Change Request ID"
  value       = module.create_cr.cr_id
}
