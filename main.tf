// File: main.tf
module "create_cr" {
  source            = "./modules/servicenow_cr"
  short_description = "Automated CR via Terraform"
  description       = "Created for deployment automation"
  category          = "Software"
  risk              = "low"
  impact            = "low"
  assignment_group  = var.assignment_group
  requested_by      = var.requested_by
}

output "cr_id" {
  value = module.create_cr.cr_id
}
