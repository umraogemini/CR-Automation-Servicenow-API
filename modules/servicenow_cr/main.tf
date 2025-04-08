// Minion API Integration for CR Create CR using Minion API - Calls Minion API to create & approve CR
resource "http_request" "create_cr" {
  url = "${var.minion_api_url}/create-cr"
  method = "POST"

  request_headers = {
    Content-Type  = "application/json"
    Authorization = "Bearer ${var.minion_api_token}"
  }

  request_body = jsonencode({
    short_description = var.short_description
    description       = var.description
    risk             = var.risk
    impact           = var.impact
    assignment_group = var.assignment_group
  })
}

# 🔹 Approve CR using Minion API
resource "http_request" "approve_cr" {
  url = "${var.minion_api_url}/approve-cr"
  method = "POST"

  request_headers = {
    Content-Type  = "application/json"
    Authorization = "Bearer ${var.minion_api_token}"
  }

  request_body = jsonencode({
    cr_id = http_request.create_cr.response_body.cr_id
  })

  depends_on = [http_request.create_cr]
}
