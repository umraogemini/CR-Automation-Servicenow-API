## Remote State Storage (Stores Terraform state in S3 (or use Terraform Cloud backend))
terraform {
  backend "s3" {
    bucket = "terraform-state-bucket"
    key    = "servicenow/terraform.tfstate"
    region = "us-east-1"
  }
}
