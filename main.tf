## Terraform Root Module to Call CR Module - Calls the reusable CR module
module "create_cr" {
  source            = "./modules/servicenow_cr"
  short_description = "Automated CR via Terraform"
  description       = "Created for deployment automation"
  category         = "Software"
  risk            = "low"
  impact          = "low"
  assignment_group = var.assignment_group
  requested_by    = "umrao@example.com"
}

output "cr_id" {
  value = module.create_cr.cr_id
}
